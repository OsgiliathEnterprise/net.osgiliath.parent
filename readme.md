h1. Superpom

h2. What's this?

This is the superpom of all osgiliath projects, it's composed of multiple maven modules containing the different standard configurations, libraries...

h2. How to use?

h3. Prerequisite

You must have maven installed on your PC.

h3. Usage one: without maven configuration

You just have either to download the project (git pull http://subversion.osgiliath.net/git-private/superpom.git) and run mvn install on the project root

h3. Usage two: as a mirror in maven settings

You just have to configure the osgiliath nexus (http://nexus.osgiliath.net/nexus) as a mirror on your <MAVEN_HOME>/conf/settings.xml (see: http://maven.apache.org/settings.html#Mirrors)

h2. Architecture

Here is a little description of the different modules:
* osgiliath.repositories: contains the different repositories to grab the different dependencies
* osgiliath.reporting: contains the reporting configuration (checkstyle, pmd, sonar, ...)
* osgiliath.plugins: contains the global plugins configuration (weaving, creating manifests...)
* osgiliath.dependency-management: the dependency management of the projects and their versions
* osgiliath.projects.definition: the main configuration of osgiliath projects (where is sonar, jenkins...)