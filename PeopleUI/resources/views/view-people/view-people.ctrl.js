app.controller('viewPeopleCtrl', function($scope, peopleService, $mdToast, $mdDialog, $location) {
    $scope.limit = 9;
    $scope.limitIncreasmentFactor = 12;
    $scope.searchText = '';
    $scope.loading = false;
    var lastKeypressTime = null;

    // Methods

    $scope.getPeople = () => {
        $scope.loading = true;
        
        peopleService.getAllPeople($scope.limit).then((res) => {
            $scope.people = res.data;
            console.log(res.data);
            $scope.searchText = '';
            $scope.loading = false;
            $scope.error = null;
        }, (err) => {
            $scope.error = "Failed to load the people from the server, it's probably because the server is unavailable or offline.";
            $scope.people = [];
            console.log(err);
            $scope.loading = false;
        })
    }

    $scope.increaseLimit = () => {
        $scope.limit += $scope.limitIncreasmentFactor;
    }

    var search = (searchText) => {
        $scope.loading = true;
        
        peopleService.searchPeople($scope.searchText).then((res) => {
            $scope.people = res.data;
            console.log(res.data);
            $scope.loading = false;
        }, (err) => {
            $mdToast.showSimple('Failed to search people');
            console.log(err);
            $scope.loading = false;
        })
    }

    $scope.searchInputKeyDown = (e) => {
        lastKeypressTime = new Date().getTime();
    }

    $scope.searchInputKeyUp = () => {
        // If no search text, fetch back all the people
        if ($scope.searchText == '') {
            $scope.getPeople();
        } else {
            setTimeout(() => {
                // Set timeout for 1.5 seconds. If after that time no key pressed, call the search function.
                if ((lastKeypressTime)) {
                    // Calculate the seconds elapsed from the last keypress
                    var elpasedTime = (new Date().getTime() - lastKeypressTime) / 1000;

                    if (elpasedTime > 1) {
                        search($scope.searchText);
                    } 
                }
            }, 1000);
        }
    }

    $scope.showPersonDialog = (person) => {
        $location.path('/people/' + person._id);
    }

    $scope.uploadPictureDialog = () => {
        $mdDialog.show($mdDialog.uploadPicture());
    }

    // Run
    (() => {
        $scope.getPeople();
    })();

})