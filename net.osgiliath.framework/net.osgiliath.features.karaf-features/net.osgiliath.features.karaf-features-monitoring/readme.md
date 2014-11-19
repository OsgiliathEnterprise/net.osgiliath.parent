# Karaf Features: Monitoring


## What is it?

Monitoring/admin features for Osgiliath

## How to use?

Reference this feature in your pom:
```
    <dependency>
      <groupId>net.osgiliath.framework</groupId>
      <version>${osgiliath.maven.version.version}</version>
      <artifactId>net.osgiliath.features.karaf-features-monitoring</artifactId>
      <type>xml</type>
      <classifier>features</classifier>
    </dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now have access to monitoring features for Osgiliath (Swagger UI to query your REST services and Hawtio).

If you've annotated your bean with Swagger annotations and configured the Blueprint/CDI swagger bean (see hello blueprint sample to have an overview), you will be able to query your service going to service_url/api-docs/

Everytime, you'll be able to monitor your OSGI beans, Camel routes, MBeans... with the hawtio web console going to <server url>/hawtio/  
