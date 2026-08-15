from __future__ import annotations
import math
FEATURES = ["requestRate","cpuUsage","memoryUsage","networkUsage","queueLength","p95Latency","p99Latency","cacheMissRate","dbConnectionUsage","dbLatency","retryRate","errorRate","activeConnections"]
DERIVED = ["cpuSlope","latencySlope","queueSlope","requestRateSlope","retryRateSlope","dbLatencySlope","cacheMissSlope","rollingCpuMean","rollingLatencyMean","rollingQueueMean","rollingRetryMean","latencyAcceleration","queueAcceleration","requestGrowthRate"]
ALL_FEATURES = FEATURES + DERIVED
def number(value: object) -> float:
    try:
        v=float(value)
        return v if math.isfinite(v) else 0.0
    except (TypeError,ValueError): return 0.0
def engineer(features: dict, history: list[dict]) -> dict[str,float]:
    rows=sorted(history, key=lambda x:str(x.get("timestamp","")))
    values={k:[number(r.get(k)) for r in rows] for k in FEATURES}
    out={k:number(features.get(k)) for k in FEATURES}
    def slope(k):
        a=values[k]
        return 0.0 if len(a)<2 else a[-1]-a[0]
    for name,key in [("cpuSlope","cpuUsage"),("latencySlope","p95Latency"),("queueSlope","queueLength"),("requestRateSlope","requestRate"),("retryRateSlope","retryRate"),("dbLatencySlope","dbLatency"),("cacheMissSlope","cacheMissRate")]: out[name]=slope(key)
    for name,key in [("rollingCpuMean","cpuUsage"),("rollingLatencyMean","p95Latency"),("rollingQueueMean","queueLength"),("rollingRetryMean","retryRate")]: out[name]=sum(values[key][-5:])/max(1,len(values[key][-5:]))
    out["latencyAcceleration"]=(values["p95Latency"][-1]-2*values["p95Latency"][-2]+values["p95Latency"][-3]) if len(rows)>=3 else 0.0
    out["queueAcceleration"]=(values["queueLength"][-1]-2*values["queueLength"][-2]+values["queueLength"][-3]) if len(rows)>=3 else 0.0
    out["requestGrowthRate"]=slope("requestRate")/max(1.0,abs(values["requestRate"][0]) if values["requestRate"] else 1)
    return out
