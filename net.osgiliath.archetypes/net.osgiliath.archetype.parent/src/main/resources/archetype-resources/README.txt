#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
Java modules (all except ui): 

Prerequisite: 

Jdk, Maven (3.x)

Usage:

add this property (in an active profile) in your settings.xml : <mavenSettings.location>/usr/share/apache-maven/conf/settings.xml</mavenSettings.location>

Run mvn clean install on the root project!

IDE recommandation:

* Eclipse 4.3 with the following plugins:
* * Lombok installation: mandatory for IDE compilation
* * M2e (compilation)
* * Spring-IDE (for blueprint file editing)
* * Virgo tooling (template.mf edition)

Development:
Starting from the archetype, you have a simple helloworld made with this project, don't forget to remove every reference once you've understood the stack

Follow the different TODO keywords, starting from this module order:
<project>.database
<project>.model.jpa
<project>.business.spi
<project>.business.impl
<project>.features
<project>.routes
<project>.ui

UI module:
Prerequisite:

npm, bower (npm -g install bower), grunt (npm -g install grunt-cli)

navigate to the 'yo' folder and run 'npm -g install --save-dev', then 'bower install --save-dev'

Then mvn clean install on the project

Deployement:

Prerequisite: Karaf 3.0.0

Usage: run 'feature:repo-add mvn:<project.parent.groupId>/<project.parent.artifactId>/<version>/xml/feature', then 'feature:install <project.parent.artifactId>.full'