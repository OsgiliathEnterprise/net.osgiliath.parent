'use strict';
angular.module('hello').controller(
		'HelloCtrl',
		function($scope, stompservice) {
			$scope.helloMessage = '';
			$scope.sendHello = function() {
				stompservice.subscribe('/queue/registered', function() {
					stompservice.subscribe('/queue/helloJaxRSEndPoint',
							function(message) {
								this.helloMessage = message.body;
							});
					stompservice.send('/queue/helloJmsEntryPoint',
							'{"httpRequestType":"GET"}', '{"helloMessage": ' + this.helloMessage + '}');

				});
				stompservice.send('/queue/helloJaxRSEntryPoint',
						'{"httpRequestType":"POST"}', '{"helloMessage": ' + this.helloMessage + '}');
				console.log('helloMessage received: ' + $scope.helloMessage);
			};
		});
