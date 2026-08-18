# PROJECT SPECIFICATION FOR A CODING AGENT

## Project Name 

**PredictiveShard AI — Proactive Distributed-System Failure Prevention Platform**

## Purpose of This Document

Hand this specification to a coding agent and it must be able to build PredictiveShard AI from scratch with complete, functional, reproducible behavior.

PredictiveShard AI is a production-style educational distributed-system simulator and AI-assisted infrastructure reliability platform that predicts future shard overload and failure, identifies possible cascading-failure paths, calculates risk, determines a low-cost preventive traffic redistribution plan, and verifies whether the preventive action actually reduced the predicted risk.

The system is an **original student/research prototype**.

It must not claim to reproduce or outperform the internal infrastructure of Amazon, Google, Microsoft, Netflix, or any other company.

The project demonstrates the engineering concept:

> **Predict a distributed-system failure before it occurs, understand how the failure could propagate, and determine the lowest-cost safe intervention before the failure happens.**

---

# ================================================================================

# IMPORTANT WORKSPACE INSTRUCTIONS

# ================================================================================

The workspace already contains three folders:

```
client/
server/
ml-service/
```

## STRICT RULES

1. ALL frontend code MUST be created inside `client/`.

2. ALL Java backend code MUST be created inside `server/`.

3. ALL Python ML code MUST be created inside `ml-service/`.

4. Root-level files are permitted only for infrastructure/configuration/documentation explicitly required by this specification.

5. Maintain strict separation of concerns.

6. Use Java 21 + Spring Boot 3 for the backend.

7. Use React + Vite + JavaScript/JSX for the frontend.

8. Use Python 3.11+ + FastAPI for the ML service.

9. Use PostgreSQL as the authoritative persistent database.

10. Use Redis only for caching, short-lived state, coordination, and performance optimization.

11. Use Apache Kafka for telemetry/event streaming.

12. Use Docker Compose for complete local development.

13. Provide Kubernetes manifests for educational/local deployment.

14. Generate complete production-quality code.

15. No pseudo-code.

16. No placeholder implementations.

17. No TODO implementations for required functionality.

18. Every API defined in this specification must be implemented.

19. Every UI page defined in this specification must be implemented.

20. Every algorithm defined in this specification must have executable code and automated tests.

21. All secrets and infrastructure credentials must be supplied through environment variables.

22. The system MUST remain usable when the ML service is unavailable.

23. ML failure MUST trigger the deterministic fallback predictor.

24. Redis failure MUST degrade gracefully to PostgreSQL where practical.

25. Kafka consumer failure MUST use Kafka retry/replay semantics rather than silently dropping events.

26. The simulator MUST be deterministic when a random seed is supplied.

27. The simulator MUST support both healthy and failure scenarios.

28. Replaying a simulation with the same seed and configuration MUST reproduce the same logical telemetry sequence.

29. AI MUST solve a meaningful prediction problem and must not be decorative.

30. Graph algorithms MUST remain independently implemented and testable.

31. Optimization MUST remain independently implemented and testable.

32. ML must not contain the graph traversal or traffic-allocation logic.

33. All important mutations MUST generate audit events.

34. All important asynchronous operations MUST have a correlation ID.

35. All APIs return JSON except:

    * health/actuator endpoints
    * CSV exports if implemented
    * PDF/file responses if implemented
    * streaming/SSE responses if implemented

36. PostgreSQL is the source of truth.

37. Redis MUST NOT be treated as authoritative persistent storage.

38. The project MUST run locally without AWS, Azure, GCP, or paid services.

39. Docker Compose MUST provide the complete local environment.

40. The README MUST document:

    * architecture
    * data flow
    * API
    * database
    * Kafka
    * Redis
    * ML methodology
    * feature engineering
    * model evaluation
    * fallback behavior
    * graph algorithms
    * optimization
    * failure scenarios
    * testing
    * performance
    * Docker
    * Kubernetes
    * limitations
    * demo walkthrough

41. The project MUST NOT imply access to proprietary infrastructure, customer data, internal APIs, or confidential systems.

42. Automatic remediation MUST be disabled by default.

43. Automatic remediation MUST only be permitted for traffic redistribution.

44. No automatic remediation may execute arbitrary shell commands.

45. No automatic remediation may modify database schema.

46. No automatic remediation may delete application data.

47. No automatic remediation may shut down the entire cluster.

48. No automatic remediation may modify credentials.

---

# ================================================================================

# PROJECT OVERVIEW

# ================================================================================

Build a full-stack AI-powered distributed-system reliability platform that simulates a sharded microservice application and proactively prevents simulated failures.

The system automates:

* Distributed workload simulation
* Realistic correlated telemetry generation
* Kafka telemetry streaming
* Telemetry validation
* Time-window aggregation
* Temporal feature engineering
* AI-based future overload prediction
* Failure probability prediction
* Estimated time-to-failure prediction
* Explainable prediction signals
* Dependency graph construction
* Cascading failure analysis
* Blast-radius calculation
* Risk propagation
* Traffic redistribution optimization
* Preventive remediation recommendation
* Remediation approval
* Controlled remediation execution
* Post-remediation verification
* Incident creation and tracking
* Historical analytics
* Model performance monitoring
* Simulation replay
* Audit logging
* Real-time infrastructure visualization

The central processing pipeline is:

```
Telemetry
    ->
Aggregation
    ->
Feature Engineering
    ->
AI Prediction
    ->
Risk Analysis
    ->
Dependency Graph
    ->
Cascade Analysis
    ->
Optimization
    ->
Remediation
    ->
Verification
    ->
Analytics
```

The platform resembles a combination of:

* Distributed-system simulator
* Infrastructure observability console
* Predictive ML platform
* Graph-based failure-analysis engine
* Traffic optimization controller
* Incident-management system

## CENTRAL PRODUCT QUESTION

> Can the system identify a likely overload before it happens and determine a lower-cost preventive action?

---

# ================================================================================

# PROBLEM STATEMENT

# ================================================================================

Traditional threshold-based infrastructure monitoring is primarily reactive.

For example:

```
CPU > 80%
    ->
Alert
```

This approach can identify an unhealthy state, but it does not inherently answer:

* Is the system trending toward failure?
* How quickly could the failure happen?
* Which shard is likely to fail first?
* Which downstream services could be affected?
* How large could the blast radius become?
* Which healthy shard can safely absorb traffic?
* How much traffic should be moved?
* What is the lowest-cost intervention?
* Did the intervention actually reduce future failure risk?

PredictiveShard AI addresses this simulated problem using three independently testable capabilities:

1. **Temporal ML prediction**
2. **Dependency-graph failure propagation**
3. **Constrained traffic optimization**

AI predicts future risk.

Graph algorithms determine possible propagation.

Optimization determines a feasible preventive action.

These responsibilities MUST remain separate.

---

# ================================================================================

# WHY AI IS REQUIRED

# ================================================================================

The ML component must not simply reproduce a static CPU threshold.

The simulator generates temporal telemetry where several signals interact:

```
requestRate increasing
      ->
queueLength increasing
      ->
CPU utilization increasing
      ->
latency increasing
      ->
retryRate increasing
      ->
effective requestRate increasing
      ->
further load increase
```

A shard may still be below a static CPU threshold while its temporal trajectory indicates an approaching overload.

The model therefore receives both:

* Current infrastructure state
* Historical temporal behavior

The ML problem is:

> Predict whether the shard will enter an overload state within the configured prediction window.

The project MUST explicitly explain that the prototype is evaluating this approach on simulator-generated data.

---

# ================================================================================

# TECH STACK

# ================================================================================

## FRONTEND

* React 18+
* Vite
* JavaScript/JSX
* React Router
* TanStack React Query
* Zustand
* Axios
* TailwindCSS
* Recharts
* lucide-react

Scripts:

```
npm run dev
npm run build
npm run preview
```

Development command:

```
vite --host 0.0.0.0
```

---

## BACKEND

* Java 21
* Spring Boot 3
* Spring Web
* Spring Validation
* Spring Security
* Spring Data JPA
* PostgreSQL driver
* Spring Kafka
* Spring Data Redis
* Jackson
* JWT library
* Lombok
* Spring Boot Actuator

Commands:

```
./mvnw spring-boot:run
./mvnw test
./mvnw package
```

---

## ML SERVICE

* Python 3.11+
* FastAPI
* Uvicorn
* NumPy
* Pandas
* scikit-learn
* XGBoost
* joblib
* Pydantic
* pytest

Commands:

```
python train.py
python -m uvicorn app.main:app --host 0.0.0.0 --port 8000
pytest
```

---

## INFRASTRUCTURE

* PostgreSQL 16
* Redis 7
* Apache Kafka
* Kafka UI
* Docker
* Docker Compose
* Kubernetes

---

## OBSERVABILITY

* Spring Boot Actuator
* Prometheus
* Grafana
* Optional OpenTelemetry

---

# ================================================================================

# ENVIRONMENT CONFIGURATION

# ================================================================================

## BACKEND

The backend must support:

```
SERVER_PORT=8080

DATABASE_URL=jdbc:postgresql://localhost:5432/predictiveshard
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

KAFKA_BOOTSTRAP_SERVERS=localhost:9092

ML_SERVICE_URL=http://localhost:8000

JWT_SECRET=change-me-in-production
JWT_EXPIRATION=86400000

PREDICTION_WINDOW_SECONDS=60
TELEMETRY_WINDOW_SECONDS=300

PREDICTION_THRESHOLD=0.70
MIN_PREDICTION_CONFIDENCE=0.60

SIMULATOR_ENABLED=true
AUTO_REMEDIATION_ENABLED=false

CORS_ALLOWED_ORIGINS=http://localhost:5173

SPRING_PROFILES_ACTIVE=local
```

---

## ML SERVICE

```
PORT=8000

MODEL_PATH=models/failure_predictor.joblib
FAILURE_MODEL_PATH=models/failure_predictor.joblib
TTF_MODEL_PATH=models/time_to_failure.joblib

MODEL_VERSION=1.0.0

PREDICTION_THRESHOLD=0.70
MIN_CONFIDENCE=0.60

FALLBACK_ENABLED=true
```

---

## FRONTEND

