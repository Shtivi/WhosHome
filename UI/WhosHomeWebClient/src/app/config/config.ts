export default {
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
    },
    "presenceData": {
        "endpoint": "http://localhost:9000/api/presence",
        "api": {
            "fetchAllPeoplePresenceData": ""
        }
    }
}