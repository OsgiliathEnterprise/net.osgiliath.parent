# UI

User interface module

## Files

* Bower.json and package.json: third party dependencies for the UI
* Grunfile: build file
* Karma* : tests configuration files
* Osgi.bnd : web endpoint declaration

## Tests

There are three kind of tests:

src/it/java/** : Selenium tests: full application tests (with backend started) 
* [Server and UI full karaf feature](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/it/java/helper/exam/StandaloneKarafPaxExamConfiguration.java)
* [Sample Selenium test](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/it/java/net/osgiliath/messaging/repository/impl/itests/ITHelloWebUITest.java)

src/test/javascript/e2e : end-to-end karma test for testing UI and web pages without java backend
* [Tested files references](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/karma-e2e.conf.js)
* [Sample e2e test](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/test/javascript/e2e/indexTest.js)

src/test/javascript/spec/*** : Controllers unit tests

* [Tested files references](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/karma.conf.js)
* [Sample controller test with mocked websocket service](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/test/javascript/spec/controllers/hello.js)

## UI files

* [Main index file referencing app](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/main/javascript/index.html)
* [Main controller](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/main/javascript/scripts/directives/hello.js)
* [Main page](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/main/javascript/views/main.html)
* [Sample directive template](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/main/javascript/views/templates/hello.html)
* [Controller making webSocket calls](https://github.com/OsgiliathEnterprise/net.osgiliath.parent/blob/master/net.osgiliath.samples/net.osgiliath.hello/net.osgiliath.hello.ui/src/main/javascript/scripts/controllers/hello.js)
