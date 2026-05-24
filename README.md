# LLMChat

A lightweight LLM chatbot platform with inference logging and ingestion pipeline.

## Features

- Multi-turn chatbot conversations
- JWT Authentication
- Conversation persistence
- Inference logging
- Metadata ingestion
- Docker support
- Swagger API docs

---

# Architecture Overview

Frontend:
- React + Vite

Backend:
- Spring Boot

Database:
- MySQL

LLM Provider:
- OpenRouter API

---

# Inference Logging Flow

1. User sends message from frontend
2. Backend calls OpenRouter API
3. Wrapper captures metadata:
   - latency
   - model
   - provider
   - token usage
   - timestamps
   - request status
4. Metadata stored in MySQL
5. Chat response returned to frontend

---

# Database Design

## User
Stores user authentication data.

## ChatMessage
Stores:
- user messages
- assistant responses
- conversation IDs

## InferenceLog
Stores:
- model
- provider
- latency
- token usage
- timestamps
- errors
- request status

---

# Setup Instructions

## Backend

```bash
./mvnw spring-boot:run
```

## Frontend

```bash
cd frontend
npm install
npm run dev
```

## Docker

```bash
docker-compose up --build
```

---

# Environment Variables

Create application.properties:

```properties
openrouter.api.key=YOUR_OPENROUTER_API_KEY
jwt.secret=YOUR_JWT_SECRET
```

---

# Swagger

Open:

```bash
http://localhost:8080/swagger-ui.html
```

---

# Tradeoffs Made

- Simple synchronous ingestion
- Minimal schema design
- Basic context memory
- Single-node architecture

---

# Scaling Considerations

- Kafka/RabbitMQ for async ingestion
- Redis caching
- Distributed logging
- Kubernetes deployment

---

# Failure Handling

- Request timeout handling
- Error logging
- Database validation
- API fallback assumptions

---

# Improvements With More Time

- Streaming responses
- Multi-provider support
- Dashboard analytics
- PII redaction
- Event-driven ingestion
- Self-hosted Kubernetes deployment

---

# Demo

Add screenshots or Loom video here.
