/**
 * Stomp protocol wrapper
 * 
 * @returns the stomp client-> websocket processing
 * Apache License V2, all rights reserved to charlie mordant
 */
'use strict';
angular.module('commonapp', []).service('stompservice', function(){
	this.protocol = 'ws';
	this.host = 'localhost';
	this.port = '61614';
	this.user = 'guest';
	this.password = 'guest';
	this.url = this.protocol + '://' + this.host + ':' + this.port;

	this.stompClient = Stomp.client(this.url);
	// this.stompClient.heartbeat.outgoing=20000;

	this.stompClient.connect(this.user, this.password, this.callback);
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
		var heartBeatId = heartBeat(this.stompClient, queue);
		var protocolData = {
			'connectionId' : id,
			'heartBeatId' : heartBeatId
		};
		return protocolData;
	};
	this.unsubscribe = function(id) {
		console.info('unsubscribing ID: ' + id.connectionId);
		this.stompClient.unsubscribe(id.connectionId);
		window.clearInterval(id.heartBeatId);
	};
	this.disconnect = function() {
		this.stompClient.disconnect(this.disconnect);
	};
	
	
	/**
	 * callback for connection
	 */
	this.callback=function() {
		console.info('client connected!');
	};
	/**
	 * Callback for disconnection
	 */
	this.disconnect=function() {
		console.info('client disconnected!');
	};
	/**
	 * Send heartbeat to keep stomp connection alive
	 * 
	 * @param client
	 *            the stomp client
	 * @param queue
	 *            the queue to send
	 * @returns the heartbeat ref
	 */
	function heartBeat(client, queue) {
		return setInterval(function() {
			client.send(queue, {
				webSocketMsgType : 'heartBeat'
			}, '');
		}, 3000);
	}
	/**
	 * Filter heartbeat message
	 * 
	 * @param message
	 *            the input message
	 * @returns {Boolean} true if the message is heartbeat
	 */
	function heartBeatFilter(message) {
		return message.headers.webSocketMsgType === 'heartBeat';
	}
	
});


