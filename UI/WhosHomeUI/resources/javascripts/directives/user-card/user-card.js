directives.directive("userCard", function() {
    return {
        templateUrl: './javascripts/directives/user-card/user-card.tmpl.html',
        scope: {
            personData: '='
        },
        controller: function($scope) {
            
        }
    }
})