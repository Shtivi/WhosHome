services.service("peopleService", function(serverUrl, $http) {
    this.addPerson = (personData) => {
        return $http.post(serverUrl + '/api/people', personData);
    }
})