# Parent Archetype
Describes an entire application. Is used to be a container of many businesses/routes/model modules.
It embed a descriptor allowing you to deploy an entire app in a command.

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


