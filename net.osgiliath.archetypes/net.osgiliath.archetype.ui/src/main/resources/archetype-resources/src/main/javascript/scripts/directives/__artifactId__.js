'use strict';
angular.module('${artifactId}', [ 'ui.bootstrap', 'commonapp', 'ngRoute' ]).directive('${artifactId}window',
		function() {
			console.log('loaded ${artifactId} module');
			return {
				restrict : 'E',
				scope : {},
				controller : '${artifactId}Ctrl',
				templateUrl : 'views/templates/${artifactId}.html',
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