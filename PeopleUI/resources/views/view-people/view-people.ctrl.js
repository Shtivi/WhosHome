app.controller('viewPeopleCtrl', function($scope, peopleService, $mdToast) {
    $scope.limit = 9;
    $scope.limitIncreasmentFactor = 12;
    $scope.searchText = '';
    var lastKeypressTime = null;

    // Methods

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

    var search = (searchText) => {
        peopleService.searchPeople($scope.searchText).then((res) => {
            $scope.people = res.data;
            console.log(res.data);
        }, (err) => {
            $mdToast.showSimple('Failed to search people');
            console.log(err);
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

    // Run
    (() => {
        $scope.getPeople();
    })();

})