# Karaf Features camel


## What is it?

These are Enterprise integration patterns features for the osgiliath framework

## How to use?
First of all, if your looking for samples, go to the itests module.

If you want to have Osgiliath EIP features in a Karaf 3 installation, type in the Karaf console: 
feature:repo-add mvn:net.osgiliath.framework/net.osgiliath.feature.camel/[Actual Osgiliath Version]/xml/features 


Features summary:

* camel jms: java messaging service for camel
* camel http: java http service for camel
* camel jackson/xml: xml/json transformers
* camel websocket: Websocket messaging