# Archetypes

You'll find here all the project skeleton generators for osgiliath EF programming.

## Model Archetype

This archetype is here to declare database (creation and access), your dao's and entities:

* Entities are the representation of your RDBMS tables in the Java world, you'll be able to save/retreives these datas, this is also known as Model objects.
* Dao permits to save, retreive and query the database to retreive/save entities
 
### Default technologies
Lombok, Jsr 303 (bean validation), Blueprint, Spring-data-JPA, JPA 2, JTA (transactions), Derby as file database, Junit, Mockito

## Business Archetype

Its made to process datas: for example make computation.
It also exposes services, consume data, and in a general way (even if its not mandatory) is an intermediary to the Model part

### Default technologies:
Guava, Lombok, CXF-JaxRS, JaxB, JSonP, Camel-JMS (producer/consumer), CDI, Jsr303, Spring-Security, DeltaSpike-Core, junit, Mockito, Pax-Exam (integration tests)

## Route archetype

Made to route and transform messages between your different business/model module: while usefull in large projects, it maybe boilerplate on small ones.

### Default technologies:
Camel-jms, Camel-CXFRS, Camel-CDI, Camel-Xstream, Camel-Jaxb, Camel-Json, Camel-XmlJson, Camel-blueprint (CDI alternative), springockito (mock tests), camel-spring(for tests).

## UI Archetype
A web client for your application, can comunicate by default with routes or businesses modules via WebSocket (STOMP/StompXA) or REST.

### Technologies
Grunt, Bower, Npm, AngularJs, AngularUI, Twitter bootstrap, Karma (tests), PhantomJs (e2e tests)

## Parent Archetype
Describes an entire application. Is used to be a container of many businesses/routes/model modules.
It embed a descriptor allowing you to deploy an entire app in a command.

### Technologies
Karaf features

### Building/using

Add to the settings.xml of your maven if you're behind a proxy:
```xml
<settings>
  [...]
  <profiles>
    <profile>
      <id>osgiliath-exec</id>
      <properties>
        <mavenSettings.location>/usr/share/maven/conf/settings.xml</mavenSettings.location>
      </properties>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>osgiliath-exec</activeProfile>
  </activeProfiles>
</settings>
```
If you're on a Mac, you've also to add a java.home property pointing on your jdk 1.7 installation root.


