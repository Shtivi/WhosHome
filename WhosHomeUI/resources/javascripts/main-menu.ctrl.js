app.controller("mainMenuCtrl", function($scope, $location) {
    $scope.mainMenuItems = [{
        href: 'home',
        icon: 'users',
        description: "Present"
    }, {
        href: 'history',
        icon: 'history',
        description: 'History'
    }, {
        href: "chat",
        icon: "comments",
        description: "Chat"
    }]

    $scope.$on("$locationChangeSuccess", (event, to, from) => {
        $scope.currentPath = $location.path().substring(1);
    })

    $scope.navigate = (selectedItem) => {
        $location.path(selectedItem.href);
    }
})