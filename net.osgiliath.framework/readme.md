#Osgiliath framework

It's composed in two parts: helpers and features.

## Features
A feature enables a capability to your modules.
For example if you want your module have persistence capability, just reference the jpa feature on your module pom.xml and as a feature dependency on your module feature.xml part, you'll be then able to code and deploy your persistence aware module.

## Helpers
They are wrapped or handcoded module to be used by Osgiliath framework. For example there are no any JaxRS Cdi component in the world except in the Osgiliath platform.
For most of them, these helpers should go to their respective opensource projects, so if you're a CXF, ActiveMQ, Deltaspike commiter, feel free to merge it!
