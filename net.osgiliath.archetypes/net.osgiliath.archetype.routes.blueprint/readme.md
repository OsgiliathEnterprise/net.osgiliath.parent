# Route archetype with blueprint

Made to route and transform messages between your different business/model module: while usefull in large projects, it maybe boilerplate on small ones.

### Default technologies:
Camel-jms, Camel-CXFRS, Camel-CDI, Camel-Xstream, Camel-Jaxb, Camel-Json, Camel-XmlJson, Camel-blueprint, springockito (mock tests), camel-spring(for tests).


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


