'use strict';
angular.module('hello', [ 'ui.bootstrap', 'commonapp', 'ngRoute' ]).directive('hellowindow',
		function() {
			console.log('loaded hello module');
			return {
				restrict : 'E',
				scope : {},
				controller : 'HelloCtrl',
				templateUrl : 'scripts/templates/hello.html',
				replace : true
			};
		}).config(function ($routeProvider) {
		    $routeProvider
		      .when('/', {
		        templateUrl: 'views/main.html'
		      })
		      .otherwise({
		        redirectTo: '/'
		      });
		  });
