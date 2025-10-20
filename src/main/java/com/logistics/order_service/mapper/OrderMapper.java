package com.logistics.order_service.mapper;

import com.logistics.order_service.dto.CreateOrderRequest;
import com.logistics.order_service.dto.OrderResponse;
import com.logistics.order_service.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toOrder(CreateOrderRequest request);

    OrderResponse toOrderResponse(Order order);
}
