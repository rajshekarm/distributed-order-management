package com.example.ordermanagement.order;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders", indexes = @Index(name = "idx_orders_client_order_id", columnList = "clientOrderId", unique = true))
public class Order {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String clientOrderId;
    @Column(nullable = false)
    private String symbol;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderSide side;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderType type;
    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal quantity;
    @Column(precision = 19, scale = 6)
    private BigDecimal limitPrice;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    protected Order() { }

    public Order(String clientOrderId, String symbol, OrderSide side, OrderType type,
                 BigDecimal quantity, BigDecimal limitPrice) {
        this.id = UUID.randomUUID();
        this.clientOrderId = clientOrderId;
        this.symbol = symbol.toUpperCase();
        this.side = side;
        this.type = type;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.status = OrderStatus.RECEIVED;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() { return id; }
    public String getClientOrderId() { return clientOrderId; }
    public String getSymbol() { return symbol; }
    public OrderSide getSide() { return side; }
    public OrderType getType() { return type; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getLimitPrice() { return limitPrice; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setStatus(OrderStatus status) { this.status = status; this.updatedAt = Instant.now(); }
}
