# Business module: cdi way

## Relevant files
### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp.simple/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/osgi.bnd): jaxrs, camel, eager start, validation, properties capabilities

### Validation
* [Validation interceptor](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/resources/META-INF/beans.xml)
* [Validator declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/conf/CDIValidator.java)
* [Validation usage: violation retreiving and throwing HTTP 500 error containing errors](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/rest/HelloServiceImpl.java)

### REST Services
* [REST http access definition](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/rest/HelloServiceJaxRS.java)
* [REST service implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/rest/HelloServiceImpl.java)
* [REST service application (service delivery)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/conf/CXFApplication.java)
* [Model entity to send](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.spi/src/main/java/net/osgiliath/sample/webapp/spi/model/Hellos.java)
* [Jaxb index file enabling marshalling/unmarshalling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.sample.webapp.simple/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.spi/src/main/java/net/osgiliath/sample/webapp/spi/model/jaxb.index)

### Access REST UI management
* With the use of Swagger (defining your API with annotations), you have access to a management UI allowing you to query your services.
* Just navigate to your host:port/api-docs to see the ui
* Then enter http://localhost:port/cxf/yourserver/api-docs on the 'server url' input field
* You can finally query your model elements 

### Messaging services
* [Messaging components import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/conf/CDIMessagingComponents.java)
* [Messaging implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/net/osgiliath/sample/webapp/impl/HelloServiceJMS.java)

### Testing
* [Testing REST and messaging services](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp.simple/net.osgiliath.sample.webapp.business/net.osgiliath.sample.webapp.business.impl/src/main/java/itests/net/osgiliath/sample/webapp/impl/ITHelloServiceJaxRS.java)

### Production
* [Business and business itest feature declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.features/src/main/resources/net.osgiliath.sample.webapp.features.xml)
