# Inventory Management Platform 🚀
**Spring Boot · Kafka · Keycloak · Microservices**

This project is a **learning-oriented backend platform** built to explore **real-world backend concepts**, not just a simple REST API.

It combines:
- **Synchronous communication** using REST (service-to-service calls)
- **Asynchronous communication** using Apache Kafka (event-driven)
- **Centralized security** using Keycloak (OAuth2 / JWT)
- **API Gateway** as a single entry point

---

## 🧠 What I Wanted to Learn with This Project

- How microservices communicate **synchronously vs asynchronously**
- How Kafka is used to decouple services
- How JWT authentication works internally
- Why **Keycloak** is preferred over custom JWT logic in real projects
- How an API Gateway routes and secures traffic
- How configuration (ports, hosts, profiles) changes in distributed systems

---

## 🏗️ Architecture Overview

### Services

- **api_gateway**
    - Entry point for all requests
    - Routes `/api/**` to internal services
    - Secured with Keycloak (JWT validation)

- **product-service**
    - Manages products
    - Publishes product events to Kafka

- **inventory_service**
    - Maintains inventory state
    - Consumes Kafka events from product-service

- **order_service**
    - Creates orders
    - Uses REST (synchronous) calls to product-service to validate products
    - Publishes order events to Kafka

- **notification_service**
    - Consumes Kafka events
    - Stores / processes notifications

---

## 🔁 Communication Patterns

### ✅ Synchronous (REST)
Used when **immediate validation is required**.

Example:
- `order_service → product-service`
- Product existence is checked before creating an order

### ✅ Asynchronous (Kafka)
Used when **decoupling and scalability** are desired.

Examples:
- Product created → inventory updates
- Order created → notification processing

---

## 🔐 Security

### Current approach (Keycloak)
All services are secured using **Keycloak** with:
- OAuth2 Resource Server
- JWT validation
- Centralized user & role management

Each service validates JWT tokens issued by Keycloak.

---

### Previous approach (replaced)
Before Keycloak, this project used a **custom JWT implementation**:
- `JwtAuthFilter`
- `JwtService`
- `/auth/login` endpoint generating tokens manually

This was intentionally replaced to learn **industry-standard authentication** with Keycloak.

---

## 🧰 Tech Stack

- Java / Spring Boot
- Spring Cloud Gateway
- Spring Security (OAuth2 Resource Server)
- Apache Kafka (Confluent images)
- Keycloak
- MySQL
- JPA / Hibernate
- Docker & Docker Compose (for local dev)

---

## 🔌 Ports Used (Local Development)

| Component | Port |
|---------|------|
| API Gateway | 8080 |
| Product Service | 8082 |
| Inventory Service | 8083 |
| Order Service | 8084 |
| Notification Service | 8085 |
| Keycloak | 8088 |
| Kafka | 9092 |
| Kafka UI (optional) | 8089 |
| MySQL | 3306 |

---

## ▶️ Running the Project (Recommended Way)

### 1️⃣ Start infrastructure (Kafka, Keycloak, MySQL)

```bash
docker compose up -d 
```




### This starts:


- Kafka (Confluent)
- Zookeeper
- Keycloak
- Apache Kafka (Confluent images)
- Keycloak
- MySQL
---
### 2️⃣ Run the services
Each service can be run:
- from your IDE
or
- via Docker (using the provided Dockerfile)
  
Use the **docker** Spring profile to ensure services resolve:
- **kafka:9092**
- **keycloak:8080**
- internal service hostnames

---

### 🔑 Keycloak Notes
**Keycloak** runs on http://localhost:8088

Realm and client configuration can be exported and committed

This makes the project **plug-and-play** for anyone cloning the repo

 ---

## ⚠️ Project Status
This project is **not production-ready by design.**

Its goal is to:

    experiment
    break things
    understand why they break
    fix them properly

---
## 🧑‍💻 Author

Built as a learning project to deeply understand:

- distributed systems
- messaging
- authentication
- Spring ecosystem best practices

