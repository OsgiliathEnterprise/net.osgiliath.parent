# Routing CDI way

## Relevant files

### Common
* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/osgi.bnd): capabilities requirements
* [mandatory beans.xml file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/main/resources/META-INF/beans.xml)

### Routing

* [Components import](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/main/java/net/osgiliath/sample/enterprise/routes/conf/HelloRouteCDIComponents.java)
* [Camel context usage, property usage, component injection via annotations](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/main/java/net/osgiliath/sample/enterprise/routes/HelloRoute.java)
* [Properties declaration on a configuration file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.features/src/main/resources/net.osgiliath.sample.enterprise.features.transport.cfg)
* [Maven properties package bundling](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.features/pom.xml)

### Messaging
* [Messaging components import via configuration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/main/java/net/osgiliath/sample/enterprise/routes/conf/HelloRouteCDIComponents.java)
* [Message reception, transformation, sent to REST, resent to queues/topics](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/net.osgiliath.hello.routes.cdi/src/main/java/net/osgiliath/sample/enterprise/routes/HelloRoute.java)

### Unit testing
* [Spring camel context test declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/test/resources/spring/net.osgiliath.sample.enterprise.routes.test-context.xml)
* [Mock direct properties declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/test/resources/cfg/net.osgiliath.sample.enterprise.routes.test.cfg)
* [Endpoint mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/test/java/mocks/net/osgiliath/sample/enterprise/routes/HelloCDIRouteEndpointsMock.java)
* [Service mocking](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routessrc/test/java/mocks/net/osgiliath/sample/enterprise/routes/HelloCDIServiceMock.java)
* [Unit test class](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.sample.enterprise/net.osgiliath.sample.enterprise.routes/src/test/java/net/osgiliath/sample/enterprise/routes/HelloRouteTest.java)
