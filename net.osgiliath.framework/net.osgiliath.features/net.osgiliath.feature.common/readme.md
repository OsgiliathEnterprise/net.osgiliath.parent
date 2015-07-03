# Karaf Features: Common


## What is it?

Common dependencies for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
        <dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-common</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

These features are commons deps (logging, annotations...)
