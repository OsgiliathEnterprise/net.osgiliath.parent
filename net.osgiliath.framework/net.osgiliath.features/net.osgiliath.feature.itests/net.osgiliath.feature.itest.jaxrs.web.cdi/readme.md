# JaxRS Servlet 3 CDI integration test (Not working cause not wel thought)
Servlet 3 cxf web service registration

## Relevant files
* [Main servlet](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/conf/JaxRSCDIServicesProviderServlet.java)
* [JaxRS Application (Service registering)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/conf/JaxRSCDIApplication.java)
* [Annotations to map service on the service interface](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/net/osgiliath/features/karaf/jaxrs/web/cdi/HelloServiceJaxRS.java)
* [Service implementation and service registration via cdi](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/net/osgiliath/features/karaf/jaxrs/web/cdi/impl/HelloServiceImpl.java)
* [JaxB annotated model element to be able to convert it in xml](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/net/osgiliath/features/karaf/jaxrs/web/cdi/model/HelloObject.java)
* [Index file referencing jaxb annotated classes](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jaxrs.web.cdi/src/main/java/net/osgiliath/features/karaf/jaxrs/web/cdi/model/jaxb.index)

## Relevant doc
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
CXF-JaxRS: http://cxf.apache.org/docs/jax-rs.html