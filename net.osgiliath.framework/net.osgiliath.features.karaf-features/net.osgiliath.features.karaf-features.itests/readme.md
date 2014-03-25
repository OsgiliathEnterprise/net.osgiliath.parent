# Integration tests

Here are all the tests done on the Osgiliath features.

## Summary

* CDI: test that the bean injection via @Inject works
* features: the integration tests feature to launch integration tests :p
* Jaxrs-cdi: test endpoint creation via annotations
* JaxRS-Web-cdi: test endpoint creation via servlet3/cdi: doesn't work and is a ticket for pax-web
* JaxRS-Web: declares a web service via servlet3
* JPA-CDI: test persistence with deltaspike-data and deltaspike-jpa: doesn't work for the moment
* JPA-database creates a database for JPA tests
* JPA: test persistence with Spring-data-jpa and aries blueprint
* Messaging-CDI: test messaging with CDI: only @Consume on methods doesn't work, consuming via routes is OK
* Messaging: Messaging using camel-blueprint
* Security: Spring-security tests
* Validation: tests for bean validation (@Valid, @RegExp...).
