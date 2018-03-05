var app = angular.module("WhosHome", ['ngMaterial', 'ngRoute']);

app.config(function($routeProvider, $mdThemingProvider) {
    // Theming
    var darkBlueGrey = $mdThemingProvider.extendPalette("blue-grey", {
        '800': "#022C36",
        'contrastLightColors': ['800'],
        'contrastDefaultColor': 'dark'
    });

    $mdThemingProvider.definePalette('dark-blue-grey', darkBlueGrey);

    $mdThemingProvider.theme("WhosHome")
        .primaryPalette('dark-blue-grey')
        .accentPalette("amber")
        .warnPalette("red")
        .backgroundPalette("grey");

    $mdThemingProvider.setDefaultTheme("WhosHome");    

    // Routing
    $routeProvider.when("/home", {
        templateUrl: "./views/home/home.view.html",
        controller: "homeCtrl"
    }).when("/history", {
        templateUrl: "./views/history/history.view.html",
        controller: "historyCtrl"
    }).otherwise({
        redirectTo: "/home"
    })
})

app.run(function() {
    var ws = new WebSocket("ws://localhost:5001");
    
    ws.onopen =() => {
        console.log("Socket connected");
    }

    ws.onmessage = (msg) => {
        console.log(msg);
    }
})