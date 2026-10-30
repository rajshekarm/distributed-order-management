package com.example.ordermanagement.order;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class OrderService {
    public static final String ORDER_EVENTS_TOPIC = "orders.events";
    private final OrderRepository repository;
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;

    public OrderService(OrderRepository repository, KafkaTemplate<String, OrderResponse> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        if (request.type() == OrderType.MARKET && request.limitPrice() != null) {
            throw new IllegalArgumentException("Market orders cannot have a limit price");
        }
        if (request.type() == OrderType.LIMIT && request.limitPrice() == null) {
            throw new IllegalArgumentException("Limit orders require a limit price");
        }
        if (repository.findByClientOrderId(request.clientOrderId()).isPresent()) {
            return OrderResponse.from(repository.findByClientOrderId(request.clientOrderId()).orElseThrow());
        }
        try {
            Order order = repository.save(new Order(request.clientOrderId(), request.symbol(), request.side(),
                    request.type(), request.quantity(), request.limitPrice()));
            OrderResponse response = OrderResponse.from(order);
            kafkaTemplate.send(ORDER_EVENTS_TOPIC, order.getId().toString(), response);
            return response;
        } catch (DataIntegrityViolationException duplicate) {
            return OrderResponse.from(repository.findByClientOrderId(request.clientOrderId()).orElseThrow());
        }
    }

    @Transactional(readOnly = true)
    public OrderResponse find(UUID id) {
        return repository.findById(id).map(OrderResponse::from)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(UUID id) { super("Order not found: " + id); }
    }
}
