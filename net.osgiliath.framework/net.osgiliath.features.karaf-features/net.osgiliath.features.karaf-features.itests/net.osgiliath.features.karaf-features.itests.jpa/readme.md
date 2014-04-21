# JPA integration test
JPA persistence with blueprint

## Relevant files
Simple JPA Entity: /src/main/java/net/osgiliath/jpa/model/HelloEntity.java
DAO/Repository declaration: /src/main/java/net/osgiliath/jpa/repository/HelloRepository.java
DAO/Repository implementation: /src/main/java/net/osgiliath/jpa/repository/impl/HelloJpaRepository.java
Persistence.xml JPA mapping file: /src/main/resources/META-INF/persistence.xml
Blueprint file referencing bean mapping, enabling spring-data, aries jpa context references, transactions, and exporting repository as Osgi service
Openjpa maven plugin declaration (pom.xml)

## Relevant Documentation
Spring data: http://projects.spring.io/spring-data/
Aries blueprint: http://aries.apache.org/