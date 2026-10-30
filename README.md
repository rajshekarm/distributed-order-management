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
- `trading-simulator` — React + TypeScript client for creating and tracking orders
- `docs` — architecture and design notes

## Run the current vertical slice

```bash
docker compose -f infrastructure/docker-compose.yml up -d postgres redis kafka
cd order-service
mvn spring-boot:run
```

The order API is available at `POST /api/v1/orders`; health and metrics are exposed at `/actuator/health` and `/actuator/prometheus`.

## Run the trading simulator

```bash
cd trading-simulator
npm install
npm run dev
```

Open `http://localhost:5173`. The Vite development proxy forwards `/api` requests to the order service on port `8080`.


# Distributed Order Management System

A scalable, event-driven Order Management System built using **Java, Spring Boot, Apache Kafka, and PostgreSQL** to simulate how financial trading platforms process and manage orders.

## Overview

In financial trading systems, thousands of orders are processed every second. These systems must be fast, reliable, scalable, and capable of handling failures without losing or duplicating orders.

This project simulates a real-world trading order processing system using a **microservices architecture**, where independent services communicate asynchronously through Apache Kafka.

The system manages the complete order lifecycle, from order creation and validation to routing and execution.

## Why Are We Building This?

The goal is to understand and implement core software engineering concepts used in large-scale distributed systems.

- **Scalability:** Handle high volumes of orders efficiently.
- **Distributed Systems:** Build independent microservices that communicate through events.
- **Reliability:** Handle failures, retries, and duplicate messages safely.
- **Performance:** Optimize throughput, latency, and resource utilization.
- **System Design:** Apply real-world architectural patterns to backend applications.

## Tech Stack

- **Backend:** Java, Spring Boot, REST APIs
- **Messaging:** Apache Kafka
- **Database:** PostgreSQL
- **Caching:** Redis
- **Infrastructure:** Docker, Docker Compose
- **Monitoring:** Prometheus, Grafana
- **Testing:** JUnit, Testcontainers, k6
- **CI/CD:** GitHub Actions

## System Architecture

The application consists of four core microservices:

1. **Order Service:** Receives orders, stores order details, and tracks order status.
2. **Validation Service:** Validates orders against predefined business rules.
3. **Routing Service:** Determines the appropriate simulated execution destination.
4. **Execution Service:** Simulates order execution and publishes execution results.

Apache Kafka enables asynchronous communication between services.

**Order Flow:**

`Client → Order Service → Kafka → Validation → Routing → Execution → Order Status Update`

## What I Aim to Learn

Through this project, I aim to strengthen my understanding of:

- Java backend development and Spring Boot.
- Event-driven microservices architecture.
- Kafka producers, consumers, and message processing.
- Database design and consistency in distributed systems.
- Fault tolerance, idempotency, and error handling.
- Performance testing, monitoring, and system scalability.

## Project Goal

Build a reliable, high-performance backend system that demonstrates how modern financial platforms manage large volumes of transactions using distributed, event-driven architecture.

**Focus:** Clean architecture, scalability, reliability, and production-oriented software engineering.

---

*This is a learning and portfolio project that uses simulated trading orders and does not execute real financial transactions.*
