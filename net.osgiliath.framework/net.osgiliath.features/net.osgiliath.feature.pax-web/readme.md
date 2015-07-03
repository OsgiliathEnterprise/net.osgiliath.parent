# Karaf Features: Pax-web


## What is it?

Low level web features for Osgiliath

## How to use?

Reference this feature in your pom:
```
    <dependency>
      <groupId>net.osgiliath.framework</groupId>
      <version>${osgiliath.maven.version.version}</version>
      <artifactId>net.osgiliath.feature.pax-web</artifactId>
      <type>xml</type>
      <classifier>features</classifier>
    </dependency>
```
And don't forget to add the feature you want to use on your app features.xml

Can now have standard (low level) web features in your project (wars, http services, etc)...