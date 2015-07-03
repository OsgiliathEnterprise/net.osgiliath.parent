# JSR-303 (bean validation) with blueprint integration tests

Bean validation tests with osgiliath

## Relevant files
Manifest requirements: 

* [osgi.bnd imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation/osgi.bnd)
* [Validator factory declaration and injection](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation/src/main/resources/OSGI-INF/blueprint/validation.osgi-context.xml)
* [Validation annotated bean](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation/src/main/java/net/osgiliath/validation/HelloObject.java)
* [Validated service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.validation/src/main/java/net/osgiliath/validation/impl/ValidatorFactorySample.java)

## Relevant doc

* Aries blueprint: http://aries.apache.org/
* Hibernate validator: http://hibernate.org/validator/
