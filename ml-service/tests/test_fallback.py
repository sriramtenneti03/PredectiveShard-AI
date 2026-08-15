from app.main import PredictRequest, predict

def test_predictor_is_deterministic():
    data = PredictRequest(features={"cpuUsage": 90, "queueLength": 1500, "p95Latency": 300})
    assert predict(data) == predict(data)
    assert predict(data)["fallbackUsed"] is True
