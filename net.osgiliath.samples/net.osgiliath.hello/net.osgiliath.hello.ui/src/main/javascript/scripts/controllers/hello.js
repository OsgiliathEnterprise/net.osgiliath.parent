'use strict';
angular.module('hello').controller(
		'HelloCtrl',
		function($scope, stompservice) {
			$scope.helloMessage = '';
			$scope.helloMessages = [];
			$scope.registered = false;
			$scope.sendHello = function() {
				if (!$scope.registered) {
					stompservice.subscribe("/topic/helloJMSEndPoint", function(
							message) {
						if (!stompservice.heartBeatFilter(message)) {
							var body = message.body;
							console.log('helloMessage received: ' + body);
							$scope.helloMessages = [];
							var json = JSON.parse(body);
							var hellos =  json.hellos;
							console.log('hellos: ' + hellos);
							var collection = hellos.collection;
							console.log('collection: ' + collection);
							for (var i in collection) {
								
								$scope.helloMessages.push(collection[i]);
								}
							 $scope.$apply();
							
						}
						$scope.registered = true;
					});
				}
				stompservice.send("/queue/helloJMSEntryPoint",
						{"httpRequestType":"POST"}, '{"helloMessage": "' + $scope.helloMessage + '"}');


			};
//			$scope.refreshHello = function() {
//				
//				stompservice.send("/queue/helloJMSEntryPoint",
//						{"httpRequestType":"GET"}, '');
//			};
		});
