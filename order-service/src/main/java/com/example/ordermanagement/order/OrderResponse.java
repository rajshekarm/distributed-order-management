package com.example.ordermanagement.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(UUID id, String clientOrderId, String symbol, OrderSide side,
                            OrderType type, BigDecimal quantity, BigDecimal limitPrice,
                            OrderStatus status, Instant createdAt, Instant updatedAt) {
    public static OrderResponse from(Order o) {
        return new OrderResponse(o.getId(), o.getClientOrderId(), o.getSymbol(), o.getSide(), o.getType(),
                o.getQuantity(), o.getLimitPrice(), o.getStatus(), o.getCreatedAt(), o.getUpdatedAt());
    }
}
