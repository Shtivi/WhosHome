var app = angular.module("WhosHome", ['ngMaterial', 'ngRoute']);

app.config(function($routeProvider, $mdThemingProvider) {
    $routeProvider.when("/", {
        template: "Hello"
    }).otherwise({
        redirect: "/"
    })
})