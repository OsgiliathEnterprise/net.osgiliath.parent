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
							var hellos = json.hellos;
							if (!hellos == undefined) {
								$scope.helloMessages = [];
								console.log('hellos: ' + hellos);
								var collection = hellos.collection;
								console.log('collection: ' + collection);
								for ( var i in collection) {
									$scope.helloMessages.push(collection[i]);
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
