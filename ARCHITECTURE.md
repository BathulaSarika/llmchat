# Architecture Notes

## Ingestion Flow

1. Frontend sends user message
2. Spring Boot backend receives request
3. OpenRouter service calls LLM provider
4. Wrapper captures metadata
5. Metadata sent to ingestion service
6. Inference logs stored in database
7. Chat response returned to frontend

---

# Logging Strategy

The wrapper captures:

- provider
- model
- latency
- timestamps
- token usage
- request status
- errors
- session IDs

Logs are stored synchronously in MySQL.

---

# Scaling Considerations

Current implementation is single-node.

Future improvements:
- Kafka queue
- Async ingestion workers
- Redis caching
- Horizontal scaling
- Kubernetes deployment

---

# Failure Handling Assumptions

- API failures are logged
- Timeout errors captured
- Invalid payloads rejected
- Database failures return safe errors

---

# Tradeoffs

Chosen:
- Simplicity
- Faster implementation
- Readable architecture

Avoided:
- Distributed systems complexity
- Event streaming overhead
