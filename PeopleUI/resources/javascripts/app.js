var app = angular.module("people", ['ngMaterial', 'ngRoute', 'peopleServices']);

app.config(function($routeProvider, $mdThemingProvider, $mdIconProvider, $mdToastProvider, $mdDialogProvider) {
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
    $routeProvider.when("/people", {
        templateUrl: "./views/view-people/view-people.html",
        controller: "viewPeopleCtrl"
    }).when("/people/:id", {
        templateUrl: "./views/person-dialog/person-dialog.html",
        controller: "personDialogCtrl"
    }).otherwise({
        redirectTo: "/people"
    })

    // Dialogs
    $mdDialogProvider.addPreset('uploadPicture', {
        options: function() {
            return {
                templateUrl: './views/upload-picture-dialog/upload-picture-dialog.html',
                controller: 'uploadPictureCtrl'
            }
        }
    })
})

app.constant('serverUrl', 'http://localhost:5020')