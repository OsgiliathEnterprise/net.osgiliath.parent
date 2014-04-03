'use strict';
angular.module('hello').controller(
		'HelloCtrl',
		function($scope, stompservice) {
			$scope.helloMessage = '';
			$scope.helloMessages = new Array();
			$scope.registered = false;
			$scope.sendHello = function() {
				if (!$scope.registered) {
					stompservice.subscribe("/queue/helloJMSEndPoint", function(
							message) {
						if (!stompservice.heartBeatFilter(message)) {
							var body = message.body;
							console.log('helloMessage received: ' + body);
							var json = JSON.parse(body);
							var hellos =  json.hellos;
							console.log('hellos: ' + hellos);
							var collection =hellos.collection;
							console.log('collection: ' + collection);
							for (var i in collection) {
								$scope.helloMessages.push(collection[i]);
								}
							 $scope.$apply();
							
						}
					});
				}
				stompservice.send("/queue/helloJMSEntryPoint",
						{"httpRequestType":"POST"}, '{"helloMessage": "' + $scope.helloMessage + '"}');


			};
			$scope.refreshHello = function() {
				
				stompservice.send("/queue/helloJMSEntryPoint",
						{"httpRequestType":"GET"}, '');
			};
		});
