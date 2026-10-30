package com.example.ordermanagement.events;

import java.time.Instant;
import java.util.UUID;

/** Stable event envelope shared by order processing services. */
public record OrderEvent(UUID orderId, String eventType, Instant occurredAt, String payload) { }
