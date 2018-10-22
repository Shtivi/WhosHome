"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = {
    "push": {
        "endpoint": "http://localhost:9000/push",
        "topics": {
            "sensorsStatus": "/topics/sensors/status",
            "sensorError": "/topics/sensors/error",
            "engineStatus": "/topics/engine/status",
            "peopleDetection": "/topics/people/detection"
        }
    },
    "sensors": {
        "endpoint": "http://localhost:9000/api/sensors",
        "api": {
            "fetchAllSensors": "",
            "toggleSensor": "toggle"
        }
    }
};
//# sourceMappingURL=config.js.map