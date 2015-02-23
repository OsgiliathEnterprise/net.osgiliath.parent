# Karaf Features: Enterprise


## What is it?

Enabling low level Enterprise features for Osgiliath (jndi, scr, connector)

## How to use?

Reference this feature in your pom:
```
        <dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-enterprise</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can now use Enterprise features in your project
