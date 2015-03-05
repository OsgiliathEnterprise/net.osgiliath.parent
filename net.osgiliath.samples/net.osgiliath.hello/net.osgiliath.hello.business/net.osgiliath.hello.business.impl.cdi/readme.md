# Business module: cdi way

## Relevant files
### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/osgi.bnd): jaxrs, camel, eager start, validation, properties capabilities

### Validation
* [Validation interceptor](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/resources/META-INF/beans.xml)
* [Validator declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/conf/HelloCDIValidator.java)
* [Validation usage: violation retreiving and throwing HTTP 500 error containing errors](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/cdi/impl/services/impl/HelloServiceJaxRS.java)

### REST Services
* [REST http access definition](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/cdi/impl/HelloServiceJaxRS.java)
* [REST service implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/cdi/impl/services/impl/JpaHelloServiceJaxRS.java)
* [REST service application (service delivery)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/javaconf/CXFApplication.java)
* [Model entity to send](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.spi/src/main/java/net/osgiliath/hello/business/model/Hellos.java)
* [Jaxb index file enabling marshalling/unmarshalling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.spi/src/main/java/net/osgiliath/hello/business/model/jaxb.index)

### Access REST UI management
* With the use of Swagger (defining your API with annotations), you have access to a management UI allowing you to query your services.
* Just navigate to your host:port/api-docs to see the ui
* Then enter http://localhost:port/cxf/yourserver/api-docs on the 'server url' input field
* You can finally query your model elements 

### Messaging services
* [Messaging components import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/conf/HelloComponents.java)
* [Messaging implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/cdi/impl/services/impl/HelloServiceJMS.java)

### Testing
* [Testing REST and messaging services](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/it/java/net/osgiliath/hello/business/impl/cdi/services/impl/itests/ITHelloServiceJaxRS.java)

### Production
* [Business and business itest feature declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/src/main/resources/net.osgiliath.hello.features.xml)
