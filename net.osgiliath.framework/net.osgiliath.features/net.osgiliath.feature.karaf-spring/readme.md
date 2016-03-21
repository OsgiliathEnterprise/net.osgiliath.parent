# Karaf Features: Spring


## What is it?

Enabling low level Spring features for Osgiliath (core, mvc, data-jpa)

## How to use?

Reference this feature in your pom:
```
        <dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.feature.karaf-spring</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now use Spring features in your project
