'use strict';
angular.module('hello', [ 'ngRoute', 'ui.bootstrap', 'commonapp' ]).config(function ($routeProvider) {
		    $routeProvider
		      .when('/', {
		        templateUrl: 'scripts/main/main.html'
		      })
		      .otherwise({
		        redirectTo: '/'
		      });
		  });
