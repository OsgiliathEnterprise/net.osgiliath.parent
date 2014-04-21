# Messaging (e.g. JMS) Integration tests with CDI

Messagin tests with CDI (e.g. annotations)

## Relevant files
Component imports: /src/main/java/conf/Components.java
Message producer: src/main/java/net/osgiliath/messaging/repository/impl/HelloJMSCDIRepository.java (consumer annotation don't work in CDI, take care!)
Messages consumer: src/main/java/net/osgiliath/messaging/repository/impl/RouteConsumer.java
Manifest Requierments: osgi.bnd

## Relevant Documentation:
Camel JMS: http://camel.apache.org/jms.html
Camel-CDI: http://camel.apache.org/cdi.html
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI