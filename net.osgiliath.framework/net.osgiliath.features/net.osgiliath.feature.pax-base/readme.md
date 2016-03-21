# Karaf Features: Pax-base


## What is it?

Low level OPS4J feature for Osgiliath

## How to use?

Reference this feature in your pom:
```
    <dependency>
      <groupId>net.osgiliath.framework</groupId>
      <version>${osgiliath.maven.version.version}</version>
      <artifactId>net.osgiliath.feature.pax-base</artifactId>
      <type>xml</type>
      <classifier>features</classifier>
    </dependency>
```
And don't forget to add the feature you want to use on your app features.xml

All ops4j feature will now consume this feature