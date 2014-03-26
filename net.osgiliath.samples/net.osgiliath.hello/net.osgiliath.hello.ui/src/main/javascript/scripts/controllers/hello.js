'use strict';
angular.module('hello').controller(
		'HelloCtrl',
		function($scope, stompservice) {
			$scope.helloMessage = '';
			$scope.sendHello = function() {
				
				stompservice.send('/queue/helloJaxRSEntryPoint',
						'{"httpRequestType":"POST"}', '{"helloMessage": ' + $scope.helloMessage + '}');
				stompservice.send('/queue/helloJmsEntryPoint',
						'{"httpRequestType":"GET"}', '{"helloMessage": ' + $scope.helloMessage + '}');
				stompservice.subscribe('/queue/helloJaxRSEndPoint',
						function(message) {
					$scope.helloMessage = message.body;
					console.log('helloMessage received: ' + message);
						});

				
			};
		});
