# Karaf Features: CXF


## What is it?

CXF wrapping feature support for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
	<dependency>
		<groupId>net.osgiliath.framework</groupId>
		<version>${osgiliath.maven.version.version}</version>
		<artifactId>net.osgiliath.feature.cxf</artifactId>
		<type>xml</type>
		<classifier>features</classifier>
	</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can use these feature with the werservice one, using webservice in your services


