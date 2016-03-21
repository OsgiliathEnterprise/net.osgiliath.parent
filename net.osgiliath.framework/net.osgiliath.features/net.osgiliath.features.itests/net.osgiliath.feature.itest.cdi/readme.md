# CDI integration test

To integrate cdi, you've to look at these files:

* [osgi.bnd CDI extension requierement](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.cdi/osgi.bnd)
* [Beans.xml mandatory file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi/src/main/resources/META-INF/beans.xml)
* [CDI Osgi service registration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi/src/main/java/net/osgiliath/feature/itest/cdi/impl/Provider.java)
* [CDI Osgi service consuming via inject](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.cdi/src/main/java/net/osgiliath/feature/itest/cdi/impl/Consumer.java)

Relevant doc: 

* https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI