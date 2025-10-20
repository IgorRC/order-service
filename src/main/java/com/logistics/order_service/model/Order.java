package com.logistics.order_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String customerId;
    private OrderStatus status;
    private List<OrderItem> items;
    private Instant createdAt;
    private Instant updatedAt;
}
