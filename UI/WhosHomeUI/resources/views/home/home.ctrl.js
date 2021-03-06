app.controller("homeCtrl", function($scope, notificationsServiceUrl, presentEntitiesService, $mdToast) {
    var ws = null;

    // Initialization - web the current presence
    presentEntitiesService.getPresentEntities().then((res) => {
        $scope.entities = res.data;
        
        // Init the websocket
        ws = new WebSocket(notificationsServiceUrl);
        
        ws.onopen =() => {
            console.log("Connected to the notifications center");
        }

        ws.onmessage = (msg) => {
            // From string to objet
            data = JSON.parse(msg.data);

            console.log(data);

            if (data.eventType == 'IN') {
                if (data.subject) {
                    $scope.entities.identified[data.subject._Id] = data;
                } else {
                    $scope.entities.unknown[data.identificationData] = data;
                }
            } else if (data.eventType == 'OUT') {
                if (data.subject) {
                    delete $scope.entities.identified[data.subject._Id];
                } else {
                    delete $scope.entities.unknown[data.identificationData];
                }
            }

            $scope.$digest();
        }
    }, (err) => {
        $mdToast.showSimple("Error fetching the present entities.");
        console.log(err);
    })
})