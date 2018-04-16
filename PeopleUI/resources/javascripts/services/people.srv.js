services.service("peopleService", function(serverUrl, $http) {
    this.addPerson = (personData) => {
        return $http.post(serverUrl + '/api/people', personData);
    }

    this.getPeopleLimited = (limit) => {
        return $http.get(serverUrl + '/api/people/limit/' + limit);
    }

    this.getAllPeople = () => {
        return $http.get(serverUrl + '/api/people');
    }

    this.searchPeople = (searchQuery) => {
        return $http.get(serverUrl + '/api/people/search/' + searchQuery);
    }

    this.updatePerson = (person) => {
        return $http.put(serverUrl + '/api/people', person);
    }

    this.deletePerson = (personId) => {
        return $http.delete(serverUrl + '/api/people/' + personId);
    }
})