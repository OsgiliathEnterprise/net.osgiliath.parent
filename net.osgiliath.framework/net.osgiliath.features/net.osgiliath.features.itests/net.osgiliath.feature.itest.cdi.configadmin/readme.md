# CDI integration test
To integrate cdi, you've to look at these files:

* [osgi.bnd CDI extension requirement](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.configadmin/osgi.bnd)
* [Beans.xml mandatory file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.configadmin/src/main/resources/META-INF/beans.xml)
* [CDI bean consuming properties via injection](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi.configadmin/src/main/java/net/osgiliath/feature/itest/cdi/configadmin/impl/PropertyConsumer.java)

Relevant doc:
* https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
* https://deltaspike.apache.org/documentation/configuration.html