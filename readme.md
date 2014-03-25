#Osgiliath Enterprise framework

## Purpose

Osgiliath EF aims to provides a way to develop Web/backend/enterprise applications in a clean, modular way

## Technologies

Osgiliath EF uses many existing first class frameworks, like AngularJS, twitter Bootstrap, Apache Karaf, Apache Camel, CXF, ActiveMQ, JPA, JTA...

## What could I find

### Features
You'll find here a bunch of Karaf features allowing you to enable persistence on your project, Web-Servicing, Async messaging, Transactions, validation, Security, dependency injection...

### Archetypes
Osgiliath come with Maven archetypes generating your project skeleton, adding capabilities to it...
There are four archetypes: 
* Parent, that describes an entire application
* Routes, that enable communication between your different web services/parent modules...
* Business that embeds validation, web services, async ones, transactions, functional programming style...
* Model that permits you to save your elements in a/some relational databases easily

###Samples
There are two way of programming with Osgiliath framework: using blueprint or using CDI.
Blueprint configuration as an xml based syntax and is a proven OSGI technology while CDI offers configuration based on annotation (more conscise but may be a little bit less featured), so choose your weapons!

### Superpom
The entire Osgiliath EF uses these Maven configuration to configure dependency versions, plugin execution... So feel free to use it in your own framework ;).

# Where to start?

You can first take a look at the documentation: https://blog.osgiliath.net/?page_id=224.
Then, you can directly use the archectypes as they're published in Maven central (mvn archetype:generate ...).
Or look at the examples or even the features to see how to code something.
Readme.md will describe what's doing a module all along your way through this repo, so don't hesitate to crawl into!


# License

Osgiliath EF is fully Apache 2.0 License so feel free to use, modify, sell... Just don't forget to cite me or at least send me a mail...

Enjoy Osgiliath coding!
