# JPA integration test
JPA persistence with blueprint

## Relevant files
* [Simple JPA Entity](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jpa/src/main/java/net/osgiliath/jpa/model/HelloEntity.java)
* [DAO/Repository declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jpa/src/main/java/net/osgiliath/jpa/repository/HelloRepository.java)
* [DAO/Repository implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jpa/src/main/java/net/osgiliath/jpa/repository/impl/HelloJpaRepository.java)
* [Persistence.xml JPA mapping file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jpa/src/main/resources/META-INF/persistence.xml)
* [Blueprint file referencing bean mapping, enabling spring-data, aries jpa context references, transactions, and exporting repository as Osgi service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.jpa/src/main/resources/OSGI-INF/blueprint/model.osgi-context.xml)
* [Openjpa maven plugin declaration (pom.xml)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.jpa/pom.xml)
* [Database automatic creation from config file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.feature/src/main/resources/net.osgiliath.feature.itest.feature.database.cfg)
* [Feature to import config and test](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.feature/src/main/resources/net.osgiliath.feature.itest.feature.xml)

## Relevant Documentation
* Spring data: http://projects.spring.io/spring-data/
* Aries blueprint: http://aries.apache.org/
* Pax-JDBC: https://ops4j1.jira.com/wiki/display/PAXJDBC/Pax+JDBC