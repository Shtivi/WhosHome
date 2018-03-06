app.controller("homeCtrl", function($scope, notificationsServiceUrl, presentEntitiesService) {
    $scope.entities = {unknown: {}, identified: {}};
    var ws = new WebSocket(notificationsServiceUrl);
    
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

    presentEntitiesService.getPresentEntities().then((res) => {
        //$scope.entities = res.data;
        res.data.forEach((entity) => {
            if (entity.subject) {
                $scope.entities.identified[entity.subject._id] = entity;
            } else {
                $scope.entities.unknown[entity.identificationData] = entity;
            }
        })
    }, (err) => {
        
    })
})