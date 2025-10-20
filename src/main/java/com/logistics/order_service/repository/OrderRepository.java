package com.logistics.order_service.repository;

import com.logistics.order_service.model.Order;
import com.logistics.order_service.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerId(String customerId);

    List<Order> findByStatusAndCustomerId(OrderStatus status, String customerId);

}
