app.controller('viewPeopleCtrl', function($scope, peopleService, $mdToast) {
    $scope.limit = 9;
    $scope.limitIncreasmentFactor = 12;

    $scope.getPeople = () => {
        peopleService.getPeopleLimited($scope.limit).then((res) => {
            $scope.people = res.data;
            console.log(res.data);
        }, (err) => {
            $mdToast.showSimple('Failed to load people');
            console.log(err);
        })
    }

    $scope.increaseLimit = () => {
        $scope.limit += $scope.limitIncreasmentFactor;
    }

    $scope.getPeople();
})