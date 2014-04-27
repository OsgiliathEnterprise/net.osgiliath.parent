Java modules (all except ui): 

Prerequisite: 

Jdk 7, Maven (3.x)

Usage:

If you're behind a proxy, add this property (in an active profile) in your settings.xml : <mavenSettings.location>/usr/share/apache-maven/conf/settings.xml</mavenSettings.location>

Run mvn clean install on the root project!

IDE recommandation:

* Eclipse 4.3 with the following plugins:
* * Lombok installation: mandatory for IDE compilation
* * M2e (compilation)
* * Spring-IDE (for blueprint file editing)
* * BND

Development:
Starting from the archetype, you have a simple helloworld made with this project, don't forget to remove every reference once you've understood the stack

Follow the different TODO keywords, starting from this module order:
<project>.database
<project>.model.jpa
<project>.business.spi
<project>.business.impl.cdi/blueprint
<project>.features
<project>.routes.blueprint/cdi
<project>.ui

UI module:
Prerequisite:

npm
sudo npm -g install grunt-cli
sudo npm -g install phantomjs
sudo npm -g install bower
gem install sass
gem install compass


Then mvn clean install on the project

Deployement:

Prerequisite: Karaf 3.0.1

Usage: run 'feature:repo-add mvn:<project.parent.groupId>/<project.parent.artifactId>/<version>/xml/feature', then 'feature:install <project.parent.artifactId>.cdi/blueprint.full'