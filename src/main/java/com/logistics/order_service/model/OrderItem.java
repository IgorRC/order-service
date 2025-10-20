package com.logistics.order_service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String sku;
    private Integer quantity;
    private BigDecimal price;
}
