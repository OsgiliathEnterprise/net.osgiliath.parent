# Messaging (e.g. JMS) Integration tests with CDI

Messagin tests with CDI (e.g. annotations)

## Relevant files
* [Component imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.messaging/src/main/java/conf/CDIMessagingComponents.java)
* [Message producer](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.messaging/src/main/java/net/osgiliath/feature/itest/messaging/repository/impl/HelloJMSCDIRepository.java) (consumer annotation don't work in CDI, take care!)
* [Messages consumer](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.messaging/src/main/java/net/osgiliath/feature/itest/messaging/repository/impl/RouteConsumer.java)
* [Manifest Requierments](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.messaging/osgi.bnd)

## Relevant Documentation:
Camel JMS: http://camel.apache.org/jms.html
Camel-CDI: http://camel.apache.org/cdi.html
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI