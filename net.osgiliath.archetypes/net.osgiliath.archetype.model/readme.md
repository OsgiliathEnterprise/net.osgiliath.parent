#  Model Archetype

This archetype is here to declare database (creation and access), your dao's and entities:

* Entities are the representation of your RDBMS tables in the Java world, you'll be able to save/retreives these datas, this is also known as Model objects.
* Dao permits to save, retreive and query the database to retreive/save entities
 
### Default technologies
Lombok, Jsr 303 (bean validation), Blueprint, Spring-data-JPA, JPA 2, JTA (transactions), Derby as file database, Junit, Mockito

### Building/using

Add to the settings.xml of your maven if you're behind a proxy:
```xml
    <settings>
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


