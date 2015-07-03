# Karaf Features: CXF


## What is it?

Web services features for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
        <dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-cxf</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

These features are mainly the original cxf karaf ones with slightly overriden deps  
