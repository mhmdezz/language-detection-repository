'use strict';

angular.module('langDetectorApp', ['ui.router','ngResource'])
.config(function($stateProvider, $urlRouterProvider) {
        $stateProvider

            // route for the home page
            .state('app', {
                url:'/',
                views: {
                    'header': {
                        templateUrl : 'views/header.html',
                        controller  : 'LanguageController'
                    },
                    'content': {
                    	templateUrl : 'views/home.html',
                        controller  : 'LanguageController'
                    },
                    'footer': {
                        templateUrl : 'views/footer.html',
                    }
                }
            })
            ;

        $urlRouterProvider.otherwise('/');
    })
;
