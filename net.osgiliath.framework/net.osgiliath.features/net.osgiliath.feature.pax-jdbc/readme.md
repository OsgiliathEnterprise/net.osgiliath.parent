# OPS4J Feature: Pax-Jdbc


## What is it?

Enabling low level standard features for Osgiliath (jdbc, derby....)

## How to use?

Reference this feature in your pom:
```
<dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.feature.pax-jdbc</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now use pax-jdbc features in your project
