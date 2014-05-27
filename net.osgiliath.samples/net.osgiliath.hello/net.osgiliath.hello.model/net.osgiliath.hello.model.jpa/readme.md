# Persistence module

## Noticeable files

* [osgi.bnd](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/osgi.bnd): spring-data and openjpa imports, persistence.xml location declaration
* [pom.xml](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/pom.xml): openjpa plugin declaration, by default, all classes suffixed by 'Entity' will be enhanced (see management [here](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.poms/net.osgiliath.pom.repositories/net.osgiliath.pom.reporting/net.osgiliath.pom.plugins/pom.xml))
* [persistence.xml](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/resources/META-INF/persistence.xml) persistent classes mapping and database referencing.
* [blueprint context](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/resources/OSGI-INF/blueprint/model.osgi-context.xml) jpa context creation and injection with transactional behaviour and OSGI service exporting.
* [sample entity](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/java/net/osgiliath/hello/model/jpa/model/HelloEntity.java) with Xml marshalling, and validation enabled
* [Jaxb marshaling index](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/java/net/osgiliath/hello/model/jpa/model/jaxb.index) listing xml marshallable classe on the package
* [Repository definition](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/java/net/osgiliath/hello/model/jpa/repository/HelloObjectRepository.java) to save, query or update the database
* [Repository implementation](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/net.osgiliath.hello.model.jpa/src/main/java/net/osgiliath/hello/model/jpa/repository/impl/HelloObjectJpaRepository.java)
* [Model feature declaration](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.features/src/main/resources/net.osgiliath.hello.features.xml)

## Using database versionning

In order to use database versioning, follow this steps:
* Ensure the production database is started and have the same port as the one specified in the [parent pom](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.model/pom.xml)
* Execute mvn clean install -Pdb-diff to have the according tables structure changelog
* Then add the generated changelogs on the database module/src/main/resources/db/db.changelog.xml
```
<databaseChangeLog>
<include file="db/db-20121120_120949.changelog.xml" />
</databaseChangeLog>
```  
* Execute mvn clean install -Pdb-data to retrieve ALL the production data on a changelog (it will be located on the db folder of the database module sources)

This file needs to be updated, as liquibase:
Dumps all data. You will have to keep only the added data to your db from the previous db version. No diff here performed by Liquibase.
Data is not properly ordered regarding referential integrity

Once this file has been cleaned-up, we can include it as well in our main db.changelog.xml file:
```
<include file="db/db-data-20121128_170043.changelog.xml" />
```

