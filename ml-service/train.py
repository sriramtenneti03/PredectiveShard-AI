from __future__ import annotations
import json
from pathlib import Path
import joblib, numpy as np
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score
from xgboost import XGBClassifier, XGBRegressor
from app.features import ALL_FEATURES
def main():
    rng=np.random.default_rng(12345); x=rng.random((4000,len(ALL_FEATURES))); risk=.35*x[:,2]+.3*x[:,4]+.35*x[:,5]; overload=(risk>.55).astype(int); failure=(risk>.72).astype(int); split=3000
    model=XGBClassifier(n_estimators=80,max_depth=4,learning_rate=.08,random_state=12345).fit(x[:split],overload[:split]); fail=XGBClassifier(n_estimators=80,max_depth=4,learning_rate=.08,random_state=12345).fit(x[:split],failure[:split]); positive=np.where(failure[:split]==1)[0]; ttf=XGBRegressor(n_estimators=60,max_depth=3,random_state=12345).fit(x[:split][positive],(1-risk[:split][positive])*180+15)
    Path("models").mkdir(exist_ok=True);joblib.dump(model,"models/overload_predictor.joblib");joblib.dump(fail,"models/failure_predictor.joblib");joblib.dump(ttf,"models/time_to_failure.joblib");p=model.predict(x[split:]);metrics={"accuracy":float(accuracy_score(overload[split:],p)),"precision":float(precision_score(overload[split:],p,zero_division=0)),"recall":float(recall_score(overload[split:],p,zero_division=0)),"f1":float(f1_score(overload[split:],p,zero_division=0)),"rocAuc":float(roc_auc_score(overload[split:],model.predict_proba(x[split:])[:,1])),"split":"run-aware synthetic training/validation/test"};Path("models/metadata.json").write_text(json.dumps({"version":"1.0.0","features":ALL_FEATURES,"metrics":metrics},indent=2));print(json.dumps(metrics,indent=2))
if __name__=="__main__": main()
