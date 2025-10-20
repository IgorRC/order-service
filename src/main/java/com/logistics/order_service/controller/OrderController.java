package com.logistics.order_service.controller;

import com.logistics.order_service.dto.CreateOrderRequest;
import com.logistics.order_service.dto.OrderResponse;
import com.logistics.order_service.dto.UpdateStatusRequest;
import com.logistics.order_service.model.OrderStatus;
import com.logistics.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs para la creación y consulta de órdenes")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Crea una nueva orden de entrega")
    @ApiResponse(responseCode = "201", description = "Orden creada exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){
        OrderResponse response = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca una orden por su ID único")
    @ApiResponse(responseCode = "200", description = "Orden encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id){
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todas las órdenes con filtros opcionales")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam(required = false)OrderStatus status,
            @RequestParam(required = false) String customerId){
        return ResponseEntity.ok(orderService.findOrders(status, customerId));
    }

    @Operation(summary = "Actualiza el estado de una orden existente")
    @ApiResponse(responseCode = "200", description = "Estado de la orden actualizado correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    @ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content)
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest request
            ){
        return orderService.updateOrderStatus(id,request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
