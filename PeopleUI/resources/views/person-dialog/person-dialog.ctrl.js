app.controller("personDialogCtrl", function($scope, peopleService, personData, $mdDialog, $mdToast) {
    $scope.person = personData;
    $scope.mode = 'view';

    $scope.savePerson = () => {
        peopleService.updatePerson($scope.person).then((res) => {
            $mdToast.showSimple("Person updated!");
            $scope.mode = 'view';
        }, (err) => {
            console.log(err);
            $mdToast.showSimple("Error while updating the person");
        })
    }

    $scope.deletePerson = () => {
        $mdDialog.show($mdDialog
            .confirm()
            .textContent("Are you sure you want to delete " + $scope.person.firstname + ' ' + $scope.person.lastname + "?")
            .ok("Yes, I'm sure")
            .cancel("No"))
            .then(() => {
                peopleService.deletePerson($scope.person._id).then((res) => {
                    $mdToast.showSimple("Person deleted");
                }, (err) => {
                    console.log(err);
                    $mdToast.showSimple("Error deleting this person");
                })
            }, () => {

            });
    }
})