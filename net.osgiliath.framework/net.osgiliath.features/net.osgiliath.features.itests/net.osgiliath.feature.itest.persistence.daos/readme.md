# JPA integration test
JPA persistence with blueprint

## Relevant files
* [Simple JPA Entity](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.persistence.entities/src/main/java/net/osgiliath/persistence/entities/HelloEntity.java)
* [DAO/Repository declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.persistence.daos/src/main/java/net/osgiliath/feature/itest/persistence/daos/HelloRepository.java)
* [DAO/Repository implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.persistence.daos/src/main/java/net/osgiliath/feature/itest/persistence/daos/impl/HelloJpaRepository.java)
* [Persistence.xml JPA mapping file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.persistence.daos/src/main/resources/META-INF/persistence.xml)
* [Openjpa maven plugin declaration (pom.xml) and blueprint maven plugin](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.persistence/pom.xml)
* [Database automatic creation from config file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.feature/src/main/resources/net.osgiliath.feature.itest.feature.database.cfg)
* [Feature to import config and test](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.feature/src/main/resources/net.osgiliath.feature.itest.feature.xml)

## Relevant Documentation
* Spring data: http://projects.spring.io/spring-data/
* Aries blueprint: http://aries.apache.org/
* Pax-JDBC: https://ops4j1.jira.com/wiki/display/PAXJDBC/Pax+JDBC