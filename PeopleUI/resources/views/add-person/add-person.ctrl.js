app.controller("addPersonCtrl", function($scope, peopleService, $mdToast) {
    $scope.person = {};
    $scope.processing = false;
    
    $scope.submitPerson = () => {
        // Show spinner
        $scope.processing = true;

        peopleService.addPerson($scope.person).then((res) => {
            $scope.processing = false;
            $mdToast.showSimple('Person added');
        }, (err) => {
            $scope.processing = false;
            $mdToast.showSimple("Could not add the person: " + err.data);
            console.log(err);
        })
    }
})