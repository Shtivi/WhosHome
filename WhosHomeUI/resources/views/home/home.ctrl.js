app.controller("homeCtrl", function($scope, notificationsServiceUrl, presentEntitiesService) {
    var ws = new WebSocket(notificationsServiceUrl);
    
    ws.onopen =() => {
        console.log("Socket connected");
    }

    ws.onmessage = (msg) => {
        console.log(msg.data);
    }

    presentEntitiesService.getPresentEntities().then((res) => {
        $scope.entities = res.data;
    }, (err) => {
        
    })
})