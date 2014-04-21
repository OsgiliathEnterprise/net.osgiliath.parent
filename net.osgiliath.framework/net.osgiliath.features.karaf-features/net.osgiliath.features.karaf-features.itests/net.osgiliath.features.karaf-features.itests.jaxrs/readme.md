# JaxRS integration test
To integrate JaxRS (Restful web services), you've to look at these files:

Providers imports in osgi.bnd
Annotations to map service on the service interface: /src/main/java/net/osgiliath/jaxrs/repository/HelloRepository.java
Service implementation (nothing particular): /src/main/java/net/osgiliath/jaxrs/repository/impl/HelloJaxRSRepository.java
Declaration of bean, bus and service in /src/main/resources/OSGI-INF/blueprint/service-osgi.xml
JaxB annotated model element to be able to convert it in xml: /src/main/java/net/osgiliath/jaxrs/HelloEntity.java

## Relevant doc
CXF: http://cxf.apache.org/docs/jax-rs.html
Aries blueprint: http://aries.apache.org/