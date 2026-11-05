# Phase 1: Correctness and reliability

## Completed

- [x] Version database schema with Flyway.
- [x] Configure Hibernate with `ddl-auto: validate` so application startup detects schema drift.
- [x] Add database constraints for order enums and positive numeric values.

## Next milestones

- Add an outbox table and publish order events reliably after the database transaction commits.
- Add API tests for valid orders, invalid orders, duplicate client order IDs, and missing orders.
- Add integration tests using PostgreSQL and Kafka/Testcontainers.
- Add structured error responses and correlation IDs.
