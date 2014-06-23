'use strict';
angular.module('myApp', [ 'ui.bootstrap', 'commonapp', 'ngRoute' ]).directive('mywindow',
		function() {
			console.log('loaded myApp module');
			return {
				restrict : 'E',
				scope : {},
				controller : 'myCtrl',
				templateUrl : 'views/templates/mydirective.html',
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