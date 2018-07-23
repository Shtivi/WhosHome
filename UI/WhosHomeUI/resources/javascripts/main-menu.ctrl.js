app.controller("mainMenuCtrl", function($scope, $location) {
    $scope.mainMenuItems = [{
        href: 'home',
        icon: 'users',
        description: "Home"
    }, {
        href: 'history',
        icon: 'history',
        description: 'History'
    }, {
        href: "chat",
        icon: "comments",
        description: "Chat"
    }, {
        href: "sensors", 
        icon: "signal",
        description: "Sensors"
    }]

    $scope.$on("$locationChangeSuccess", (event, to, from) => {
        $scope.currentPath = $location.path().substring(1);
    })

    $scope.navigate = (selectedItem) => {
        $location.path(selectedItem.href);
    }
})