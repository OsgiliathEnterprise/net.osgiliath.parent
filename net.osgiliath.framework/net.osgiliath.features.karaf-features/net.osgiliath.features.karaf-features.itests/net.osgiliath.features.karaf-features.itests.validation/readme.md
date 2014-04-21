# JSR-303 (bean validation) CDI integration tests

Bean validation tests with osgiliath

## Relevant files
Manifest requierments: osgi.bnd
Beans.xml mandatory file: /src/main/resources/META-INF/beans.xml
Validator factory declaration: /src/main/java/conf/CDIValidator.java
Validation aware bean: /src/main/java/net/osgiliath/validation/HelloObject.java
Validated service: /src/main/java/net/osgiliath/validation/impl/ValidatorFactorySample.java

## Relevant doc
Pax-CDI : https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI
Hibernate validator: http://hibernate.org/validator/