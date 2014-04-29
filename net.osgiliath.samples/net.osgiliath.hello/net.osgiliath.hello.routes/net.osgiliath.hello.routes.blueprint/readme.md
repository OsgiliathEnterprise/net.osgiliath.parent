# Routing blueprint way

## Relevant files

### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/osgi.bnd): camel imports

### Routing

* [Camel context declaration on blueprint file, and route injection](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/main/resources/OSGI-INF/blueprint/routes.osgi-context.xml)
* [Blueprint compendium properties import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/main/resources/OSGI-INF/blueprint/routes.osgi-context.xml)
* [Properties usage on a route](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/main/java/net/osgiliath/hello/routes/HelloRoute.java)
* [Properties declaration on a configuration file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/src/main/resources/net.osgiliath.hello.features.cfg)
* [Maven properties package bundling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/pom.xml)

### Messaging
* [Messaging components import via blueprint](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/main/resources/OSGI-INF/blueprint/routes.osgi-context.xml)
* [Message reception, transformation, sent to REST, resent to queues/topics](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/main/java/net/osgiliath/hello/routes/HelloRoute.java)

### Unit testing
* [Spring camel context test declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/test/resources/spring/net.osgiliath.hello.routes.test-context.xml)
* [Mock direct properties declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/test/resources/cfg/net.osgiliath.hello.routes.test.cfg)
* [Endpoint mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/test/java/mocks/routes/HelloRouteEndpointsMock.java)
* [Service mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/test/java/mocks/HelloServiceMock.java)
* [Unit test class](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.routes/net.osgiliath.hello.routes.blueprint/src/test/java/net/osgiliath/hello/routes/HelloRouteTest.java)
