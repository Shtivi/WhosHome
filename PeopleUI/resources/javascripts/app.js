var app = angular.module("people", ['ngMaterial', 'ngRoute', 'peopleServices']);

app.config(function($routeProvider, $mdThemingProvider, $mdIconProvider, $mdToastProvider) {
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

    $mdThemingProvider.theme('darkdocs')
        .primaryPalette('amber')
        .accentPalette('dark-blue-grey')
        .backgroundPalette('grey')
        .dark();

    $mdThemingProvider.setDefaultTheme("WhosHome");    

    // Icons
    $mdIconProvider
        .defaultFontSet('fa')
        .defaultIconSet('./libs/font-awsome/fontawesome-webfont.svg');

    // Routing
    // $routeProvider.when("/home", {
    //     templateUrl: "./views/home/home.view.html",
    //     controller: "homeCtrl"
    // }).when("/history", {
    //     templateUrl: "./views/history/history.view.html",
    //     controller: "historyCtrl"
    // }).when("/sensors", {
    //     templateUrl: "./views/sensors/sensors.view.html",
    //     controller: "sensorsCtrl"
    // }).otherwise({
    //     redirectTo: "/home"
    // })
})

app.constant('serverUrl', 'http://localhost:5020')