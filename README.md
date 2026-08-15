# PredictiveShard AI

PredictiveShard AI is an educational, local-first distributed-system reliability simulator. It predicts simulated shard overload from correlated telemetry, analyzes downstream dependency risk, and recommends safe traffic redistribution to prevent cascading failures before they happen.

This project is intended for learning, demonstration, and experimentation. It does not represent production infrastructure from any real company and is not connected to proprietary systems or customer data.

## Overview

The platform models a sharded microservice environment and simulates realistic operational risk patterns such as:

- hot shard overload
- queue growth
- latency spikes
- retry amplification
- dependency cascade propagation
- remediation recommendation
- post-remediation verification

The system collects telemetry, estimates future overload risk, and recommends controlled redistribution as a low-risk mitigation step.

## Key Features

- React frontend for operational dashboards and simulation controls
- Spring Boot API for predictions, incidents, analytics, and remediation workflows
- FastAPI ML service for predictive risk scoring
- Deterministic fallback model when the ML runtime is unavailable
- Simulation of hot-shard and failure scenarios
- Dependency graph and cascade analysis
- Risk-aware traffic redistribution optimization
- Audit logging and incident tracking
- Local development setup without Docker

## Architecture

The application follows a three-part architecture:

- Frontend: React + Vite
- Backend: Java 21 + Spring Boot 3
- ML service: Python 3.11 + FastAPI

Runtime dependencies typically include:

- PostgreSQL as the source of truth
- Redis for caching and optional shared state
- Kafka for event-driven telemetry processing
- Prometheus/Grafana for observability

## Tech Stack

- Java 21
- Spring Boot 3
- Maven
- React
- Vite
- Python 3.11
- FastAPI
- PostgreSQL
- Redis
- Kafka
- Prometheus
- Grafana

## Prerequisites

Before running the app locally, install:

- Java 21
- Maven 3.9+
- Node.js 18+
- npm 9+
- Python 3.11+
- PostgreSQL 16
- Redis 7
- Kafka or a compatible broker
