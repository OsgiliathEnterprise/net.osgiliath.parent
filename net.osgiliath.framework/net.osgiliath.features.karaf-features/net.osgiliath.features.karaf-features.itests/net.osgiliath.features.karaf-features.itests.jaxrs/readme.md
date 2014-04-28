# JaxRS integration test
To integrate JaxRS (Restful web services), you've to look at these files:

* [Providers imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs/osgi.bnd)
* [Annotations to map service on the service interface](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs/src/main/java/net/osgiliath/jaxrs/repository/HelloRepository.java)
* [Service implementation (nothing particular)](: )https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs/src/main/java/net/osgiliath/jaxrs/repository/impl/HelloJaxRSRepository.java)
* [Declaration of bean, bus and service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs/src/main/resources/OSGI-INF/blueprint/service-osgi.xml)
* [JaxB annotated model element to be able to convert it in xml](: )https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.jaxrs/src/main/java/net/osgiliath/jaxrs/HelloEntity.java)

## Relevant doc
CXF: http://cxf.apache.org/docs/jax-rs.html
Aries blueprint: http://aries.apache.org/