```
VITE_API_URL=http://localhost:8080/api
```

---

## DOCKER

```
POSTGRES_DB=predictiveshard
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

---

# ================================================================================

# HEALTH API

# ================================================================================

Backend:

```
GET /api/health
```

Response:

```
{
  "status": "ok",
  "service": "PredictiveShard AI API",
  "mlRuntime": "xgboost-or-fallback",
  "kafka": "connected-or-disconnected",
  "redis": "connected-or-disconnected",
  "database": "connected-or-disconnected",
  "autoRemediationEnabled": false
}
```

Health must not return credentials or secrets.

Spring Actuator endpoints should additionally expose:

```
/actuator/health
/actuator/info
/actuator/metrics
/actuator/prometheus
```

---

# ================================================================================

# AUTHENTICATION MODULE

# ================================================================================

Features:

* Register
* Login
* Current user
* JWT authentication
* Protected routes
* Role-based authorization

User:

```
{
  id,
  name,
  email,
  passwordHash,
  role,
  createdAt,
  updatedAt
}
```

Roles:

```
ADMIN
OPERATOR
VIEWER
```

## AUTHORIZATION

ADMIN:

* Full access
* Cluster management
* Simulation
* Prediction access
* Incident management
* Remediation
* Audit logs
* Analytics

OPERATOR:

* View clusters
* View topology
* Run simulations
* View predictions
* View incidents
* Recommend remediation
* Approve remediation
* Execute allowed remediation
* View analytics

VIEWER:

* Read-only dashboard
* Read-only topology
* Read-only telemetry
* Read-only predictions
* Read-only incidents
* Read-only analytics

VIEWER MUST NOT:

* Start simulation
* Approve remediation
* Execute remediation
* Roll back remediation
* Modify clusters
* Modify graph
* Modify services
* Modify shards

## APIs

```
POST /api/auth/register
```

Request:

```
{
  name,
  email,
  password
}
```

Response:

```
201
{
  user,
  token
}

POST /api/auth/login

GET /api/auth/me
```

All non-auth routes require:

```
Authorization: Bearer <token>
```

Password:

* Minimum 8 characters
* Secure bcrypt hashing
* Never return passwordHash

---

# ================================================================================

# CLUSTER MANAGEMENT MODULE

# ================================================================================

Cluster:

```
{
  id,
  name,
  environment,
  region,
  status,
  totalNodes,
  totalServices,
  totalShards,
  createdAt,
  updatedAt
}
```

Statuses:

```
HEALTHY
DEGRADED
CRITICAL
UNKNOWN
```

APIs:

```
GET    /api/clusters
POST   /api/clusters
GET    /api/clusters/:id
PATCH  /api/clusters/:id
DELETE /api/clusters/:id
```

Simulation:

```
POST /api/clusters/:id/simulate
```

Request:

```
{
  scenario,
  durationSeconds,
  trafficMultiplier,
  randomSeed,
  noiseLevel
}
```

The cluster status must be recalculated from current shard/service health.

---

# ================================================================================

# SERVICE MODULE

# ================================================================================

Service:

```
{
  id,
  clusterId,
  name,
  version,
  replicas,
  cpuCapacity,
  memoryCapacity,
  requestCapacity,
  currentStatus,
  createdAt,
  updatedAt
}
```

Example services:

```
api-gateway
product-service
inventory-service
search-service
cart-service
checkout-service
recommendation-service
payment-service
database-service
```

APIs:

```
GET    /api/clusters/:clusterId/services
GET    /api/services/:id
POST   /api/clusters/:clusterId/services
PATCH  /api/services/:id
DELETE /api/services/:id
```

A service MUST be connected to one or more shards.

---

# ================================================================================

# NODE / SHARD MODULE

# ================================================================================

Shard:

```
{
  id,
  serviceId,
  name,
  nodeId,
  capacity,
  currentLoad,
  cpuUsage,
  memoryUsage,
  networkUsage,
  queueLength,
  requestRate,
  p95Latency,
  cacheMissRate,
  dbConnectionUsage,
  retryRate,
  status
}
```

Statuses:

```
HEALTHY
WARNING
OVERLOADED
FAILED
RECOVERING
```

---

# ================================================================================

# CONSISTENT HASHING

# ================================================================================

The system MUST implement deterministic consistent-hashing-style key assignment.

Requirements:

* Deterministic hash function
* Configurable virtual nodes
* Add shard
* Remove shard
* Recalculate affected key ranges
* Estimate migration cost
* Preserve unaffected assignments

APIs:

```
GET /api/services/:serviceId/shards
GET /api/shards/:id
POST /api/services/:serviceId/shards
PATCH /api/shards/:id
DELETE /api/shards/:id
```

The hashing algorithm MUST be independently testable.

Test:

```
Add one shard to a cluster.
```

Expected:

* Existing unaffected keys remain assigned.
* Only an expected subset of keys moves.

---

# ================================================================================

# DEPENDENCY GRAPH MODULE

# ================================================================================

Represent service dependencies as a directed weighted graph.

Edge:

```
{
  id,
  clusterId,
  sourceServiceId,
  targetServiceId,
  dependencyType,
  weight,
  averageLatency,
  timeoutMs,
  failurePropagationFactor
}
```

Example:

```
api-gateway -> product-service
api-gateway -> search-service
cart-service -> inventory-service
checkout-service -> inventory-service
inventory-service -> database-service
```

APIs:

```
GET /api/clusters/:clusterId/graph

POST /api/clusters/:clusterId/graph/edges

DELETE /api/graph/edges/:id
```

Algorithms MUST include:

* BFS
* DFS
* Dijkstra
* Topological ordering
* Cycle detection
* Weighted risk propagation

---

# ================================================================================

# GRAPH ANALYSIS CONTRACT

# ================================================================================

Graph analysis result:

```
{
  rootNode,
  affectedNodes[],
  paths[],
  totalRisk,
  estimatedBlastRadius
}
```

The graph engine MUST NOT depend on the ML model implementation.

It receives prediction risk as an input and independently calculates propagation.

---

# ================================================================================

# TELEMETRY MODULE

# ================================================================================

Telemetry:

```
{
  id,
  eventId,
  timestamp,
  clusterId,
  serviceId,
  shardId,
  requestRate,
  cpuUsage,
  memoryUsage,
  networkUsage,
  queueLength,
  p50Latency,
  p95Latency,
  p99Latency,
  cacheHitRate,
  cacheMissRate,
  dbConnectionUsage,
  dbLatency,
  retryRate,
  errorRate,
  activeConnections
}
```

Telemetry MUST be immutable after ingestion.

`eventId` MUST be unique.

Duplicate telemetry events MUST NOT be persisted twice.

APIs:

```
GET /api/telemetry
```

Query:

```
clusterId
serviceId?
shardId?
from?
to?
limit?
page?

GET /api/telemetry/:shardId/latest

GET /api/telemetry/:shardId/aggregate
```

Query:

```
window=1m|5m|15m

POST /api/telemetry/ingest
```

This endpoint is intended for internal ingestion.

---

# ================================================================================

# TELEMETRY SIMULATOR

# ================================================================================

The simulator is a critical project component.

It MUST generate realistic correlated telemetry.

It MUST NOT generate independent random values for every metric.

Simulation parameters:

```
{
  clusterId,
  scenario,
  durationSeconds,
  trafficMultiplier,
  randomSeed,
  noiseLevel
}
```

Supported scenarios:

1. NORMAL_TRAFFIC
2. TRAFFIC_SPIKE
3. CACHE_DEGRADATION
4. DATABASE_LATENCY
5. RETRY_STORM
6. HOT_SHARD
7. DEPENDENCY_FAILURE
8. CASCADING_FAILURE
9. RANDOM_MIXED_LOAD

---

## NORMAL_TRAFFIC

Characteristics:

* Stable request rate
* Low retry rate
* Stable latency
* Small natural variance
* No failure

---

## TRAFFIC_SPIKE

Sequence:

```
Request rate increases
    ->
Queue increases
    ->
CPU increases
    ->
Latency increases
```

---

## CACHE_DEGRADATION

Sequence:

```
Cache hit rate decreases
    ->
Database requests increase
    ->
DB latency increases
    ->
Service latency increases
```

---

## DATABASE_LATENCY

Sequence:

```
DB latency rises
    ->
Dependent service latency rises
    ->
Retries increase
```

---

## RETRY_STORM

Sequence:

```
Initial latency increase
    ->
Clients retry
    ->
Effective request rate increases
    ->
Additional load
    ->
Further latency
    ->
Positive feedback loop
```

---

## HOT_SHARD

One shard receives disproportionately high traffic.

Other shards remain relatively healthy.

This is the primary demonstration scenario for traffic redistribution.

---

## DEPENDENCY_FAILURE

A downstream service becomes degraded.

Upstream services experience:

* Increased latency
* Increased retries
* Increased error rate

---

## CASCADING_FAILURE

A root service degrades.

The dependency graph propagates risk.

Multiple downstream services become unhealthy.

---

# ================================================================================

# CORRELATED TELEMETRY REQUIREMENT

# ================================================================================

Telemetry must reflect realistic relationships.

Example:

```
requestRate ↑
    ->
queueLength ↑
    ->
cpuUsage ↑
    ->
p95Latency ↑
    ->
retryRate ↑
    ->
