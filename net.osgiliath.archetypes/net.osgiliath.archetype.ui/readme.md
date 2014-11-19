#  UI Archetype
A web client for your application, can comunicate by default with routes or businesses modules via WebSocket (STOMP/StompXA) or REST.

### Technologies
Grunt, Bower, Npm, AngularJs, AngularUI, Twitter bootstrap, Karma (tests), PhantomJs (e2e tests)


### Building/using

Add to the settings.xml of your maven if you're behind a proxy:
```xml
    <settings>
      <profiles>
        <profile>
          <id>osgiliath-exec</id>
          <properties>
            <maven.user.settings.default>/usr/share/maven/conf/settings.xml</maven.user.settings.default>
          </properties>
        </profile>
      </profiles>
      <activeProfiles>
        <activeProfile>osgiliath-exec</activeProfile>
      </activeProfiles>
    </settings>
```
Also install ruby with sass and compass gems, npm with grunt-cli , bower and phantomjs nodes

If you're on a Mac, you've also to add a java.home property pointing on your jdk 1.7 installation root.


