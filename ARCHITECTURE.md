# Architecture Notes

# Ingestion Flow

1. User sends a message from the frontend UI
2. Spring Boot backend receives the request
3. Chat service forwards request to OpenRouter API
4. Wrapper/interceptor captures inference metadata
5. Metadata is validated and transformed
6. Inference logs are persisted in MySQL
7. Chat response is returned to frontend
8. Dashboard APIs aggregate analytics metrics

---

# Logging Strategy

The lightweight inference wrapper captures:

- provider
- model
- latency
- timestamps
- token usage
- request status
- errors
- session IDs
- input/output previews

Logs are stored synchronously in MySQL using structured entities.

Sensitive user data is intentionally minimized in logs to avoid storing unnecessary personal information.

---

# Scaling Considerations

Current implementation is designed as a lightweight single-node architecture optimized for simplicity and rapid development.

Future scalability improvements may include:

- Kafka/RabbitMQ based event streaming
- Async ingestion workers
- Redis caching layer
- Horizontal scaling of backend services
- Read replicas for analytics queries
- Kubernetes deployment for orchestration
- Centralized observability using Prometheus + Grafana

---

# Failure Handling Assumptions

- API failures are captured and logged
- Timeout errors are handled gracefully
- Invalid payloads are rejected during validation
- Database failures return safe server responses
- Wrapper ensures inference metadata logging even on partial failures
- Backend avoids exposing internal exceptions to clients

---

# Tradeoffs

The project prioritizes:

- Simplicity
- Fast iteration
- Clear architecture
- Ease of deployment
- Developer productivity

The following advanced features were intentionally simplified or deferred:

- Distributed event streaming
- Full multi-provider orchestration
- Kubernetes production deployment
- Advanced PII redaction pipelines
- High-throughput asynchronous ingestion

These decisions were made to keep the implementation lightweight and maintainable within the project timeline.


# Architecture Overview

The system consists of:

- React frontend for chatbot UI and analytics dashboard
- Spring Boot backend for chat orchestration
- OpenRouter integration layer for LLM inference
- Lightweight inference logging wrapper
- MySQL database for chat history and inference logs
- Docker Compose based local deployment

# Future Improvements

- Streaming responses using Server-Sent Events (SSE)
- Multi-provider failover routing
- Token-level analytics
- Real-time dashboards
- Kubernetes deployment
- Async ingestion pipeline
- PII redaction middleware
