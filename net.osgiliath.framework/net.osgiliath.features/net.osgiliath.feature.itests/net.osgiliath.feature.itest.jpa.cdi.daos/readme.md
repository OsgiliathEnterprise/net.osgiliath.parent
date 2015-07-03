# JPA CDI integration test: daos
JPA persistence with Pax-JPA/Deltaspike-data

## Relevant files
* [Simple JPA Entity](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.feature/net.osgiliath.feature.itests/net.osgiliath.feature.itests.jpa.cdi.entities/src/main/java/net/osgiliath/jpa/cdi/entities/HelloEntity.java)
* [Persistence.xml JPA mapping file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.jpa.cdi.entities/src/main/resources/META-INF/persistence.xml)
* [Openjpa maven plugin declaration (pom.xml)](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itests.jpa.cdi.entities/pom.xml)
* [Database automatic creation from config file](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.feature/src/main/resources/net.osgiliath.feature.itests.feature.database.cfg)
* [Feature to import config and test](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.framework/net.osgiliath.features/net.osgiliath.feature.itests/net.osgiliath.feature.itest.feature/src/main/resources/net.osgiliath.feature.itests.feature.xml)

## Relevant Documentation
* Pax-JPA: https://ops4j1.jira.com/wiki/display/PAXJPA/Getting+Started
* Pax-JDBC: https://ops4j1.jira.com/wiki/display/PAXJDBC/Pax+JDBC