effectiveRequestRate ↑
```

Noise must be configurable.

Seeded simulation:

```
same configuration
+
same seed
=
same logical telemetry sequence
```

---

# ================================================================================

# SIMULATION STATE MACHINE

# ================================================================================

Simulation statuses:

```
CREATED
RUNNING
PAUSED
COMPLETED
STOPPED
FAILED
```

Controls:

```
START
PAUSE
RESUME
STOP
RESET
```

Invalid transitions MUST return validation errors.

Example:

```
COMPLETED -> RESUME
```

must fail.

Simulation state is stored in PostgreSQL and cached in Redis.

---

# ================================================================================

# KAFKA EVENT PIPELINE

# ================================================================================

Topics:

```
predictiveshard.telemetry
predictiveshard.predictions
predictiveshard.incidents
predictiveshard.remediation
predictiveshard.audit
```

Dead-letter:

```
predictiveshard.telemetry.dlq
```

Telemetry producer:

* Simulator generates telemetry.
* Simulator publishes events to Kafka.
* Partition key = clusterId + shardId.

Telemetry consumer:

1. Receive event
2. Validate schema
3. Check eventId/idempotency
4. Persist telemetry
5. Update Redis latest-state cache
6. Determine whether prediction window is eligible
7. Trigger prediction asynchronously
8. Produce prediction event

Kafka consumer requirements:

* Idempotent processing
* Retry with backoff
* Dead-letter handling
* Structured logs
* Correlation ID
* Event ID
* No silent data loss

Prediction MUST NOT block telemetry persistence.

---

# ================================================================================

# REDIS USAGE

# ================================================================================

Redis keys:

```
ps:shard:{id}:latest
ps:prediction:{shardId}:latest
ps:cluster:{id}:health
ps:incident:{id}
ps:simulation:{id}:state
ps:idempotency:{eventId}
```

TTL:

```
latest telemetry = 5 minutes
prediction cache = 10 minutes
simulation state = simulation duration + 10 minutes
idempotency record = 24 hours
```

Redis is a cache and coordination layer.

PostgreSQL remains authoritative.

If Redis is unavailable:

* Continue operation.
* Read PostgreSQL where practical.
* Mark cache as degraded.
* Never treat Redis loss as data loss.

---

# ================================================================================

# AI PREDICTION MODULE

# ================================================================================

The AI prediction engine predicts:

1. Probability of overload
2. Probability of failure
3. Estimated time to failure
4. Contributing signals
5. Prediction confidence

Prediction request:

```
{
  shardId,
  timestamp,
  features: {
    requestRate,
    cpuUsage,
    memoryUsage,
    networkUsage,
    queueLength,
    p95Latency,
    p99Latency,
    cacheMissRate,
    dbConnectionUsage,
    dbLatency,
    retryRate,
    errorRate,
    activeConnections
  },
  historicalWindow: [...]
}
```

Prediction response:

```
{
  shardId,
  overloadProbability,
  failureProbability,
  estimatedTimeToFailureSeconds,
  confidence,
  riskLevel,
  contributingFactors[],
  modelVersion,
  fallbackUsed,
  generatedAt
}
```

---

# ================================================================================

# PREDICTION TARGET DEFINITIONS

# ================================================================================

## OVERLOAD TARGET

A historical window receives:

```
overload_within_prediction_window = 1
```

if the shard enters the defined overload state within:

```
PREDICTION_WINDOW_SECONDS
```

Otherwise:

```
overload_within_prediction_window = 0
```

Overload MUST be defined using the simulated capacity model, not simply an arbitrary ML threshold.

A shard is considered overloaded when one or more configured capacity constraints are violated for the required consecutive simulation interval.

---

## FAILURE TARGET

A window receives:

```
failure_within_prediction_window = 1
```

if the shard enters:

```
FAILED
```

within the prediction window.

Otherwise:

```
0
```

---

## TIME-TO-FAILURE TARGET

For positive failure examples:

```
TTF =
seconds between prediction timestamp
and first simulated FAILED state
```

For windows with no future failure:

* Either exclude them from TTF regression training
* Or use a configured right-censoring strategy

The implementation MUST document the chosen strategy.

---

# ================================================================================

# RISK LEVELS

# ================================================================================

Default thresholds:

```
LOW       < 0.40
MEDIUM    0.40 - 0.69
HIGH      0.70 - 0.89
CRITICAL  >= 0.90
```

Thresholds MUST be configurable.

---

# ================================================================================

# ML SERVICE

# ================================================================================

FastAPI:

```
GET /health
```

Response:

```
{
  status,
  modelLoaded,
  modelVersion,
  fallbackEnabled
}
```

---

```
POST /predict
```

Request:

```
{
  features,
  historicalWindow
}
```

Response:

```
{
  overloadProbability,
  failureProbability,
  estimatedTimeToFailureSeconds,
  confidence,
  contributingFactors,
  modelVersion,
  fallbackUsed
}
```

---

```
POST /predict/batch
```

Response:

```
{
  predictions[]
}
```

---

```
POST /explain
```

Response:

```
{
  features[]
}
```

The ML service MUST load persisted models when available.

If the model cannot be loaded:

* Do not crash.
* Set modelLoaded=false.
* Use deterministic fallback.
* Set fallbackUsed=true.
* Return the same prediction schema.

---

# ================================================================================

# ML FEATURE ENGINEERING

# ================================================================================

Base features:

```
requestRate
cpuUsage
memoryUsage
networkUsage
queueLength
p95Latency
p99Latency
cacheMissRate
dbConnectionUsage
dbLatency
retryRate
errorRate
activeConnections
```

Derived temporal features:

```
cpuSlope
latencySlope
queueSlope
requestRateSlope
retryRateSlope
dbLatencySlope
cacheMissSlope
```

Rolling features:

```
rollingCpuMean
rollingLatencyMean
rollingQueueMean
rollingRetryMean
```

Acceleration features:

```
latencyAcceleration
queueAcceleration
```

Growth:

```
requestGrowthRate
```

Slope:

```
slope =
  (latestValue - oldestValue)
  /
  timeDelta
```

The implementation MUST safely handle:

* Missing values
* Short histories
* Duplicate timestamps
* Zero time deltas
* Out-of-order events
* NaN
* Infinity
* Empty histories

---

# ================================================================================

# ML DATASET GENERATION

# ================================================================================

The training pipeline MUST generate data from the simulator.

Pipeline:

```
Generate scenario
    ->
Generate telemetry
    ->
Construct historical windows
    ->
Calculate temporal features
    ->
Label overload
    ->
Label failure
    ->
Calculate TTF
    ->
Train models
    ->
Validate
    ->
Test
    ->
Save model
    ->
Save feature metadata
    ->
Save evaluation metrics
```

---

# ================================================================================

# DATA LEAKAGE PREVENTION

# ================================================================================

The training pipeline MUST NOT randomly mix temporally adjacent windows from the same simulation across train and test.

Training/validation/test splitting must be scenario/run aware.

Preferred:

```
Simulation Runs 1-70%
    ->
Training

Simulation Runs 71-85%
    ->
Validation

Simulation Runs 86-100%
    ->
Test
```

This prevents near-duplicate telemetry windows from appearing in both train and test.

The README MUST explicitly explain this.

---

# ================================================================================

# ML MODELS

# ================================================================================

Primary overload model:

```
XGBoost classifier
```

Target:

```
overload_within_prediction_window
```

Secondary failure model:

```
XGBoost classifier
```

Target:

```
failure_within_prediction_window
```

TTF model:

```
XGBoost regressor
```

Target:

```
estimated seconds until failure
```

If a TTF model is unavailable, the system may calculate a deterministic TTF estimate from risk trajectory.

The model version MUST be persisted with every prediction.

---

# ================================================================================

# MODEL EVALUATION

# ================================================================================

Calculate:

* Accuracy
* Precision
* Recall
* F1
* ROC-AUC
* False Positive Rate
* False Negative Rate

Also calculate:

* Prediction latency
* Model loading time
* Fallback prediction count
* ML availability

The project MUST prioritize:

* Recall
* False-negative reduction

because missing an imminent infrastructure failure is more costly than issuing an additional warning in this simulated environment.

The README MUST explain this trade-off.

---

# ================================================================================

# DETERMINISTIC FALLBACK PREDICTOR

# ================================================================================

The backend/system MUST remain functional without ML.

Normalize features to [0,1].

Fallback risk:

```
risk =
    0.18 * cpuUsage
  \+ 0.15 * queueLength
  \+ 0.15 * p95Latency
  \+ 0.10 * cacheMissRate
  \+ 0.12 * dbLatency
  \+ 0.10 * retryRate
  \+ 0.10 * latencySlope
  \+ 0.10 * queueSlope
```

The exact weights must be centralized in configuration.

The fallback MUST:

* Be deterministic.
* Have no random component.
* Accept the same input schema.
* Return the same output schema.
* Generate contributing factors.
* Generate confidence.
* Generate risk level.
* Set fallbackUsed=true.
* Never crash the application.

Fallback output must be clearly distinguished from an ML prediction.

---

# ================================================================================

# EXPLAINABLE AI

# ================================================================================

Every prediction MUST expose human-readable contributing signals.

Example:

```
{
  "feature": "retryRate",
  "contribution": 0.31,
  "direction": "increasing",
  "explanation":
    "Retry rate increased 38% during the latest window."
}
```

Possible signals:

* CPU utilization rising rapidly
* Queue length accelerating
* Database latency increasing
* Cache miss rate increasing
* Request rate increasing
* Retry amplification detected
* Connection utilization increasing

IMPORTANT:

The system MUST NOT claim that feature importance proves causality.

Use:

```
"contributing signal"
```

instead of:

```
"root cause"
```

unless the statement is based on an independently established graph/dependency relationship.

---

# ================================================================================

# PREDICTION STORAGE

# ================================================================================

Prediction:

```
{
  id,
  clusterId,
  serviceId,
  shardId,
  timestamp,
  overloadProbability,
  failureProbability,
  estimatedTimeToFailureSeconds,
  confidence,
  riskLevel,
  contributingFactors,
  modelVersion,
  fallbackUsed,
  generatedAt
}
```

Indexes:

```
shardId + timestamp
clusterId + timestamp
riskLevel
```

APIs:

```
GET /api/predictions
GET /api/predictions/:shardId/latest
GET /api/predictions/:shardId/history
POST /api/predictions/run
```

Prediction generation MUST be idempotent for the same:

```
shardId + telemetryWindowTimestamp + modelVersion
```

---

# ================================================================================

# FAILURE ANALYSIS MODULE

# ================================================================================

When:

```
overloadProbability >= PREDICTION_THRESHOLD
```

AND:

```
confidence >= MIN_PREDICTION_CONFIDENCE
```

then failure analysis is triggered.

Steps:

1. Identify affected shard.
2. Identify owning service.
3. Load dependency graph.
4. Traverse downstream dependencies.
5. Calculate propagation risk.
6. Calculate blast radius.
7. Generate failure paths.
8. Generate user-impact estimate.
9. Pass analysis to optimizer.

Failure analysis:

```
{
  rootShard,
  rootService,
  riskLevel,
  propagationPaths[],
  affectedServices[],
  affectedShards[],
  blastRadius,
  estimatedUserImpact,
  confidence
}
```

---

# ================================================================================

# CASCADING FAILURE ALGORITHM

# ================================================================================

For each dependency edge:

```
propagationRisk =
    sourceRisk
    *
    dependencyWeight
    *
    failurePropagationFactor
