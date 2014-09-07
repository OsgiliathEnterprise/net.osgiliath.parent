'use strict';
angular.module('hello').directive('hellowindow',
		function() {
			console.log('loaded hello module');
			return {
				restrict : 'E',
				scope : {},
				controller : 'HelloController',
				templateUrl : 'scripts/hello/hello.html',
				replace : true
			};
		});
