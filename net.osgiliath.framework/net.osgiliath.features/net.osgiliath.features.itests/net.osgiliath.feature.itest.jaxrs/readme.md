# JaxRS CDI integration test
To integrate JaxRS (Restful web services) with CDI, you've to look at these files:

* [Required capabilities](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.feature/net.osgiliath.feature.itests/net.osgiliath.feature.itests.jaxrs.cdi/osgi.bnd)
* [Annotations to map service on the service interface](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs/src/main/java/net/osgiliath/feature/itest/jaxrs/HelloServiceJaxRS.java)
* [Application to expose all your services](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs/src/main/java/conf/CXFApplication.java)
* [Service implementation and service registration via cdi](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.feature/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs/src/main/java/net/osgiliath/feature/itest/jaxrs/impl/HelloServiceImpl.java)
* [JaxB annotated model element to be able to convert it in xml](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs/src/main/java/net/osgiliath/feature/itest/jaxrs/model/HelloObject.java)
* [Index file referencing jaxb annotated classes](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs/src/main/java/net/osgiliath/feature/itest/jaxrs/model/jaxb.index)

Relevant doc: https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
Relevant doc: http://cxf.apache.org/docs/jax-rs.html