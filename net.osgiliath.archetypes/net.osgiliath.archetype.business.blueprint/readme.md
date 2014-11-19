 # Business Archetype with blueprint

Its made to process datas: for example make computation.
It also exposes services, consume data, and in a general way (even if its not mandatory) is an intermediary to the Model part

### Default technologies:
Guava, Lombok, CXF-JaxRS, JaxB, JSonP, Camel-JMS (producer/consumer), Jsr303, Spring-Security, blueprint, junit, Mockito, Pax-Exam (integration tests)


### Technologies
Karaf features

### Building/using

Add to the settings.xml of your maven if you're behind a proxy:
```xml
&lt;settings&gt;
  [...]
  &lt;profiles&gt;
    &lt;profile&gt;
      &lt;id&gt;osgiliath-exec&lt;/id&gt;
      &lt;properties&gt;
        &lt;mavenSettings.location&gt;/usr/share/maven/conf/settings.xml&lt;/mavenSettings.location&gt;
      &lt;/properties&gt;
    &lt;/profile&gt;
  &lt;/profiles&gt;

  &lt;activeProfiles&gt;
    &lt;activeProfile&gt;osgiliath-exec&lt;/activeProfile&gt;
  &lt;/activeProfiles&gt;
&lt;/settings&gt;
```
If you're on a Mac, you've also to add a java.home property pointing on your jdk 1.7 installation root.


