# PredictiveShard AI

An educational, local-first distributed-system reliability simulator. It predicts simulated shard overload from correlated telemetry, explores downstream cascade paths in a weighted dependency graph, and recommends safe traffic redistribution.

## Quick start

```bash
docker compose up --build
```

Open `http://localhost:5173`. Seeded demo accounts are `admin@predictiveshard.ai` and `operator@predictiveshard.ai`, both with `Password@123`.

The platform is intentionally a research/student prototype. Its data is simulator generated, its predictive signals are not causal proof, and it does not represent any company's proprietary infrastructure.

## Architecture

`React UI → Spring Boot API → PostgreSQL (source of truth)` with Redis as an optional cache, Kafka-ready event configuration, and a FastAPI prediction runtime. If ML is unavailable, the backend uses a deterministic weighted fallback predictor with the same response schema.

## Demo

Login as the operator, open Simulation, run `HOT_SHARD` with a seed, then inspect Predictions, Incidents, and Remediation. The simulator concentrates traffic on `product-shard-03`; the system creates a high-risk prediction and proposes a limited redistribution to healthy sibling shards.

## Safety and limitations

Automatic remediation is off by default. Only traffic redistribution is supported, each action is capped at 25%, and remediation never runs shell commands, changes credentials/schema, deletes data, or shuts down a cluster.
