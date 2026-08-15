from __future__ import annotations
import os, joblib
from pathlib import Path
class Models:
    def __init__(self):
        self.overload=self.load(os.getenv("MODEL_PATH","models/overload_predictor.joblib")); self.failure=self.load(os.getenv("FAILURE_MODEL_PATH","models/failure_predictor.joblib")); self.ttf=self.load(os.getenv("TTF_MODEL_PATH","models/time_to_failure.joblib"))
    def load(self,path):
        try: return joblib.load(Path(path))
        except Exception: return None
    @property
    def loaded(self): return self.overload is not None
models=Models()
