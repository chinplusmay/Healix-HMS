# 🏥 Healix HMS — Microservices-based Hospital Management System

## Overview

Healix HMS is a scalable Hospital Management System built using a modern Java microservices architecture.

The project demonstrates:

* API Gateway pattern
* Service Discovery using Eureka
* JWT Authentication & Authorization
* Inter-service communication using OpenFeign
* Layered architecture
* RESTful APIs
* Centralized routing
* Distributed backend system design

This project was built to learn and implement production-style backend engineering concepts using Spring Boot and Spring Cloud.

---

# 🚀 Tech Stack

## Backend

* Java 17
* Spring Boot 3.5.x
* Spring Security
* Spring Data JPA
* Hibernate
* Spring Cloud Gateway
* Eureka Discovery Server
* OpenFeign
* Maven
* Lombok

## Database

* H2 Database (Development)
* MySQL (Production-ready configuration)

## Architecture

* Microservices
* REST APIs
* JWT Authentication
* Service Discovery
* API Gateway
* Layered Architecture

---

# 🧩 Microservices

## 1. Discovery Server

Service registry using Netflix Eureka.

Responsibilities:

* Service registration
* Service discovery
* Registry management

Port:

```txt
8761
```

---

## 2. API Gateway

Central entry point for all client requests.

Responsibilities:

* Request routing
* Service discovery routing
* Centralized API access
* Future JWT validation

Port:

```txt
8081
```

---

## 3. Auth Service

Handles authentication and authorization.

Features:

* User registration
* User login
* JWT token generation
* Role-based authorization
* BCrypt password hashing

Roles:

* ADMIN
* DOCTOR
* PATIENT

Port:

```txt
8082
```

---

## 4. Patient Service

Manages patient records.

Features:

* Create patient
* Update patient
* Delete patient
* Get patient details

Port:

```txt
8083
```

---

## 5. Doctor Service

Manages doctor records.

Features:

* Create doctor
* Update doctor
* Delete doctor
* Doctor availability management

Port:

```txt
8084
```

---

## 6. Appointment Service

Handles appointment booking and validation.

Features:

* Create appointments
* Validate patient existence
* Validate doctor existence
* Appointment status management
* Inter-service communication using OpenFeign

Appointment Status:

* SCHEDULED
* COMPLETED
* CANCELLED

Port:

```txt
8085
```

---

# 🏗️ Architecture

```txt
Client
   ↓
API Gateway
   ↓
---------------------------------
|       |         |             |
Auth   Patient   Doctor   Appointment
Service Service  Service     Service
                    ↓
              OpenFeign Calls
```

All services register themselves with Eureka Discovery Server.

---

# 🔐 Authentication Flow

```txt
User Login
    ↓
Auth Service validates credentials
    ↓
JWT token generated
    ↓
Client stores token
    ↓
Token sent with requests
```

---

# 🌐 Inter-Service Communication

Appointment Service communicates with:

* Patient Service
* Doctor Service

using OpenFeign clients.

Example:

```txt
Appointment Service
        ↓
Verify Patient Exists
        ↓
Verify Doctor Exists
        ↓
Create Appointment
```

---

# 📌 Key Features

* Microservices architecture
* API Gateway routing
* Eureka service discovery
* JWT authentication
* Role-based authorization
* OpenFeign inter-service communication
* Global exception handling
* Validation using Jakarta Validation
* Layered architecture
* RESTful APIs
* H2 + MySQL profile support
* Centralized routing through gateway

---

# 📂 Project Structure

```txt
hospital-management-system/
│
├── discovery-server
├── api-gateway
├── auth-service
├── patient-service
├── doctor-service
└── appointment-service
```

---

# ▶️ How to Run

## Step 1 — Start Discovery Server

```bash
cd discovery-server
./mvnw spring-boot:run
```

Open:

```txt
http://localhost:8761
```

---

## Step 2 — Start API Gateway

```bash
cd api-gateway
./mvnw spring-boot:run
```

---

## Step 3 — Start Remaining Services

Run individually:

```bash
cd auth-service
./mvnw spring-boot:run
```

```bash
cd patient-service
./mvnw spring-boot:run
```

```bash
cd doctor-service
./mvnw spring-boot:run
```

```bash
cd appointment-service
./mvnw spring-boot:run
```

---

# 📬 Sample APIs

## Register User

```http
POST /api/v1/auth/register
```

---

## Login User

```http
POST /api/v1/auth/login
```

---

## Create Patient

```http
POST /api/v1/patients
```

---

## Create Doctor

```http
POST /api/v1/doctors
```

---

## Create Appointment

```http
POST /api/v1/appointments
```

Sample Request:

```json
{
  "patientId": 1,
  "doctorId": 1,
  "appointmentDate": "2026-05-20T10:30:00Z",
  "status": "SCHEDULED",
  "notes": "Regular checkup"
}
```

---

# 🧠 Concepts Implemented

* Microservices Architecture
* API Gateway Pattern
* Service Discovery
* JWT Authentication
* Role-based Authorization
* Feign Client Communication
* Distributed Validation
* Exception Handling
* DTO Pattern
* Layered Architecture
* REST API Design
* Database Profiles

---

# 🔮 Future Improvements

* Docker & Docker Compose
* Swagger/OpenAPI documentation
* Redis caching
* Kafka notifications
* Kubernetes deployment
* CI/CD pipeline
* React frontend
* Appointment slot management
* Payment integration

---

# 👨‍💻 Author

Built as a backend microservices learning project using Spring Boot and Spring Cloud.
