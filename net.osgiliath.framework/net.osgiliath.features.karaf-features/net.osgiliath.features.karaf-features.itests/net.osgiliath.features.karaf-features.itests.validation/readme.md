# JSR-303 (bean validation) with blueprint integration tests

Bean validation tests with osgiliath

## Relevant files
Manifest requirements: 

* [osgi.bnd imports](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.validation/osgi.bnd)
* [Validator factory declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.validation/src/main/resources/OSGI-INF/blueprint/validation.osgi-context.xml)
* [Validation annotated bean](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.validation/src/main/java/net/osgiliath/validation/HelloObject.java)
* [Validated service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features.karaf-features/net.osgiliath.features.karaf-features.itests/net.osgiliath.features.karaf-features.itests.validation/src/main/java/net/osgiliath/validation/impl/ValidatorFactorySample.java)

## Relevant doc

* Aries blueprint: http://aries.apache.org/
* Hibernate validator: http://hibernate.org/validator/