```

Traversal:

1. Start from predicted unhealthy node.
2. Insert node into a priority queue.
3. Highest propagation risk is processed first.
4. Expand downstream dependencies.
5. Stop expansion when risk falls below configured threshold.
6. Maintain visited set.
7. Prevent infinite loops.
8. Record all meaningful propagation paths.
9. Return highest-risk path.
10. Return top 5 paths.

Result:

```
highestRiskPath
topPropagationPaths
affectedNodes
riskPerNode
blastRadius
```

Expected complexity:

```
O((V + E) log V)
```

The README MUST explain the complexity.

---

# ================================================================================

# BLAST RADIUS

# ================================================================================

Blast radius:

```
affected services/shards
/
total services/shards
```

Represent as a percentage.

Example:

```
4 potentially affected shards
/
14 total shards

= 28.57%
```

---

# ================================================================================

# OPTIMIZATION MODULE

# ================================================================================

The optimization engine determines how traffic should be redistributed.

Objective:

```
minimize(
    latencyCost
    +
    overloadRiskCost
    +
    migrationCost
    +
    imbalanceCost
)
```

Subject to:

```
CPU <= capacity

memory <= capacity

requestRate <= requestCapacity

databaseConnections <= connectionCapacity

trafficAllocation >= 0

trafficAllocation <= shardMaximum

sum(trafficAllocation) = 100%
```

---

# ================================================================================

# MANDATORY OPTIMIZATION IMPLEMENTATION

# ================================================================================

The project MUST implement a deterministic constrained heuristic.

Do not leave optimization as optional.

Algorithm:

1. Identify overloaded/high-risk shard.
2. Identify healthy eligible destination shards.
3. Calculate available capacity.
4. Calculate latency cost.
5. Calculate predicted risk.
6. Calculate migration cost.
7. Rank destination shards.
8. Transfer traffic in small increments.
9. Recalculate projected risk.
10. Continue until:

    * risk reaches target,
    * source risk is sufficiently reduced,
    * or no feasible capacity remains.
11. Validate every constraint.
12. Return best feasible allocation.

Candidate ranking:

```
availableCapacity
+
lowerLatency
+
lowerFailureRisk
+
lowerMigrationCost
```

The implementation MUST be deterministic.

Optional linear-programming experimentation may be documented, but it is not required for the final application.

---

# ================================================================================

# OPTIMIZATION RESULT

# ================================================================================

Return:

```
{
  allocations[],
  objectiveScoreBefore,
  objectiveScoreAfter,
  estimatedRiskReduction,
  estimatedMigrationCost,
  feasible,
  reason
}
```

Each allocation:

```
{
  shardId,
  currentTrafficPercent,
  recommendedTrafficPercent,
  deltaPercent,
  expectedLatency,
  expectedRisk
}
```

If no feasible solution exists:

```
feasible = false
```

and:

```
reason = "NO_FEASIBLE_REMEDIATION"
```

---

# ================================================================================

# REMEDIATION MODULE

# ================================================================================

Supported action types:

```
REDISTRIBUTE_TRAFFIC
SCALE_OUT
REDUCE_REQUEST_RATE
WARM_CACHE
CIRCUIT_BREAK_DEPENDENCY
NO_ACTION
```

Only:

```
REDISTRIBUTE_TRAFFIC
```

may be automatically executed.

All other actions are recommendations only.

Remediation:

```
{
  id,
  incidentId,
  actionType,
  sourceShardId,
  targetShardId,
  percentage,
  status,
  expectedRiskReduction,
  actualRiskReduction,
  migrationCost,
  createdAt,
  executedAt
}
```

Statuses:

```
RECOMMENDED
APPROVED
EXECUTED
FAILED
ROLLED_BACK
```

---

# ================================================================================

# AUTO-REMEDIATION SAFETY

# ================================================================================

Default:

```
AUTO_REMEDIATION_ENABLED=false
```

When false:

```
System recommends.
Operator approves.
Operator executes.
```

When true:

Only REDISTRIBUTE_TRAFFIC may execute automatically.

Safety conditions:

* Maximum movement per action = 25%.
* Target shard must have sufficient capacity.
* Target shard cannot already be HIGH/CRITICAL.
* Projected risk must decrease.
* Allocation must remain within constraints.
* Every action must generate audit event.
* Original allocation must be stored.
* Rollback information must be stored.
* Correlation ID must be stored.

Automatic remediation MUST NOT:

* Delete data.
* Modify database schema.
* Change credentials.
* Shut down the entire cluster.
* Execute arbitrary shell commands.
* Execute arbitrary external APIs.

---

# ================================================================================

# INCIDENT MODULE

# ================================================================================

Incident:

```
{
  id,
  clusterId,
  rootServiceId,
  rootShardId,
  severity,
  status,
  predictionId,
  blastRadius,
  failurePaths,
  recommendedAction,
  startedAt,
  resolvedAt,
  createdAt
}
```

Severity:

```
LOW
MEDIUM
HIGH
CRITICAL
```

Status:

```
DETECTED
INVESTIGATING
MITIGATING
RESOLVED
FALSE_POSITIVE
```

Incident creation condition:

```
overloadProbability >= configured threshold
```

AND:

```
prediction confidence >= minimum confidence
```

Duplicate incidents MUST be prevented for:

```
same shard
+
same active prediction window
```

Prediction errors MUST NOT create incidents.

---

# ================================================================================

# INCIDENT APIs

# ================================================================================

```
GET   /api/incidents
GET   /api/incidents/:id
PATCH /api/incidents/:id
```

Remediation:

```
POST /api/incidents/:id/recommend
POST /api/incidents/:id/approve
POST /api/incidents/:id/execute
POST /api/incidents/:id/rollback
```

Authorization:

* VIEWER = read only
* OPERATOR = approve/execute
* ADMIN = full access

---

# ================================================================================

# INCIDENT VERIFICATION

# ================================================================================

After remediation:

1. Continue telemetry collection.
2. Wait for post-remediation window.
3. Run prediction again.
4. Compare pre-remediation risk.
5. Compare post-remediation risk.
6. Calculate risk reduction.
7. Validate traffic allocation.
8. Mark remediation successful or failed.

Example:

```
Before:
    overload probability = 91%

After:
    overload probability = 28%

Risk reduction:
    63 percentage points
```

Result:

```
SUCCESS
```

If risk does not decrease by the configured minimum:

```
remediation = FAILED
```

or:

```
recommend another action
```

---

# ================================================================================

# ANALYTICS MODULE

# ================================================================================

Calculate:

* Total predictions
* High-risk predictions
* Critical predictions
* True positive incidents
* False positives
* False negatives
* Average warning time
* Average predicted time to failure
* Average risk reduction
* Average remediation cost
* Incidents prevented
* Remediation success rate
* Top contributing signals
* Most unstable services
* Most unstable shards
* Average cluster utilization
* Fallback prediction count
* ML prediction count
* ML availability

APIs:

```
GET /api/analytics/overview
GET /api/analytics/predictions
GET /api/analytics/incidents
GET /api/analytics/remediation
GET /api/analytics/model
```

---

# ================================================================================

# MODEL PERFORMANCE MODULE

# ================================================================================

Display:

```
Accuracy
Precision
Recall
F1
ROC-AUC
False Positive Rate
False Negative Rate
```

Runtime:

```
Average prediction latency
ML service availability
Fallback prediction count
ML prediction count
Model version
```

The dashboard MUST clearly distinguish:

```
ML prediction
```

from:

```
deterministic fallback prediction
```

---

# ================================================================================

# AUDIT LOG MODULE

# ================================================================================

Audit:

```
{
  id,
  userId,
  action,
  resourceType,
  resourceId,
  metadataJson,
  correlationId,
  timestamp
}
```

Examples:

```
CLUSTER_CREATED
CLUSTER_UPDATED
SIMULATION_STARTED
SIMULATION_PAUSED
SIMULATION_RESUMED
SIMULATION_STOPPED
PREDICTION_GENERATED
INCIDENT_CREATED
REMEDIATION_RECOMMENDED
REMEDIATION_APPROVED
REMEDIATION_EXECUTED
REMEDIATION_ROLLED_BACK
```

Only ADMIN and OPERATOR may access audit logs.

Passwords, JWT secrets, database passwords, and private credentials MUST NEVER be logged.

API:

```
GET /api/audit-logs
```

---

# ================================================================================

# REAL-TIME DASHBOARD

# ================================================================================

The dashboard must display:

* Cluster health
* Active incidents
* High-risk shards
* Predicted failures
* Prevented incidents
* Average warning time
* Risk reduction
* Remediation success rate
* Current traffic
* CPU
* Memory
* p95 latency
* Retry rate
* Queue length

Example:

```
+-------------------------------------------------------------+
| PredictiveShard AI                 Cluster: Demo Commerce    |
+-------------------------------------------------------------+
| Health | Risk | Incidents | Predicted | Prevented          |
|  92%   | HIGH |    1     |     7     |    14              |
+-------------------------------------------------------------+

LIVE CLUSTER TOPOLOGY

API Gateway
   /    \
  /      \
Product  Search
  |
Inventory
  |
Database

PREDICTED RISKS

product-shard-03
HIGH
91%
TTF: 47 sec

RECOMMENDED REMEDIATION

