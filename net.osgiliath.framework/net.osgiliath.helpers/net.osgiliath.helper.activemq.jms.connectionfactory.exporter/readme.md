# Connection factory ActiveMQ broker default configuration

Default broker configuration for Osgiliath framework

You can change ports configuration with these properties:

"jmsTransportConnector.uri":"tcp://localhost:61616"
"jmsXATransportConnector.uri":"tcp://localhost:61626"
"stompTransportConnector.uri":"stomp://localhost:61612"
"stompXATransportConnector.uri":"stomp://localhost:61623"
"webSocketTransportConnector.uri":"ws://localhost:61614"
"webSocketXATransportConnector.uri":"ws://localhost:61624"


## Relevant files

Blueprint configuration: /src/main/resources/OSGI-INF/blueprint/connection-exporter.xml