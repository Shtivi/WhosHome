services.service("sensorsService", function (serverUrl, $http) {
    this.getAttachedSensors = () => {
        return $http.get(serverUrl + "/sensors");
    }

    this.toggleSensor = (sensorID) => {
        return $http.get(serverUrl + "/sensors/toggle/" + sensorID);
    }
})