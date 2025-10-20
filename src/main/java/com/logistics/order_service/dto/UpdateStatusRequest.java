package com.logistics.order_service.dto;

import com.logistics.order_service.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull
    private OrderStatus newOrderStatus;
}
