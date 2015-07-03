# CDI integration test
To integrate cdi, you've to look at these files:

* [osgi.bnd CDI extension requierement](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.properties/osgi.bnd)
* [Beans.xml mandatory file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.properties/src/main/resources/META-INF/beans.xml)
* [CDI bean consuming properties via injection](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.properties/src/main/java/net/osgiliath/cdi/properties/impl/PropertyConsumer.java)

Relevant doc:
* https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
* https://deltaspike.apache.org/documentation/configuration.html