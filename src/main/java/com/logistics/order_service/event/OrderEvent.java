package com.logistics.order_service.event;

import com.logistics.order_service.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrderEvent {
    private String orderId;
    private OrderStatus oldStatus;
    private OrderStatus newStatus;
    private Instant timestamp;
}
