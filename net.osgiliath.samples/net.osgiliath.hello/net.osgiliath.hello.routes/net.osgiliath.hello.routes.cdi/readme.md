# Routing CDI way

## Relevant files

### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/osgi.bnd): capabilities requirements
* [mandatory beans.xml file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/main/resources/META-INF/beans.xml)

### Routing

* [Components import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/main/java/conf/Components.java)
* [Camel context usage, property usage, component injection via annotations](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/main/java/net/osgiliath/hello/routes/HelloRoute.java)
* [Properties declaration on a configuration file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/src/main/resources/net.osgiliath.hello.features.cfg)
* [Maven properties package bundling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/pom.xml)

### Messaging
* [Messaging components import via configuration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/main/java/conf/Components.java)
* [Message reception, transformation, sent to REST, resent to queues/topics](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/main/java/net/osgiliath/hello/routes/HelloRoute.java)

### Unit testing
* [Spring camel context test declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/test/resources/spring/net.osgiliath.hello.routes.test-context.xml)
* [Mock direct properties declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/test/resources/cfg/net.osgiliath.hello.routes.test.cfg)
* [Endpoint mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/test/java/mocks/routes/HelloRouteEndpointsMock.java)
* [Service mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/test/java/mocks/HelloServiceMock.java)
* [Unit test class](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.cdi/src/test/java/net/osgiliath/hello/routes/HelloRouteTest.java)
