'use strict';
angular.module('sampleApp', [ 'ui.bootstrap', 'commonapp', 'ngRoute' ]).config(function ($routeProvider) {
		    $routeProvider
		      .when('/', {
		        templateUrl: 'scripts/main/main.html'
		      })
		      .otherwise({
		        redirectTo: '/'
		      });
		  });
