h1. Karaf Features

h2. What is it?
This is bunch of Karaf features to enable a fully-fledged osgiliath ESB project

h2. How to use?
First you have to configure your maven settings.xml to mirror the osgiliath nexus (http://nexus.osgiliath.net/nexus/content/groups/public/) or install superpom + helpers repos.
If you mirror it, please be cool with my 6 years old machine and proxy to your own Nexus (or even better, clone and push it to m2).


Then, enable this Maven configuration in your Karaf server <Karaf_Home>/etc/org.ops4j.pax.url.mvn.cfg (reference to settings.xml + osgiliath nexus: http://nexus.osgiliath.net/nexus/content/groups/public)

Then, you have to add a config file in this same directory: jmsconfig.cfg containing entry:
jmstransportconnector.uri = tcp://0.0.0.0:61616


Finally, you can add features in the karaf console: features:addurl mvn:net.osgiliath.framework/karaf-features-full/0.0.1/xml/features 

h2. Architecture

You can clone the git project at https://github.com/Tcharl/karaf.features.git

It's composed of a parent pom and multiples modules (features):

* derby for the database
* jpa for persistence
* validation for hibernate validation (with an osgi service)
* security for spring security
* messaging for jms and websocket (jms connection is exported)
* full for all of these!