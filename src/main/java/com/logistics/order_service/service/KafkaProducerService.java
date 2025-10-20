package com.logistics.order_service.service;

import com.logistics.order_service.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    @Value("${kafka.topic.orders-events}")
    private String orderEventsTopic;

    public void sendOrderEvent(OrderEvent orderEvent) {
        log.info("Producing event: {}",orderEvent);
        kafkaTemplate.send(orderEventsTopic, orderEvent.getOrderId(), orderEvent);
    }
}
