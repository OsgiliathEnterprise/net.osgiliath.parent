#Osgiliath Enterprise framework

[![Join the chat at https://gitter.im/OsgiliathEnterprise/net.osgiliath.parent](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/OsgiliathEnterprise/net.osgiliath.parent)

[![Build Status](https://travis-ci.org/OsgiliathEnterprise/net.osgiliath.parent.svg?branch=master)](https://travis-ci.org/OsgiliathEnterprise/net.osgiliath.parent) (sometimes fail due to timeout)
[![Codeship Status](https://codeship.io/projects/642d0130-f74b-0131-9906-6abae1c59881/status?branch=master)](https://codeship.com/projects/28581)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.osgiliath/net.osgiliath.parent/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/net.osgiliath.poms/net.osgiliath.parent)
[![Js dependencies status](https://david-dm.org/OsgiliathEnterprise/net.osgiliath.parent.svg?path=net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.ui)](https://david-dm.org/OsgiliathEnterprise/net.osgiliath.parent.json?path=net.osgiliath.samples/net.osgiliath.sample.webapp/net.osgiliath.sample.webapp.ui)
## Purpose

Osgiliath EF aims to provides a way to develop Web/backend/enterprise applications in a clean, modular way

## Technologies

Osgiliath EF uses many existing first class frameworks, like AngularJS, Twitter Bootstrap, Apache Karaf, Apache Camel, CXF, ActiveMQ, JPA, JTA...

## What could I find

### Features
You'll find here a bunch of Karaf features allowing you to enable persistence on your project, Web-Servicing, Async messaging, Transactions, Validation, Security, Dependency Injection...
Look at their itegration test counter part on how to use it

### Archetypes
Osgiliath come with Maven archetypes generating your project skeleton, adding capabilities to it...
There are four archetypes: 
* Parent, that describes an entire application
* Routes, that enable communication between your different web services/parent modules...
* Business that embeds validation, web services, async ones, transactions, functional programming style...
* Model that permits you to save your elements in a/some relational databases easily

###Samples
There are two way of programming with Osgiliath framework: using blueprint or using CDI.
Blueprint configuration has an xml based syntax and is a proven OSGI technology while CDI offers configuration based on annotation (more concise but may be a little bit less featured), so choose your weapons!

### Superpom
The entire Osgiliath EF uses these Maven configuration to configure dependency versions, plugin execution... So feel free to use it in your own framework ;).

# Where to start?

You can first take a look at the documentation: http://osgiliathenterprise.github.io/net.osgiliath.parent/.
Then, you can directly use the archectypes as they're published in Maven central (mvn archetype:generate ...).
Or look at the examples or even the features integration tests to see how to code something.
Readme.md will describe what's doing a module all along your way through this repo, so don't hesitate to crawl into!

# Running on Karaf
Be sure to set JAVA_MAX_MEM and JAVA_MAX_PERM_MEM to higher size (i.e. 1024M) before running an Osgiliath application on Karaf


# License

Osgiliath EF is fully Apache 2.0 License so feel free to use, modify, sell... Just don't forget to cite me or at least send me a mail...


# Contributor
In order to contribute: Clone the repo, install Java, Maven, Npm, Grunt, Bower, PhantomJS and Compass.
Then, add a property mavenSettings.location property pointing to your settings file in this settings file (in an active pofile).
Finally run mvn clean install on the root...
On Mac, add a java.home property in your Maven settings.xml pointing to your JDK installation (mandatory for lombok).
If you use Eclipse, also install the latest Scala IDE in order to compile (http://download.scala-ide.org/sdk/lithium/e44/scala211/dev/site)
We'll accept most of contributions in a very short time so don't be shy!

# Releasing
In order to release Osgiliath on m2 central, you've got to execute these two command:

```
    export MAVEN_OPTS="-Xmx2048M -XX:MaxPermSize=1G" (in your .profile)
    mvn release:prepare -Psonatype-oss-release
    mvn release:perform -Dgoals=deploy
```


Enjoy Osgiliath coding!
