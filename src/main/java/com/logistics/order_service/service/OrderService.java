package com.logistics.order_service.service;

import com.logistics.order_service.dto.CreateOrderRequest;
import com.logistics.order_service.dto.OrderResponse;
import com.logistics.order_service.dto.UpdateStatusRequest;
import com.logistics.order_service.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    Optional<OrderResponse> getOrderById(String id);
    List<OrderResponse> findOrders(OrderStatus status, String customerId);
    Optional<OrderResponse> updateOrderStatus(String id, UpdateStatusRequest request);
}
