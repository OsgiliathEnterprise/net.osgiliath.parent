# Karaf Features: Derby


## What is it?

Derby database support for Osgiliath framework

## How to use?

Reference this feature in your pom:
```
<dependency>
			<groupId>net.osgiliath.framework</groupId>
			<version>${osgiliath.maven.version.version}</version>
			<artifactId>net.osgiliath.features.karaf-features-derby</artifactId>
			<type>xml</type>
			<classifier>features</classifier>
		</dependency>
```
And don't forget to add the feature you want to use on your app features.xml

You can use these feature with the jpa one, creating a blueprint database definition of your provided databases

```
<bean id="dataSource" class="org.apache.derby.jdbc.EmbeddedXADataSource">
		<property name="databaseName" value="${project.parent.artifactId}.database" />
		<property name="createDatabase" value="create" />
	</bean>
	<service ref="dataSource" interface="javax.sql.XADataSource">
		<service-properties>
			<entry key="osgi.jndi.service.name" value="jdbc/${project.parent.artifactId}.database.xa" />
		</service-properties>
	</service>
```

