'use strict';
angular.module('myApp', [ 'ui.bootstrap', 'commonapp', 'ngRoute' ]).config(function ($routeProvider) {
		    $routeProvider
		      .when('/', {
		        templateUrl: 'scripts/main/main.html'
		      })
		      .otherwise({
		        redirectTo: '/'
		      });
		  });
