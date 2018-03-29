app.controller("sensorsCtrl", function($scope, sensorsService, $mdToast, notificationsServiceUrl) {
    // Fetch sensors data
    $scope.fetchSesnors = () => {
        sensorsService.getAttachedSensors().then((res) => {
            $scope.sensors = {};

            // Convert from sensors list to map
            res.data.forEach((sensor) => {
                $scope.sensors[sensor.id] = sensor;
            });
        }, (err) => {
            $mdToast.showSimple("Error loading the sensors");
            console.log(err);
        });
    };
    $scope.fetchSesnors();

    // Init the websocket
    ws = new WebSocket(notificationsServiceUrl);
        
    ws.onopen =() => {
        console.log("Connected to the notifications center");
    }

    ws.onmessage = (msg) => {
        // From string to objet
        data = JSON.parse(msg.data);

        if (data.eventType == 'SENSOR_STATE_CHANGE') {
            $scope.sensors[data.sensorID].sensorState = data.newState;
        }

        $scope.$digest();
    }

    // From sensor type to icon
    $scope.sensorIcons = {
        'NETWORK': 'fa fa-globe'
    }

    // From status name to color
    $scope.statusColors = {
        'ACTIVE': 'green',
        'READY': 'blue',
        'ERROR': 'red',
        'NOT_READY': 'yellow'
    }

    // Sensor info dialog
    $scope.showSensorInfoDialog = (ev, sensor) => {

    }
})