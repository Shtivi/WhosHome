services.service("presentEntitiesService", function($http, serverUrl) {
    this.getPresentEntities = () => $http.get(serverUrl + "/presentEntities");
})