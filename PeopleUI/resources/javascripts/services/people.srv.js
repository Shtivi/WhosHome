services.service("peopleService", function(serverUrl, $http) {
    this.addPerson = (personData) => {
        return $http.post(serverUrl + '/api/people', personData);
    }

    this.getPeopleLimited = (limit) => {
        return $http.get(serverUrl + '/api/people/limit/' + limit);
    }
})