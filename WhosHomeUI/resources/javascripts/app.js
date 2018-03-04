var app = angular.module("WhosHome", ['ngMaterial', 'ngRoute']);

app.config(function($routeProvider, $mdThemingProvider) {
    $routeProvider.when("/", {
        templateUrl: "./views/home/home.view.html",
        controller: "homeCtrl"
    }).otherwise({
        redirect: "/"
    })
})