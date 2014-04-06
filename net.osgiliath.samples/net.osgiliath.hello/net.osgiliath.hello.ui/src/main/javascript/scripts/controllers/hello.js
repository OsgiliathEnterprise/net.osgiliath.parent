'use strict';
angular.module('hello').controller(
		'HelloCtrl',
		function($scope, stompservice) {
			$scope.helloMessage = '';
			$scope.helloMessages = [];
			$scope.registered = false;
			$scope.errors = '';
			$scope.sendHello = function() {
				$scope.errors = '';
				if (!$scope.registered) {
					stompservice.subscribe("/topic/helloJMSEndPoint", function(
							message) {
						if (!stompservice.heartBeatFilter(message)) {
							var body = message.body;
							console.log('helloMessage received: ' + body);
							var json = JSON.parse(body);
							var hellos = json.collection;
							if (hellos != undefined) {
								$scope.helloMessages = [];
								console.log('hellos: ' + hellos);
								for ( var i in hellos) {
									$scope.helloMessages.push(hellos[i]);
								}
								$scope.$apply();
							}
						}
					});
					stompservice.subscribe("/queue/helloErrors", function(
							message) {
						if (!stompservice.heartBeatFilter(message)) {
							var body = message.body;
							console.log('error received: ' + body);
							var json = JSON.parse(body);
							var error = json.error;
							$scope.errors = error;
							$scope.$apply();
						}
						$scope.registered = true;
					});
				}
				stompservice.send("/queue/helloJMSEntryPoint", {
					"httpRequestType" : "POST"
				}, '{"helloMessage": "' + $scope.helloMessage + '"}');
			};
		});
