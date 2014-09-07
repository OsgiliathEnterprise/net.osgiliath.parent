'use strict';
angular.module('myApp').directive('mywindow',
		function() {
			console.log('loaded myApp module');
			return {
				restrict : 'E',
				scope : {},
				controller : 'myCtrl',
				templateUrl : 'scripts/sample/mydirective.html',
				replace : true
			};
		});