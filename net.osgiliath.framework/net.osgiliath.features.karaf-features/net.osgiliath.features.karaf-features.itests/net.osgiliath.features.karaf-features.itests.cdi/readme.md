# CDI integration test
To integrate cdi, you've to look at these files:

Declaration of Require-Capabilities in osgi.bnd
Mandatory presence of src/main/resources/META-INF/beans.xml
CDI Osgi service registration: src/main/java/net/osgiliath/cdi/impl/Provider.java
CDI Osgi service consuming via inject: src/main/java/net/osgiliath/cdi/impl/Consumer.java

Relevant doc: https://ops4j1.jira.com/wiki/display/PAXCDI/Pax+CDI