Move 17% traffic from product-shard-03
to healthy destination shards.
```

Dashboard must update through:

* Polling
* or Server-Sent Events

WebSocket is optional.

---

# ================================================================================

# CLUSTER TOPOLOGY VISUALIZATION

# ================================================================================

Create an interactive dependency graph.

Node displays:

* Service name
* Health
* Risk
* CPU
* p95 latency

Statuses:

```
HEALTHY
WARNING
HIGH RISK
CRITICAL
FAILED
```

Edges display:

* Direction
* Weight
* Latency

Clicking a node opens:

* Telemetry
* Prediction
* Contributing signals
* Failure paths
* Recommended remediation

The UI MUST NOT rely on color alone.

Every status must also include:

* text
* icon
* accessible label

---

# ================================================================================

# PREDICTION TIMELINE

# ================================================================================

Display:

* Actual CPU
* Actual p95 latency
* Actual queue
* Predicted overload probability
* Prediction threshold
* Actual overload event

Example:

```
100% |                       X
     |                     X
 80% |                  X
     |               X
 60% |------ threshold
     |          X
 40% |     X
     +-------------------------
        -5m -4m -3m -2m -1m now
```

Also display:

```
Prediction generated 47 seconds
before simulated overload.
```

---

# ================================================================================

# FAILURE EXPLORER

# ================================================================================

Page:

```
/incidents
```

Display:

* Incident ID
* Severity
* Root service
* Root shard
* Predicted time to failure
* Blast radius
* Remediation status
* Created time

Incident details:

1. Prediction
2. Contributing signals
3. Dependency graph
4. Cascade paths
5. Recommended action
6. Optimization result
7. Before/after telemetry
8. Incident timeline
9. Audit history

---

# ================================================================================

# SIMULATION CONTROL PANEL

# ================================================================================

Page:

```
/simulation
```

Controls:

* Cluster
* Scenario
* Duration
* Traffic multiplier
* Random seed
* Noise level

Buttons:

```
START
PAUSE
RESUME
STOP
RESET
```

Scenario description must be shown.

Example:

```
HOT_SHARD

Concentrates traffic on one shard to demonstrate
predictive overload detection and preventive
traffic redistribution.
```

Display:

* Simulation status
* Elapsed time
* Progress
* Events generated
* Current cluster health
* Current high-risk shard

---

# ================================================================================

# REPLAY MODULE

# ================================================================================

Every completed simulation may be stored as a replay.

Replay:

```
{
  id,
  name,
  scenario,
  randomSeed,
  duration,
  trafficMultiplier,
  noiseLevel,
  startedAt,
  completedAt
}
```

APIs:

```
GET  /api/replays
GET  /api/replays/:id
POST /api/replays/:id/start
```

Replay requirements:

* Same seed
* Same scenario
* Same duration
* Same traffic multiplier
* Same noise level

must reproduce the same logical telemetry sequence.

Replay MUST reproduce:

* Telemetry
* Predictions
* Failure analysis
* Recommendations

Model-version changes must be recorded.

If the model version differs, the UI must identify that predictions may differ.

---

# ================================================================================

# ORCHESTRATION ENGINE

# ================================================================================

Pipeline:

```
Telemetry
   ->
Aggregation
   ->
Prediction
   ->
Failure Analysis
   ->
Optimization
   ->
Remediation
   ->
Verification
   ->
Analytics
```

Each stage must contain:

```
status
startedAt
completedAt
errorState
correlationId
```

Pipeline statuses:

```
WAITING
RUNNING
COMPLETED
FAILED
FALLBACK
```

Every pipeline execution must be traceable by correlation ID.

A failure in one stage must not corrupt persisted state from previous completed stages.

---

# ================================================================================

# JAVA BACKEND PACKAGE STRUCTURE

# ================================================================================

Use:

```
com.predictiveshard

    config
    security
    controller
    service
    repository
    entity
    dto
    mapper
    kafka
    redis
    prediction
    graph
    optimization
    simulation
    incident
    analytics
    audit
    exception
    util
```

Controllers:

```
AuthController
ClusterController
ServiceController
ShardController
TelemetryController
PredictionController
GraphController
IncidentController
RemediationController
SimulationController
AnalyticsController
ReplayController
AuditController
HealthController
```

---

# ================================================================================

# DATABASE MODELS

# ================================================================================

Create JPA entities:

```
User
Cluster
ServiceInstance
Shard
DependencyEdge
TelemetryRecord
Prediction
Incident
Remediation
Simulation
Replay
AuditLog
```

Relationships MUST use foreign keys.

Use migrations rather than relying solely on implicit schema generation.

For local development, Hibernate may use:

```
ddl-auto=update
```

only if explicitly configured for local profile.

A migration-based schema is preferred for reproducibility.

---

# ================================================================================

# DATABASE DETAILS

# ================================================================================

## USER

```
id
name
email
passwordHash
role
createdAt
updatedAt
```

## CLUSTER

```
id
name
environment
region
status
createdAt
updatedAt
```

## SERVICE

```
id
clusterId
name
version
replicas
cpuCapacity
memoryCapacity
requestCapacity
status
createdAt
updatedAt
```

## SHARD

```
id
serviceId
nodeId
name
capacity
currentLoad
status
createdAt
updatedAt
```

## DEPENDENCY EDGE

```
id
clusterId
sourceServiceId
targetServiceId
dependencyType
weight
averageLatency
timeoutMs
failurePropagationFactor
```

## TELEMETRY

```
id
eventId
timestamp
clusterId
serviceId
shardId
requestRate
cpuUsage
memoryUsage
networkUsage
queueLength
p50Latency
p95Latency
p99Latency
cacheHitRate
cacheMissRate
dbConnectionUsage
dbLatency
retryRate
errorRate
activeConnections
```

## PREDICTION

```
id
clusterId
serviceId
shardId
timestamp
overloadProbability
failureProbability
estimatedTimeToFailureSeconds
confidence
riskLevel
contributingFactorsJson
modelVersion
fallbackUsed
generatedAt
```

## INCIDENT

```
id
clusterId
rootServiceId
rootShardId
severity
status
predictionId
blastRadius
failurePathsJson
recommendedActionJson
startedAt
resolvedAt
createdAt
```

## REMEDIATION

```
id
incidentId
actionType
sourceShardId
targetShardId
percentage
status
expectedRiskReduction
actualRiskReduction
migrationCost
previousAllocationJson
createdAt
executedAt
```

## SIMULATION

```
id
clusterId
scenario
durationSeconds
trafficMultiplier
randomSeed
noiseLevel
status
startedAt
completedAt
```

## REPLAY

```
id
simulationId
name
scenario
randomSeed
durationSeconds
trafficMultiplier
noiseLevel
createdAt
```

## AUDIT LOG

```
id
userId
action
resourceType
resourceId
metadataJson
correlationId
timestamp
```

---

# ================================================================================

# DATABASE INDEXES

# ================================================================================

Telemetry:

```
shardId + timestamp
clusterId + timestamp
eventId
```

Prediction:

```
shardId + timestamp
clusterId + timestamp
riskLevel
```

Incident:

```
clusterId + status
rootShardId + startedAt
```

Remediation:

```
incidentId
status
```

Audit:

```
userId + timestamp
resourceType + resourceId
correlationId
```

---

# ================================================================================

# COMPLETE API CONTRACT

# ================================================================================

## AUTH

```
POST /api/auth/register
POST /api/auth/login
GET  /api/auth/me
```

## HEALTH

```
GET /api/health
```

## CLUSTERS

```
GET    /api/clusters
POST   /api/clusters
GET    /api/clusters/:id
PATCH  /api/clusters/:id
DELETE /api/clusters/:id
POST   /api/clusters/:id/simulate
```

## SERVICES

```
GET    /api/clusters/:clusterId/services
POST   /api/clusters/:clusterId/services
GET    /api/services/:id
PATCH  /api/services/:id
DELETE /api/services/:id
```

## SHARDS

```
GET    /api/services/:serviceId/shards
POST   /api/services/:serviceId/shards
GET    /api/shards/:id
PATCH  /api/shards/:id
DELETE /api/shards/:id
```

## GRAPH

```
GET    /api/clusters/:clusterId/graph
POST   /api/clusters/:clusterId/graph/edges
DELETE /api/graph/edges/:id
```

## TELEMETRY

```
GET  /api/telemetry
GET  /api/telemetry/:shardId/latest
GET  /api/telemetry/:shardId/aggregate
POST /api/telemetry/ingest
```

## PREDICTIONS

```
GET  /api/predictions
GET  /api/predictions/:shardId/latest
GET  /api/predictions/:shardId/history
POST /api/predictions/run
```

## INCIDENTS

```
GET   /api/incidents
GET   /api/incidents/:id
PATCH /api/incidents/:id
```

## REMEDIATION

```
GET  /api/remediation
POST /api/incidents/:id/recommend
POST /api/incidents/:id/approve
POST /api/incidents/:id/execute
POST /api/incidents/:id/rollback
```

## SIMULATIONS

```
GET  /api/simulations
POST /api/simulations
GET  /api/simulations/:id
POST /api/simulations/:id/start
POST /api/simulations/:id/pause
POST /api/simulations/:id/resume
POST /api/simulations/:id/stop
POST /api/simulations/:id/reset
```

## REPLAYS

```
GET  /api/replays
GET  /api/replays/:id
POST /api/replays/:id/start
```

## ANALYTICS

```
GET /api/analytics/overview
GET /api/analytics/predictions
GET /api/analytics/incidents
GET /api/analytics/remediation
GET /api/analytics/model
```

## AUDIT

```
GET /api/audit-logs
```

---

# ================================================================================

# API ERROR FORMAT

# ================================================================================

All errors:

```
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "...",
  "path": "...",
  "correlationId": "..."
}
```

Handle:

* Validation errors
* Authentication failures
* Authorization failures
* Resource not found
* Duplicate resource
* Kafka unavailable
* Redis unavailable
* PostgreSQL unavailable
* ML unavailable
* Prediction timeout
* Invalid simulation configuration
* Invalid traffic allocation
* No feasible optimization
* Invalid remediation transition

---

# ================================================================================

# FRONTEND PAGES

# ================================================================================

Create:

```
Login
Register
Dashboard
Cluster Overview
Topology
Shard Details
Predictions
Prediction Details
Failure Explorer
Incident Details
Simulation
Replay
Analytics
Model Performance
Audit Logs
Settings
```

Routes:

```
/login
/register
```

Protected:

```
/
/clusters
/clusters/:id
/topology/:id
/shards/:id
/predictions
/predictions/:id
/incidents
/incidents/:id
/simulation
/replays
/analytics
/model
/audit
/settings
```

---

# ================================================================================

# FRONTEND INFRASTRUCTURE

# ================================================================================

`api/client.js`:

* Axios instance
* VITE_API_URL
* JWT interceptor
* 401 logout
* Correlation ID support

`store/authStore.js`:

```
{
  token,
  user,
  setSession,
  logout
}
```

React Query keys:

```
["clusters"]
["cluster", id]
["services", clusterId]
["shards", serviceId]
["topology", clusterId]
["telemetry", shardId]
["predictions"]
["prediction", shardId]
["incidents"]
["incident", id]
["remediation"]
["simulations"]
["replays"]
["analytics"]
["model-performance"]
["audit"]
```

Invalidate relevant queries after mutations.

---

# ================================================================================

# REUSABLE FRONTEND COMPONENTS

# ================================================================================

Create:

```
AppShell
ProtectedRoute
Sidebar
TopBar
HealthBadge
RiskBadge
MetricCard
TelemetryChart
PredictionCard
PredictionTimeline
TopologyGraph
DependencyEdge
ServiceNode
ShardTable
IncidentCard
IncidentTimeline
FailurePath
RemediationCard
OptimizationTable
SimulationControls
SimulationProgress
ReplayCard
EmptyState
ErrorState
LoadingState
ConfirmDialog
AuditTable
ModelStatusBadge
```

---

# ================================================================================

# UI REQUIREMENTS

# ================================================================================

Design:

* Modern infrastructure/SaaS dashboard
* Dark-first professional monitoring UI
* Responsive layout
* Sidebar navigation
* Top command/status bar
* Dense but readable tables
* Subtle borders
* Monitoring charts
* Interactive topology
* Clear severity indicators
* Accessible controls
* Loading states
* Empty states
* Error states
* Toast/success feedback

The application must feel like:

> A real infrastructure operations console

and NOT:

> A generic AI landing page.

Statuses must use:

```
HEALTHY
WARNING
HIGH RISK
CRITICAL
FAILED
```

Do not rely on color alone.

---

# ================================================================================

# DEMO DATA

# ================================================================================

Provide:

```
Demo Commerce Cluster
```

Environment:

```
simulation
```

Region:

```
local
```

Services:

```
api-gateway
product-service
inventory-service
search-service
cart-service
checkout-service
database-service
```

Shards:

```
product-shard-01
product-shard-02
product-shard-03
product-shard-04

