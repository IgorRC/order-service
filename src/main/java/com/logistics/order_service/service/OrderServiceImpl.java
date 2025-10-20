package com.logistics.order_service.service;

import com.logistics.order_service.dto.CreateOrderRequest;
import com.logistics.order_service.dto.OrderResponse;
import com.logistics.order_service.dto.UpdateStatusRequest;
import com.logistics.order_service.event.OrderEvent;
import com.logistics.order_service.mapper.OrderMapper;
import com.logistics.order_service.model.Order;
import com.logistics.order_service.model.OrderStatus;
import com.logistics.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = orderMapper.toOrder(request);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        Order savedOrder = orderRepository.save(order);
        log.info("Order {} created", savedOrder.getId());
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Cacheable(value = "orders",key = "#id")
    public Optional<OrderResponse> getOrderById(String id) {
        log.info("Fetching order {} from database",id);
        return orderRepository.findById(id).map(orderMapper::toOrderResponse);
    }

    @Override
    public List<OrderResponse> findOrders(OrderStatus status, String customerId) {
        List<Order> orders;
        if(status != null && customerId != null){
            orders = orderRepository.findByStatusAndCustomerId(status,customerId);
        }else if(status != null){
            orders = orderRepository.findByStatus(status);
        }else if(customerId != null){
            orders = orderRepository.findByCustomerId(customerId);
        }else {
            orders = orderRepository.findAll();
        }
        return orders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "orders",key = "#id")
    public Optional<OrderResponse> updateOrderStatus(String id, UpdateStatusRequest request) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            return Optional.empty();
        }

        Order order = optionalOrder.get();
        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = request.getNewOrderStatus();

        order.setStatus(newStatus);
        order.setUpdatedAt(Instant.now());
        Order updateOrder = orderRepository.save(order);
        log.info("Order {} status update from {} to {}",id,oldStatus,newStatus);

        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(id)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .timestamp(Instant.now())
                .build();
        kafkaProducerService.sendOrderEvent(orderEvent);

        return Optional.of(orderMapper.toOrderResponse(updateOrder));
    }
}



























