# JaxRS Servlet 3 CDI integration test (Not working cause not wel thought)
Servlet 3 cxf web service registration

## Relevant files
Main servlet: /src/main/java/conf/JaxRSServicesProviderServlet.java
JaxRS Application (Service registering): /src/main/java/conf/JaxRSApplication.java
Annotations to map service on the service interface: /src/main/java/net/osgiliath/features/karaf/jaxrs/web/HelloServiceJaxRS.java
Service implementation and service registration via cdi: /src/main/java/net/osgiliath/features/karaf/jaxrs/web/impl/HelloServiceImpl.java
JaxB annotated model element to be able to convert it in xml: /src/main/java/net/osgiliath/features/karaf/jaxrs/web/model/HelloObject.java
Index file referencing jaxb annotated classes: /src/main/java/net/osgiliath/features/karaf/jaxrs/web/model/jaxb.index

## Relevant doc
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
CXF-JaxRS: http://cxf.apache.org/docs/jax-rs.html