# Architecture

The platform is split into independently deployable stages:

1. `order-service` receives API requests, applies request-level validation, persists orders, and publishes `orders.events`.
2. `validation-service` will consume received orders and publish validation outcomes.
3. `routing-service` will select an execution venue or simulated market.
4. `execution-service` will simulate fills and publish lifecycle updates.
5. `common-events` contains versioned event contracts shared by the services.

Kafka is the asynchronous boundary. PostgreSQL is the source of record, Redis is intended for idempotency/hot state, and Prometheus/Grafana provide operational visibility.
