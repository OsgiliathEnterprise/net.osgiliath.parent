'use strict';
angular.module('sampleApp').directive('samplewindow',
		function() {
			console.log('loaded myApp module');
			return {
				restrict : 'E',
				scope : {},
				controller : 'sampleController',
				templateUrl : 'scripts/sample/sampleDirective.html',
				replace : true
			};
		});