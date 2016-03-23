Java modules (all except ui): 

Prerequisite: 

Jdk 7, Maven (3.x)

Usage:

If you're behind a proxy, add this property (in an active profile) in your settings.xml : <mavenSettings.location>/usr/share/apache-maven/conf/settings.xml</mavenSettings.location>

Run mvn clean install on the root project!

IDE recommandation:

* Eclipse 4.3 with the following plugins:
* Lombok installation: mandatory for IDE compilation
* M2e (compilation)
* Spring-IDE (optional: for blueprint file editing)
* BND

Development:
Starting from the archetype, you have a simple implemented helloworld, don't forget to remove every reference once you've understood the stack

Follow the different TODO keywords, starting from this module order:

    <project>.model
    <project>.business
    <project>.ui
    <project>.features

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

Prerequisite: Karaf 4.0.4

Usage: run 'feature:repo-add mvn:${project.groupId}/${project.artifactId}.features/${project.version}/xml/feature', then 'feature:install ${project.artifactId}.ui.feature'
