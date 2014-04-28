# JaxRS Servlet 3 integration test
Servlet 3 cxf web service registration

## Relevant files
* [Main servlet](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/conf/JaxRSServicesProviderServlet.java)
* [JaxRS Application (Service registering)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/conf/JaxRSApplication.java)
* [Annotations to map service on the service interface](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/net/osgiliath/features/karaf/jaxrs/web/HelloServiceJaxRS.java)
* [Service implementation and service registration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/net/osgiliath/features/karaf/jaxrs/web/impl/HelloServiceImpl.java)
* [JaxB annotated model element to be able to convert it in xml](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/net/osgiliath/features/karaf/jaxrs/web/model/HelloObject.java)
* [Index file referencing jaxb annotated classes](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs.web/src/main/java/net/osgiliath/features/karaf/jaxrs/web/model/jaxb.index)


Relevant doc: http://cxf.apache.org/docs/jax-rs.html