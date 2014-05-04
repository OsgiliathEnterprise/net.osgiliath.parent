# Karaf Features: CDI


## What is it?

Context dependency injection for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
<dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-cdi</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

Then, add these capabilities on your osgi.bnd:

```
Require-Capability: osgi.extender;filter:="(osgi.extender=pax.cdi)", org.ops4j.pax.cdi.extension;filter:="(extension=pax-cdi-extension)"
```
You can also add a number of CDI portable extensions this way on your consumer bundle

Finally, add a mandatory META-INF/beans.xml file on your consumer project (for example, look at the cdi itests).

