from __future__ import annotations

import math
import numpy as np
from typing import Any
from fastapi import FastAPI
from pydantic import BaseModel, Field
from app.features import ALL_FEATURES, engineer
from app.model_loader import models

app = FastAPI(title="PredictiveShard ML", version="1.0.0")

class PredictRequest(BaseModel):
    features: dict[str, float] = Field(default_factory=dict)
    historicalWindow: list[dict[str, Any]] = Field(default_factory=list)

def clamp(value: float) -> float:
    return max(0.0, min(1.0, value))

def normalized(features: dict[str, float], key: str, scale: float = 100.0) -> float:
    return clamp(float(features.get(key, 0.0)) / scale)

def predict(payload: PredictRequest) -> dict[str, Any]:
    f = payload.features
    history = payload.historicalWindow
    cpu = normalized(f, "cpuUsage")
    queue = normalized(f, "queueLength", 2000)
    latency = normalized(f, "p95Latency", 500)
    cache = normalized(f, "cacheMissRate")
    db = normalized(f, "dbLatency", 250)
    retry = normalized(f, "retryRate", 20)
    slope = 0.0
    if len(history) >= 2:
        slope = clamp((float(history[-1].get("p95Latency", 0)) - float(history[0].get("p95Latency", 0))) / 300)
    fallback_risk = clamp(.18*cpu + .15*queue + .15*latency + .10*cache + .12*db + .10*retry + .10*slope)
    vector=np.array([[engineer(f,history).get(k,0.0) for k in ALL_FEATURES]])
    fallback_used=not models.loaded
    if models.loaded:
        try:
            risk=float(models.overload.predict_proba(vector)[0][1])
            failure=float(models.failure.predict_proba(vector)[0][1]) if models.failure else clamp(risk*.78)
            ttf=max(15,round(float(models.ttf.predict(vector)[0]))) if models.ttf else max(15,round(180*(1-risk)))
        except Exception:
            risk,failure,ttf,fallback_used=fallback_risk,clamp(fallback_risk*.78),max(15,round(180*(1-fallback_risk))),True
    else: risk,failure,ttf=fallback_risk,clamp(fallback_risk*.78),max(15,round(180*(1-fallback_risk)))
    factors = []
    for key, val, label in [("cpuUsage", cpu, "CPU utilization"), ("queueLength", queue, "Queue length"), ("p95Latency", latency, "p95 latency"), ("retryRate", retry, "Retry rate")]:
        if val >= .45:
            factors.append({"feature": key, "contribution": round(val, 3), "direction": "increasing", "explanation": f"{label} is elevated in the latest telemetry window."})
    confidence = clamp(.58 + min(.30, len(history) * .03) + (0.08 if risk > .5 else 0))
    return {"overloadProbability": round(risk, 4), "failureProbability": round(failure, 4), "estimatedTimeToFailureSeconds": ttf, "confidence": round(confidence, 4), "contributingFactors": factors, "modelVersion": "1.0.0" if not fallback_used else "fallback-1.0.0", "fallbackUsed": fallback_used}

@app.get("/health")
def health():
    return {"status": "ok", "modelLoaded": models.loaded, "modelVersion": "1.0.0" if models.loaded else "fallback-1.0.0", "fallbackEnabled": True}

@app.post("/predict")
def single_prediction(payload: PredictRequest):
    return predict(payload)

@app.post("/predict/batch")
def batch_prediction(payload: list[PredictRequest]):
    return {"predictions": [predict(item) for item in payload]}

@app.post("/explain")
def explain(payload: PredictRequest):
    return {"features": predict(payload)["contributingFactors"]}
