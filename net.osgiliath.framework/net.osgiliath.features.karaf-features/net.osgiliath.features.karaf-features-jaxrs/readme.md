# Karaf Features: JaxRS


## What is it?

Enabling RESTFul services for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
<dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-jaxrs</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now use JaxRS feature (see jaxRS itests or JaxRS cdi ones)
