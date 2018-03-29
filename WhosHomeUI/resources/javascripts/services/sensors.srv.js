services.service("sensorsService", function (serverUrl, $http) {
    this.getAttachedSensors = () => {
        return $http.get(serverUrl + "/sensors");
    }
})