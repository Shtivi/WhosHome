app.controller("homeCtrl", function($scope, notificationsServiceUrl, presentEntitiesService) {
    var ws = null;

    // Initialization - web the current presence
    presentEntitiesService.getPresentEntities().then((res) => {
        $scope.entities = res.data;
        
        // Init the websocket
        ws = new WebSocket(notificationsServiceUrl);
        ws.onopen =() => {
            console.log("Socket connected");
        }

        ws.onmessage = (msg) => {
            // From string to objet
            data = JSON.parse(msg.data);

            if (data.eventType == 'IN') {
                if (data.subject) {
                    $scope.entities.identified[data.subject._id] = data;
                } else {
                    $scope.entities.unknown[data.identificationData] = data;
                }
            } else if (data.eventType == 'OUT') {
                if (data.subject) {
                    delete $scope.entities.identified[data.subject._id];
                } else {
                    delete $scope.entities.unknown[data.identificationData];
                }
            }

            $scope.$digest();
        }
    }, (err) => {
        
    })
})