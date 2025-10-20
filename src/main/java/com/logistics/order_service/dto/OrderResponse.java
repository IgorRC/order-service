package com.logistics.order_service.dto;

import com.logistics.order_service.model.OrderItem;
import com.logistics.order_service.model.OrderStatus;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String customerId;
    private OrderStatus status;
    private List<OrderItem> items;
    private Instant createdAt;
    private Instant updatedAt;
}
