# Karaf Features


## What is it?

This is bunch of Karaf capabilities/features to enable a fully-fledged ESB project!

## How to use?
First of all, if your looking for samples, go to the itests module.

If you want to have Osgiliath features in a Karaf 3 installation, type in the Karaf console: 
feature:repo-add mvn:net.osgiliath.framework/karaf-features-full/[Actual Osgiliath Version]/xml/features 

Then install the feature you want (i.e. feature:install osgiliath-full)

Features summary:

* CDI: enables Context-dependency-injection in your project, i.e. declaring web-services endpoint via @Endpoint annotation, injecting beans with @Inject, declaring them via @Named...
* Derby: Derby file database on the fly creation: usefull for developping ;)
* Full: all the other features
* Functional: enables functional programming and many helpers for business programming (Guava, apache commons...).
* JaxRS: RestFul web services with apache CXF. Model marshalling/unmarshalling with Json and JaxB
* JPA: database persistence and transactions for your pojos!
* Messaging: async messaging and routes: JMS, ActiveMQ, Camel-*
* Security: Spring-security support.
* Standard: rewrapped native Karaf or other Apache framework features: for example resolves a dependency conflict for joda time when we installed the camel-xstream and cxf-jaxb native features.
* Validation: enable JSR339 (bean validation 1.1) and CDI-Validation on your project (annotating your Pojos with @NotNull, validating with @Valid on a method...)
* Itests: all the features integration tests, can be consulted as sample to succeed in implementing some piece of code.
* Routes: all the apache camel modules that have nothing to do in the messaging one.