inventory-shard-01
inventory-shard-02

database-shard-01
database-shard-02
```

Dependencies:

```
api-gateway
    -> product-service
    -> search-service
    -> cart-service

cart-service
    -> inventory-service

checkout-service
    -> inventory-service
    -> database-service

inventory-service
    -> database-service
```

Demo users:

```
admin@predictiveshard.ai
Password@123

operator@predictiveshard.ai
Password@123
```

Demo seed data MUST be idempotent.

Repeated seed execution must not create duplicates.

---

# ================================================================================

# PRIMARY PORTFOLIO DEMONSTRATION

# ================================================================================

The primary demo is:

```
HOT_SHARD
```

Sequence:

1. Cluster starts healthy.
2. Traffic is evenly distributed.
3. Simulation starts.
4. Traffic gradually concentrates on product-shard-03.
5. CPU increases.
6. Queue increases.
7. Cache misses increase.
8. DB latency begins increasing.
9. Retry rate increases.
10. Temporal ML detects future overload.
11. Dashboard shows HIGH risk.
12. Prediction shows estimated time to failure.
13. Dependency graph calculates potential cascade.
14. Blast radius is calculated.
15. Optimizer finds healthy destination shards.
16. System recommends traffic redistribution.
17. Operator approves.
18. Traffic is redistributed.
19. New telemetry is generated.
20. Prediction is rerun.
21. Overload probability decreases.
22. Before/after risk reduction is displayed.
23. Incident is mitigated/resolved.
24. Analytics records the prevented incident.

This MUST be the polished end-to-end portfolio demonstration.

---

# ================================================================================

# EXAMPLE PREDICTION

# ================================================================================

Input:

```
requestRate = 7842
cpuUsage = 64
queueLength = 812
p95Latency = 182
cacheMissRate = 31
dbConnectionUsage = 78
dbLatency = 94
retryRate = 4.8
```

Output:

```
overloadProbability = 0.91
failureProbability = 0.73
estimatedTimeToFailureSeconds = 47
confidence = 0.88
riskLevel = HIGH
```

Contributing signals:

```
Queue acceleration
Increasing p95 latency
Rising retry rate
Increasing cache misses
High database connection utilization
```

These are example demonstration values, not hard-coded prediction results.

---

# ================================================================================

# EXAMPLE FAILURE ANALYSIS

# ================================================================================

Root:

```
product-shard-03
```

Potential propagation:

```
product-shard-03
    ->
product-service
    ->
inventory-service
    ->
database-service
```

Example blast radius:

```
28%
```

Estimated impact:

```
Product browsing
Inventory availability
Checkout latency
```

The system must calculate these values dynamically.

---

# ================================================================================

# EXAMPLE OPTIMIZATION

# ================================================================================

Current:

```
product-shard-01 = 21%
product-shard-02 = 23%
product-shard-03 = 41%
product-shard-04 = 15%
```

Recommended:

```
product-shard-01 = 25%
product-shard-02 = 29%
product-shard-03 = 24%
product-shard-04 = 22%
```

Action:

```
Move 17% traffic away from product-shard-03.
```

Example expected result:

```
overload probability:
    91% -> 24%

p95 latency:
    412ms -> 176ms

queue:
    1840 -> 640
```

These are demonstration targets.

The implementation must calculate actual values.

---

# ================================================================================

# BACKGROUND JOBS

# ================================================================================

Every 10 seconds:

```
process eligible prediction windows
```

Every 30 seconds:

```
update cluster health
```

Every 1 minute:

```
aggregate telemetry
```

Every 5 minutes:

```
calculate analytics
```

Every 10 minutes:

```
clean expired Redis state
```

Jobs MUST be idempotent.

Kafka-driven processing remains the preferred real-time path.

Spring `@Scheduled` may be used for local mode.

---

# ================================================================================

# JAVA SERVICE LAYER

# ================================================================================

Recommended services:

```
AuthService
ClusterService
ServiceInstanceService
ShardService
TelemetryService
TelemetryAggregationService
PredictionService
MlClientService
FallbackPredictionService
GraphService
CascadeAnalysisService
OptimizationService
RemediationService
IncidentService
SimulationService
ReplayService
AnalyticsService
AuditService
HealthService
```

Each service should have a focused responsibility.

Controllers MUST NOT contain business logic.

---

# ================================================================================

# ML SERVICE STRUCTURE

# ================================================================================

```
ml-service/
    requirements.txt
    train.py
    Dockerfile

    models/
        failure_predictor.joblib
        time_to_failure.joblib
        metadata.json

    app/
        __init__.py
        main.py
        config.py
        schemas.py
        predictor.py
        fallback.py
        features.py
        explain.py
        model_loader.py

    tests/
        test_features.py
        test_fallback.py
        test_predictor.py
        test_explain.py
        test_schemas.py

    data/
        generated/
```

---

# ================================================================================

# FRONTEND PROJECT STRUCTURE

# ================================================================================

```
client/
    package.json
    index.html
    vite.config.js
    tailwind.config.js
    postcss.config.js

    src/
        main.jsx
        router.jsx
        index.css

        api/
            client.js
            queries.js

        store/
            authStore.js

        components/
            AppShell.jsx
            ProtectedRoute.jsx
            Sidebar.jsx
            TopBar.jsx
            HealthBadge.jsx
            RiskBadge.jsx
            MetricCard.jsx
            TelemetryChart.jsx
            PredictionCard.jsx
            PredictionTimeline.jsx
            TopologyGraph.jsx
            ServiceNode.jsx
            ShardTable.jsx
            IncidentCard.jsx
            IncidentTimeline.jsx
            FailurePath.jsx
            RemediationCard.jsx
            OptimizationTable.jsx
            SimulationControls.jsx
            SimulationProgress.jsx
            ReplayCard.jsx
            LoadingState.jsx
            EmptyState.jsx
            ErrorState.jsx
            ConfirmDialog.jsx
            AuditTable.jsx
            ModelStatusBadge.jsx

        pages/
            Login.jsx
            Register.jsx
            Dashboard.jsx
            ClusterOverview.jsx
            Topology.jsx
            ShardDetails.jsx
            Predictions.jsx
            PredictionDetails.jsx
            FailureExplorer.jsx
            IncidentDetails.jsx
            Simulation.jsx
            Replay.jsx
            Analytics.jsx
            ModelPerformance.jsx
            AuditLogs.jsx
            Settings.jsx

        utils/
            format.js
            risk.js
```

---

# ================================================================================

# BACKEND PROJECT STRUCTURE

# ================================================================================

```
server/
    pom.xml
    mvnw
    mvnw.cmd

    src/
        main/
            java/
                com/
                    predictiveshard/
                        PredictiveShardApplication.java

                        config/
                        security/

                        controller/
                        service/
                        repository/
                        entity/
                        dto/
                        mapper/

                        kafka/
                        redis/

                        prediction/
                        graph/
                        optimization/
                        simulation/
                        incident/
                        analytics/
                        audit/

                        exception/
                        util/

            resources/
                application.yml
                application-local.yml
                db/
                    migration/

        test/
            java/
                com/
                    predictiveshard/

    Dockerfile
```

---

# ================================================================================

# ROOT PROJECT STRUCTURE

# ================================================================================

```
PredictiveShard-AI/

    client/
    server/
    ml-service/

    docker-compose.yml
    prometheus.yml

    grafana/
        dashboards/

    k8s/
        namespace.yaml
        configmap.yaml
        secret.example.yaml
        postgres.yaml
        redis.yaml
        kafka.yaml
        ml-service.yaml
        server.yaml
        client.yaml

    README.md
    .env.example
    .gitignore
```

No application source code may be placed directly in the root.

---

# ================================================================================

# DOCKER COMPOSE

# ================================================================================

docker-compose.yml MUST provide:

```
postgres
redis
kafka
kafka-ui
server
ml-service
client
prometheus
grafana
```

Expected local URLs:

```
Frontend:
    http://localhost:5173

