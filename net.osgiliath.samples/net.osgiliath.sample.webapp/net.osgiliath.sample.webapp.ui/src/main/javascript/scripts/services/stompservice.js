/**
 * Stomp protocol wrapper
 * 
 * @returns the stomp client-> websocket processing
 * Apache License V2, all rights reserved to charlie mordant
 */
'use strict';
angular.module('commonapp', []).service('stompservice', function(){
	this.protocol = 'ws';
	this.host = '127.0.0.1';
	this.port = '${defaultWebSocketXAPort}';
	this.user = '${database.user}';
	this.password = '${database.password}';
	this.url = this.protocol + '://' + this.host + ':' + this.port ;
	this.stompClient = Stomp.client(this.url, 'v11.stomp');
	this.stompClient.heartbeat.outgoing = 30000;
	this.stompClient.heartbeat.incoming = 30000;
	this.stompClient.connect(this.user, this.password, function() {
		console.info('client connected!');
	}, function(error) {
	    // display the error's message header:
		console.error(error.message);
	} );
	
	console.info('connectStomp instanciated: client: ' + JSON.stringify(this.stompClient));

	this.send = function(queue, header, body) {
          console.info('Sending to queue: ' + queue + ', with header: ' + header + ', with body: ' + body + ', this:' + JSON.stringify(this));
          this.stompClient.send(queue, header, body);
        };
	
	
	this.subscribe = function(queue, callback) {
          console.info('Subscribing to queue: ' + queue);
          var id = this.stompClient.subscribe(queue, callback);
          console.info('stompObject: ' + JSON.stringify(this));
          console.info('Subscription ID: ' + id);
          var protocolData = {
            'connectionId' : id
          };
          return protocolData;
        };
	
	this.unsubscribe = function(id) {
		console.info('unsubscribing ID: ' + id.connectionId);
		this.stompClient.unsubscribe(id.connectionId);
	};
	this.disconnect = function() {
		this.stompClient.disconnect(this.disconnectcb);
	};
	

	/**
	 * Callback for disconnection
	 */
	this.disconnectcb=function() {
		console.info('client disconnected!');
	};
	
	
});


