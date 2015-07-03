# Karaf Features: Messaging


## What is it?

Enabling messaging and message transformation for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
<dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.feature.messaging</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now use asynchronous messaging in your app (see messaging and messaging-cdi integration tests on how to do) 
