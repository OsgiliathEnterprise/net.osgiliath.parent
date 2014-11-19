 # Business Archetype with blueprint

Its made to process datas: for example make computation.
It also exposes services, consume data, and in a general way (even if its not mandatory) is an intermediary to the Model part

### Default technologies:
Guava, Lombok, CXF-JaxRS, JaxB, JSonP, Camel-JMS (producer/consumer), Jsr303, Spring-Security, blueprint, junit, Mockito, Pax-Exam (integration tests)


### Technologies
Karaf features

### Building/using

Add to the settings.xml of your maven if you're behind a proxy:
```
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


