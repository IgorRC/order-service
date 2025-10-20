package com.logistics.order_service.service;



import com.logistics.order_service.dto.CreateOrderRequest;
import com.logistics.order_service.dto.OrderResponse;
import com.logistics.order_service.mapper.OrderMapper;
import com.logistics.order_service.model.Order;
import com.logistics.order_service.model.OrderStatus;
import com.logistics.order_service.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private KafkaProducerService  kafkaProducerService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Deberia crear una orden exitosamente")
    void shouldCreateOrderSuccessfully() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCustomerId("74439864");

        Order orderSinId = Order.builder()
                .customerId("74439864")
                .build();

        Order orderGuardada = Order.builder()
                .id("123")
                .customerId("74439864")
                .status(OrderStatus.NEW)
                .build();

        OrderResponse responseEsperado = new OrderResponse();
        responseEsperado.setId("123");
        responseEsperado.setStatus(OrderStatus.NEW);

        when(orderMapper.toOrder(any(CreateOrderRequest.class))).thenReturn(orderSinId);
        when(orderRepository.save(any(Order.class))).thenReturn(orderGuardada);
        when(orderMapper.toOrderResponse(any(Order.class))).thenReturn(responseEsperado);

        OrderResponse responseReal = orderService.createOrder(createOrderRequest);

        assertThat(responseReal).isNotNull();
        assertThat(responseReal.getId()).isEqualTo("123");
        assertThat(responseReal.getStatus()).isEqualTo(OrderStatus.NEW);

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
