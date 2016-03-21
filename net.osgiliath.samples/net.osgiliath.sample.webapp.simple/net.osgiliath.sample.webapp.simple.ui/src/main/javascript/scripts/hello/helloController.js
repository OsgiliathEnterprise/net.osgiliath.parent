'use strict';
angular.module('hello').controller('HelloController',
		function($scope, stompservice, $http) {
			$scope.helloMessage = '';
			$scope.helloMessages = [];
			$scope.registered = false;
			$scope.errors = '';
			$scope.sendHelloSync = function() {
				$http({
				       method: 'POST',
				       url: 'http://localhost:8181/cxf/helloService/hello',
				       data: '{"helloEntity" : {"helloMessage": "' + $scope.helloMessage + '"}}',
				        headers: {
				            'Content-Type': 'application/json'
				   }});
				$http.get('http://localhost:8181/cxf/helloService/hello').
		        	success(function(data) {
		        		
		        		//var json = JSON.parse(data);
		        		console.log('helloMessage received: ' + data);
						var hellos = data.hellos.helloCollection;
						if (hellos !== undefined) {
							$scope.helloMessages = [];
							console.log('hellos: ' + hellos);
							for ( var i in hellos) {
								$scope.helloMessages.push(hellos[i]);
							}
							//$scope.$apply();
						}
		        	});
			}
			$scope.sendHelloAsync = function() {
				$scope.errors = '';
				if (!$scope.registered) {
					stompservice.subscribe('/topic/helloServiceQueueOut', function(
							message) {
						if (!stompservice.heartBeatFilter(message)) {
							var body = message.body;
							console.log('helloMessage received: ' + body);
							var json = JSON.parse(body);
							var hellos = json.helloCollection;
							if (hellos !== undefined) {
								$scope.helloMessages = [];
								console.log('hellos: ' + hellos);
								for ( var i in hellos) {
									$scope.helloMessages.push(hellos[i]);
								}
								$scope.$apply();
							}
						}
					});
					stompservice.subscribe('/queue/MessagingErrors', function(
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
				stompservice.send('/queue/helloServiceQueueIn', {
					'httpRequestType' : 'POST'
				}, '{"helloMessage": "' + $scope.helloMessage + '"}');
			};
		});
