# LLMChat – Lightweight Inference Logging System

## Features
- Multi-turn chatbot
- JWT authentication
- Conversation history
- Inference logging
- OpenRouter integration
- Docker Compose support
- React frontend
- Spring Boot backend

## Architecture
Frontend → Backend → OpenRouter API
                  ↓
          Inference Logging
                  ↓
              Database

## Tech Stack
Frontend:
- React + Vite

Backend:
- Spring Boot
- Spring Security
- JWT

Database:
- PostgreSQL/MySQL

LLM:
- OpenRouter API

## Setup

### Backend
mvn spring-boot:run

### Frontend
npm install
npm run dev

### Docker
docker-compose up --build

## Environment Variables
OPENROUTER_API_KEY=your_key
JWT_SECRET=your_secret

## Database Design
User
ChatMessage
InferenceLog

## Tradeoffs
- Simple synchronous logging
- Limited conversation context
- Basic architecture for assignment scope

## Improvements
- Kafka event streaming
- Redis caching
- Kubernetes deployment
- WebSocket streaming
- Multi-provider support
- PII redaction
