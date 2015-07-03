# Karaf Features: Pax-cdi


## What is it?

Low level CDI features for Osgiliath

## How to use?

Reference this feature in your pom:
```
    <dependency>
      <groupId>net.osgiliath.framework</groupId>
      <version>${osgiliath.maven.version.version}</version>
      <artifactId>net.osgiliath.feature.pax-cdi</artifactId>
      <type>xml</type>
      <classifier>features</classifier>
    </dependency>
```
And don't forget to add the feature you want to use on your app features.xml

Can now have weld (CDI RI) access in your project. Cdi allows to inject beans via annotation and manage bean lifecycle.