# Distributed Order Management System

Event-driven order management platform organized as independently deployable services.

## Repository layout

- `order-service` — public order API, persistence, and event producer
- `validation-service` — validation processing boundary
- `routing-service` — execution venue routing boundary
- `execution-service` — fill/execution simulation boundary
- `common-events` — shared event contracts
- `infrastructure` — Docker Compose and monitoring configuration
- `load-tests` — k6/Gatling test scenarios
- `docs` — architecture and design notes

## Run the current vertical slice

```bash
docker compose -f infrastructure/docker-compose.yml up -d postgres redis kafka
cd order-service
mvn spring-boot:run
```

The order API is available at `POST /api/v1/orders`; health and metrics are exposed at `/actuator/health` and `/actuator/prometheus`.
