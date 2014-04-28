# Messaging (e.g. JMS) Integration tests with CDI

Messagin tests with CDI (e.g. annotations)

## Relevant files
* [Component imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.messaging.cdi/src/main/java/conf/Components.java)
* [Message producer](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.messaging.cdi/src/main/java/net/osgiliath/messaging/repository/impl/HelloJMSCDIRepository.java) (consumer annotation don't work in CDI, take care!)
* [Messages consumer](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.messaging.cdi/src/main/java/net/osgiliath/messaging/repository/impl/RouteConsumer.java)
* [Manifest Requierments](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.messaging.cdi/osgi.bnd)

## Relevant Documentation:
Camel JMS: http://camel.apache.org/jms.html
Camel-CDI: http://camel.apache.org/cdi.html
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI