package com.logistics.order_service.dto;

import com.logistics.order_service.model.OrderItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class CreateOrderRequest {
    @NotNull
    private String customerId;
    @NotEmpty
    private List<OrderItem> items;
}
