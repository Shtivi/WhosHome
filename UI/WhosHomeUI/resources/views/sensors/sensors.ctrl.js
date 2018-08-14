app.controller("sensorsCtrl", function($scope, sensorsService, $mdToast, notificationsServiceUrl, $mdDialog) {
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
    // ws = new WebSocket(notificationsServiceUrl);
        
    // ws.onopen =() => {
    //     console.log("Connected to the notifications center");
    // }

    // ws.onmessage = (msg) => {
    //     // From string to objet
    //     data = JSON.parse(msg.data);

    //     if (data.eventType == 'SENSOR_STATE_CHANGE') {
    //         $scope.sensors[data.sensorID].sensorState = data.newState;
    //     }

    //     $scope.$digest();
    // }

    // From sensor type to icon
    $scope.sensorIcons = {
        'NETWORK': 'fa fa-globe'
    }

    // From status name to color
    $scope.statusColors = {
        'CONNECTED': 'green',
        'INITIALIZED': 'blue',
        'ERROR': 'red',
        'CLOSED': 'yellow',
        'CONNECTING': 'blue'
    }

    // Sensor context menu
    function dialogController($scope, $mdDialog, sensorData, statusColors, sensorIcons) {
        $scope.sensor = sensorData;
        $scope.statusColors = statusColors;
        $scope.sensorIcons = sensorIcons;
    }

    $scope.contextMenuItems = [{
        icon: 'fa fa-power-off',
        description: 'Turn on / off',
        execute: (sensor) => {
            sensorsService.toggleSensor(sensor.id).then((res) => {
                $mdToast.showSimple("Toggling sensor...");
            }, (err) => {
                $mdToast.showSimple(err.message);
            })
        }
    }, {
        icon: 'fa fa-exclamation-circle',
        description: 'View more details',
        execute: (sensor) => {
            $mdDialog.show({
                templateUrl: './views/sensors/sensors.dialog.html',
                controller: dialogController,
                locals: {
                    sensorData: sensor,
                    statusColors: $scope.statusColors,
                    sensorIcons: $scope.sensorIcons
                }
            })
        }
    }]
})