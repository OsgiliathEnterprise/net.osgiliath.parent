# JSR-303 (bean validation) with blueprint integration tests

Bean validation tests with osgiliath

## Relevant files
Manifest requirements: 

* [osgi.bnd imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation.spring/osgi.bnd)
* [Validator factory declaration and injection](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation.spring/src/main/resources/OSGI-INF/blueprint/validation.osgi-context.xml)
* [Validation annotated bean](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation.spring/src/main/java/net/osgiliath/feature/itest/validation/spring/HelloObject.java)
* [Validated service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation.spring/src/main/java/net/osgiliath/feature/itest/validation/spring/impl/ValidatorFactorySample.java)

## Relevant doc

* Aries blueprint: http://aries.apache.org/
* Hibernate validator: http://hibernate.org/validator/