Backend:
    http://localhost:8080

ML:
    http://localhost:8000

Kafka UI:
    http://localhost:8085

Prometheus:
    http://localhost:9090

Grafana:
    http://localhost:3000
```

All containers must communicate using Docker service names.

Example:

```
postgres:5432
redis:6379
kafka:9092
ml-service:8000
```

Do not hard-code localhost between containers.

Each major service should have a health check.

The server must wait for PostgreSQL/Kafka/Redis to become reachable before starting dependent functionality.

---

# ================================================================================

# KUBERNETES

# ================================================================================

Provide manifests:

```
namespace.yaml
postgres-deployment.yaml
redis-deployment.yaml
kafka-deployment.yaml
ml-service-deployment.yaml
server-deployment.yaml
client-deployment.yaml
```

Services:

```
postgres
redis
kafka
ml-service
server
client
```

Provide:

* ConfigMap
* Secret example
* Deployment
* Service
* Readiness probe
* Liveness probe

Kubernetes deployment is for educational/local use.

Do not claim production cloud readiness without:

* secret management
* persistent volumes
* TLS
* network policies
* autoscaling
* backup
* disaster recovery
* production Kafka configuration
* production PostgreSQL configuration
* security hardening

---

# ================================================================================

# PROMETHEUS / GRAFANA

# ================================================================================

Expose backend metrics through Actuator.

Track:

* HTTP request count
* HTTP latency
* Kafka consumer metrics
* Prediction latency
* Prediction count
* Fallback prediction count
* Incident count
* Remediation count
* Simulation events
* Error count

Grafana dashboard should display:

* Backend health
* Request rate
* API latency
* Kafka status
* Prediction latency
* ML/fallback distribution
* Active incidents
* Remediation activity

---

# ================================================================================

# PERFORMANCE REQUIREMENTS

# ================================================================================

The local environment should support at least:

```
20 shards
10 services
100 telemetry events/second
```

Telemetry processing MUST be asynchronous.

Prediction MUST NOT block telemetry ingestion.

Target locally:

```
ML prediction API:
    p95 < 500ms

Deterministic fallback:
    p95 < 50ms

Graph analysis:
    < 100ms for demo graph sizes
```

The UI must paginate large telemetry/prediction/incident queries.

The system must avoid loading unlimited telemetry history into memory.

---

# ================================================================================

# RESILIENCE REQUIREMENTS

# ================================================================================

## ML FAILURE

If ML service fails:

* Continue operation.
* Use fallback.
* Mark fallbackUsed=true.
* Record degraded event.
* Continue predictions.

---

## REDIS FAILURE

If Redis fails:

* Continue with PostgreSQL.
* Do not lose persisted telemetry.
* Disable cache-dependent optimization where necessary.
* Surface degraded cache status.

---

## KAFKA FAILURE

If Kafka consumer fails:

* Retry with backoff.
* Kafka retains events.
* Do not duplicate processed events.
* Do not silently discard events.

If Kafka producer fails:

* Record simulator degraded state.
* Retry according to configured policy.
* Development-only direct ingestion fallback may be enabled explicitly.

---

## PREDICTION FAILURE

If prediction fails:

* Store prediction processing failure event.
* Do not create a false incident.
* Surface degraded state.
* Attempt fallback if the failure is ML-specific.

---

## REMEDIATION FAILURE

If remediation fails:

* Mark remediation FAILED.
* Preserve audit log.
* Preserve original allocation.
* Do not silently retry a dangerous action.
* Allow explicit operator retry.

---

# ================================================================================

# LOGGING

# ================================================================================

Use structured JSON logging.

Important events include:

```
correlationId
userId
clusterId
serviceId
shardId
eventId
```

Prediction:

```
predictionId
modelVersion
fallbackUsed
latencyMs
```

Remediation:

```
incidentId
sourceShard
targetShard
percentage
expectedRiskReduction
actualRiskReduction
```

Never log:

* Passwords
* JWT secrets
* Database passwords
* Authorization headers
* Sensitive environment variables

---

# ================================================================================

# SECURITY REQUIREMENTS

# ================================================================================

Authentication:

```
JWT
```

Authorization:

```
ADMIN
OPERATOR
VIEWER
```

Validate:

* IDs
* Numeric ranges
* Percentages
* Simulation duration
* Traffic multiplier
* Random seed
* Noise level
* Prediction thresholds
* Remediation percentages

Automatic traffic movement:

```
0 < percentage <= 25
```

Use parameterized JPA queries.

Never concatenate SQL.

CORS must be configurable.

Production secrets must never be committed.

Provide:

```
.env.example
```

with safe placeholder values.

---

# ================================================================================

# TESTING REQUIREMENTS

# ================================================================================

## BACKEND TESTS

Test:

* Controllers
* Services
* Repositories
* Authentication
* Authorization
* Graph algorithms
* Optimization
* Simulation
* Consistent hashing
* Prediction fallback
* Incident creation
* Remediation state transitions
* Replay determinism

---

## ML TESTS

Test:

* Feature engineering
* Missing values
* Short histories
* Duplicate timestamps
* Out-of-order timestamps
* Model loading
* Model inference
* Fallback predictor
* Explanation generation
* Risk-level classification

---

## FRONTEND TESTS

Test:

* Login
* Dashboard rendering
* Prediction cards
* Incident workflow
* Simulation controls
* Remediation approval
* Risk badges
* Loading states
* Error states

---

## INTEGRATION TESTS

Test:

* Kafka telemetry ingestion
* Prediction flow
* Incident creation
* Failure analysis
* Optimization
* Remediation
* Before/after verification
* Replay

---

# ================================================================================

# ALGORITHM TEST CASES

# ================================================================================

## GRAPH

Graph:

```
A -> B -> C
```

Verify:

```
A reaches C
```

---

## CYCLE

```
A -> B -> C -> A
```

Verify:

```
Cycle detected
```

---

## DISCONNECTED GRAPH

```
A -> B

