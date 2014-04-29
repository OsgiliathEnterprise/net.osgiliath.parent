# Business module: blueprint way

## Relevant files
### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/osgi.bnd): jaxrs, camel, eager start, validation, properties capabilities

### Validation
* [Validation interceptor](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/resources/META-INF/beans.xml)
* [Validator declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/conf/CDIValidator.java)
* [Validation usage: violation retreiving and throwing HTTP 500 error containing errors](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/impl/services/impl/HelloServiceJaxRS.java)

### REST Services
* [REST http access definition](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/impl/HelloServiceJaxRS.java)
* [REST service implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/impl/services/impl/HelloServiceJaxRS.java)
* [Model entity to send](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.spi/src/main/java/net/osgiliath/hello/business/model/Hellos.java)
* [Jaxb index file enabling marshalling/unmarshalling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.spi/src/main/java/net/osgiliath/hello/business/model/jaxb.index)

### Messaging services
* [Messaging components import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/conf/Components.java)
* [Messaging implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.business/net.osgiliath.hello.business.impl.cdi/src/main/java/net/osgiliath/hello/business/impl/services/impl/HelloServiceJMS.java)