C -> D
```

Verify:

```
A cannot reach D
```

---

## OPTIMIZATION

One overloaded shard.

Three healthy shards.

Verify:

* Traffic moves away from overloaded shard.
* Destination capacity is respected.
* Total traffic remains 100%.
* Risk decreases.

---

## NO CAPACITY

All shards near capacity.

Verify:

```
NO_FEASIBLE_REMEDIATION
```

---

## CONSISTENT HASHING

Add a shard.

Verify:

* Only expected portion of keys moves.
* Existing unaffected keys remain assigned.

---

## SIMULATION DETERMINISM

Run:

```
HOT_SHARD
seed = 12345
```

twice.

Verify:

```
Same telemetry sequence.
```

---

# ================================================================================

# ACCEPTANCE CRITERIA

# ================================================================================

The build is correct only when ALL criteria hold.

## A — AUTH

* Register works.
* Login issues JWT.
* Protected APIs return 401 without JWT.
* VIEWER cannot execute remediation.
* OPERATOR can approve allowed remediation.
* ADMIN has full access.

## B — CLUSTER

* User can create cluster.
* User can view cluster.
* User can update cluster.
* User can delete cluster where safe.
* Services and shards can be created.

## C — TOPOLOGY

* Dependency graph correctly represents relationships.
* BFS works.
* DFS works.
* Dijkstra works.
* Cycle detection works.
* Blast radius is calculated.

## D — HASHING

* Keys map deterministically.
* Virtual nodes work.
* Adding a shard only moves affected keys.
* Migration cost is calculated.

## E — TELEMETRY

* Simulator generates correlated telemetry.
* Telemetry is persisted.
* Kafka events are consumed.
* Duplicate event IDs do not create duplicate records.
* Redis latest state updates.
* PostgreSQL remains authoritative.

## F — SIMULATION

* NORMAL_TRAFFIC remains healthy.
* HOT_SHARD creates localized overload pressure.
* TRAFFIC_SPIKE increases load progressively.
* RETRY_STORM produces feedback behavior.
* CASCADING_FAILURE propagates across dependencies.
* Seeded simulations are reproducible.

## G — ML

* Training script generates dataset.
* Model can be trained.
* Model is persisted.
* Model can be loaded.
* Prediction endpoint works.
* Overload probability is returned.
* Failure probability is returned.
* TTF is returned.
* Confidence is returned.
* Contributing signals are returned.

## H — ML FALLBACK

With ML service unavailable:

* Backend continues.
* Fallback prediction executes.
* Same response schema is returned.
* fallbackUsed=true.
* UI remains functional.

## I — FEATURE ENGINEERING

* Slopes calculated.
* Rolling metrics calculated.
* Acceleration calculated.
* Missing data handled.
* Short histories handled.
* Duplicate timestamps handled.
* Out-of-order timestamps handled.

## J — PREDICTION

* Predictions are persisted.
* Predictions are indexed.
* Duplicate prediction windows are prevented.
* Prediction history is visible.
* Model/fallback distinction is visible.

## K — FAILURE ANALYSIS

When risk exceeds threshold:

* Failure analysis runs.
* Root shard identified.
* Root service identified.
* Downstream dependencies traversed.
* Propagation paths generated.
* Blast radius calculated.

## L — OPTIMIZATION

* Overloaded shard identified.
* Healthy destinations identified.
* Capacity constraints respected.
* Traffic remains 100%.
* Migration cost calculated.
* Risk reduction calculated.
* NO_FEASIBLE_REMEDIATION works.

## M — REMEDIATION

* Recommendation created.
* Operator can approve.
* Allowed remediation executes.
* Maximum automatic movement = 25%.
* Original allocation is stored.
* Rollback works.
* Failed remediation is recorded.

## N — INCIDENTS

* Incident created only when threshold + confidence requirements are met.
* Duplicate incidents prevented.
* Incident lifecycle works.
* Incident details show prediction and failure analysis.

## O — VERIFICATION

After remediation:

* New telemetry is generated.
* New prediction runs.
* Before/after risk is calculated.
* Risk reduction is displayed.
* Incident becomes resolved when configured success criteria are met.

## P — ANALYTICS

Analytics correctly calculates:

* Total predictions
* High-risk predictions
* Critical predictions
* Incidents
* False positives
* False negatives
* Warning time
* Risk reduction
* Remediation success
* Fallback usage
* Unstable services
* Unstable shards

## Q — UI

Dashboard displays:

* Cluster health
* Incidents
* High-risk shards
* Prediction timeline
* Topology
* Recommended remediation
* Before/after risk

## R — REPLAY

Same simulation:

```
same seed
same configuration
```

must reproduce the same telemetry sequence.

## S — OBSERVABILITY

Prometheus can scrape backend metrics.

Grafana dashboard loads.

Actuator health endpoint works.

## T — DOCKER

Running:

```
docker compose up --build
```

must start the required stack.

## U — KUBERNETES

Manifests are syntactically valid.

Deployments contain:

* readiness probes
* liveness probes
* services
* configuration
* secret references

## V — TESTS

Backend tests pass.

ML tests pass.

Frontend tests pass.

Integration tests pass.

Algorithm tests pass.

---

# ================================================================================

# DEVELOPMENT APPROACH

# ================================================================================

Implement in phases.

## PHASE 1 — BACKEND FOUNDATION

Build:

* Spring Boot
* PostgreSQL
* Redis
* Authentication
* JWT
* RBAC
* Cluster
* Service
* Shard
* REST APIs
* Database migrations
* Exception handling
* Correlation IDs

---

## PHASE 2 — GRAPH ENGINE

Build:

* Dependency graph
* Graph storage
* BFS
* DFS
* Dijkstra
* Cycle detection
* Topological ordering
* Risk propagation
* Topology API
* Topology UI

---

## PHASE 3 — SIMULATOR

Build:

* Telemetry model
* Simulation engine
* All scenarios
* Seeded randomness
* Correlated telemetry
* Simulation state machine
* Replay data

---

## PHASE 4 — KAFKA PIPELINE

Build:

* Kafka topics
* Producer
* Consumer
* Schema validation
* Idempotency
* Retry
* DLQ
* Redis latest-state cache

---

## PHASE 5 — ML DATA PIPELINE

Build:

* Scenario generator
* Dataset generator
* Temporal windows
* Feature engineering
* Labels
* Leakage-safe split
* XGBoost training
* Evaluation
* Model persistence

---

## PHASE 6 — ML INFERENCE

Build:

* FastAPI
* Model loader
* Prediction endpoint
* Batch prediction
* Explain endpoint
* TTF prediction
* Deterministic fallback

---

## PHASE 7 — PREDICTION PIPELINE

Build:

* Prediction service
* Kafka prediction events
* Prediction persistence
* Prediction history
* Prediction dashboard
* Prediction timeline
* Model/fallback distinction

---

## PHASE 8 — FAILURE ANALYSIS

Build:

* Threshold trigger
* Cascade analysis
* Propagation risk
* Blast radius
* Failure paths
* Failure Explorer

---

## PHASE 9 — OPTIMIZATION

Build:

* Capacity model
* Candidate selection
* Deterministic heuristic
* Traffic allocation
* Migration cost
* Risk projection
* No-feasible-solution handling

---

## PHASE 10 — REMEDIATION

Build:

* Recommendations
* Approval workflow
* Controlled execution
* Rollback
* Verification
* Safety limits
* Audit events

---

## PHASE 11 — INCIDENTS + ANALYTICS

Build:

* Incident lifecycle
* Analytics
* Model metrics
* Remediation metrics
* Audit logs
* Historical analysis

---

## PHASE 12 — REPLAY

Build:

* Simulation persistence
* Replay API
* Deterministic replay
* Replay UI
* Model-version awareness

---

## PHASE 13 — OBSERVABILITY

Build:

* Actuator
* Prometheus
* Grafana
* Runtime metrics
* Prediction latency
* Kafka metrics
* Fallback metrics

---

## PHASE 14 — INFRASTRUCTURE

Build:

* Dockerfiles
* Docker Compose
* Health checks
* Service dependencies
* Kubernetes manifests
* ConfigMaps
* Secret example
* Readiness probes
* Liveness probes

---

## PHASE 15 — DEMO + TESTING

Build:

* Demo seed
* HOT_SHARD polished scenario
* Automated tests
* Integration tests
* Performance tests
* Replay tests
* Final acceptance verification

---

# ================================================================================

# README REQUIREMENTS

# ================================================================================

README.md MUST contain:

1. Project overview
2. Problem statement
3. Why reactive thresholds are insufficient for the simulated problem
4. Why AI is used
5. Architecture diagram
6. System data flow
7. Technology stack
8. Backend architecture
9. Frontend architecture
10. ML architecture
11. Kafka architecture
12. Redis architecture
13. PostgreSQL schema
14. ML methodology
15. Dataset generation
16. Feature engineering
17. Leakage prevention
18. Model evaluation
19. Fallback strategy
20. Explainability
21. Graph algorithms
22. Graph complexity
23. Optimization algorithm
24. Optimization complexity
25. Remediation workflow
26. Failure scenarios
27. Simulation methodology
28. Replay methodology
29. API documentation
30. Local setup
31. Docker setup
32. Kubernetes setup
33. Testing
34. Performance measurements
35. Resilience behavior
36. Security model
37. Known limitations
38. Future improvements
39. Demo walkthrough
40. Resume positioning

---

# ================================================================================

# KNOWN LIMITATIONS

# ================================================================================

README MUST explicitly state:

* Simulator-generated data is not production traffic.
* Model quality depends on simulated training distributions.
* Results do not prove production infrastructure performance.
* The model is not trained on proprietary infrastructure data.
* The optimizer is a prototype.
* Automatic remediation is intentionally constrained.
* Kubernetes configuration is educational/local.
* The project does not represent any company's internal infrastructure.
* Feature importance is not causal proof.
* Simulated dependencies may not represent all real-world distributed-system behavior.

---

# ================================================================================

# RESUME POSITIONING

# ================================================================================

Recommended project title:

**PredictiveShard AI — AI-Powered Predictive Failure Prevention Platform**

Recommended description:

> Built a distributed-system reliability platform that predicts shard overload before failure using XGBoost-based temporal telemetry analysis, identifies cascading failure paths using weighted graph traversal, and optimizes preventive traffic redistribution under capacity constraints. Designed an event-driven architecture using Java/Spring Boot, Kafka, Redis, PostgreSQL and FastAPI with deterministic ML fallback, incident tracking, replayable simulations, and real-time observability.

Do NOT claim:

> Solved a problem Amazon has not solved.

Do NOT claim:

> Production-grade replacement for AWS infrastructure.

Do NOT claim:

> Prevents all distributed-system failures.

Instead describe it as:

> An experimental student/research prototype demonstrating proactive failure prediction, graph-based cascade analysis, and constrained traffic optimization.

---

# ================================================================================

# BUILD / RUN

# ================================================================================

## FULL DOCKER MODE

Run:

```
docker compose up --build
```

Expected:

```
Frontend:
    http://localhost:5173

Backend:
    http://localhost:8080

ML:
    http://localhost:8000

Kafka UI:
    http://localhost:8085

Prometheus:
    http://localhost:9090

Grafana:
    http://localhost:3000
```

---

## MANUAL MODE

Infrastructure:

```
docker compose up -d postgres redis kafka kafka-ui
```

Backend:

```
cd server
./mvnw spring-boot:run
```

ML:

```
cd ml-service
pip install -r requirements.txt
python train.py
python -m uvicorn app.main:app --host 0.0.0.0 --port 8000
```

Frontend:

```
cd client
npm install
npm run dev
```

---

# ================================================================================

# DEMO WALKTHROUGH

# ================================================================================

The README MUST provide this exact walkthrough.

### STEP 1

Login as:

```
operator@predictiveshard.ai
```

### STEP 2

Open:

```
Dashboard
```

Verify:

```
Demo Commerce Cluster
HEALTHY
```

### STEP 3

Open:

```
Simulation
```

Select:

```
HOT_SHARD
```

Set:

```
deterministic seed
```

### STEP 4

Start simulation.

Observe:

```
Traffic distribution
CPU
Queue
Latency
Cache misses
Retry rate
```

### STEP 5

Prediction appears:

```
HIGH RISK
```

with:

```
overload probability
failure probability
time to failure
confidence
contributing signals
```

### STEP 6

Open:

```
Failure Explorer
```

Observe:

```
Root shard
Root service
Failure paths
Blast radius
```

### STEP 7

Open:

```
Remediation
```

Observe:

```
Current allocation
Recommended allocation
Objective score
Risk reduction
Migration cost
```

### STEP 8

Approve remediation.

### STEP 9

Execute:

```
REDISTRIBUTE_TRAFFIC
```

### STEP 10

Observe new telemetry.

### STEP 11

Prediction decreases.

Example:

```
91%
  ->
24%
```

### STEP 12

Incident becomes:

```
RESOLVED
```

### STEP 13

Open:

```
Analytics
```

Observe:

```
Warning time
Risk reduction
Prevented incidents
Remediation success rate
Prediction statistics
```

This sequence must be polished enough to demonstrate the entire project during an interview.

---

# ================================================================================

# FINAL FUNCTIONAL REQUIREMENT

# ================================================================================

The final application MUST be:

* Fully functional
* Modular
* AI-powered
* Distributed-system oriented
* Explainable
* Fault tolerant
* Observable
* Responsive
* Testable
* Dockerized
* Kubernetes-ready
* Reproducible
* Secure by default
* Safe by default
* Deterministic when seeded
* Offline-capable with ML fallback
* Documented
* Suitable for demonstrating SDE-level engineering fundamentals

Generate all:

* Code
* Folders
* APIs
* DTOs
* Entities
* Repositories
* Services
* Controllers
* Kafka configuration
* Redis configuration
* Database migrations
* Graph algorithms
* Consistent hashing
* Simulator
* ML dataset generator
* Feature engineering
* XGBoost models
* FastAPI service
* Fallback predictor
* Explainability
* Prediction pipeline
* Failure analysis
* Optimization engine
* Remediation engine
* Incident management
* Analytics
* Audit logging
* Replay
* UI pages
* UI components
* State management
* Docker configuration
* Kubernetes manifests
* Prometheus configuration
* Grafana dashboard
* Tests
* Seed data
* Documentation

required to satisfy this specification.

---

# ================================================================================

# END OF SPECIFICATION

# ================================================================================
