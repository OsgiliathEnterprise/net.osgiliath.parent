# Superpom

## What's this?

This is the superpom of all Osgiliath pom projects, it's composed of multiple maven modules containing the different standard configurations, libraries...

## How to use?

Just reference one of these poms at a parent pom of your project. i.e.:
```xml
	<parent>
<artifactId>net.osgiliath.pom.dependency-management</artifactId>
<groupId>net.osgiliath</groupId>
<version>[Osgiliath_Version_To_Set]</version>
<relativePath>..</relativePath>
</parent>
```

## Prerequisite

You must have Maven installed on your PC (and maybe a JDK for some plugins execution).

### Usage one: For Osgiliath contribution

Just mvn the parent project

### Usage one for your project

Reference one of the superpom as a parent of your project (dependending your degree of Osgiliath EF compliance) then build it

## Architecture

Here is a little description of the different sub poms modules:
* osgiliath.repositories: contains the different repositories to grab the different dependencies (empty to comply sonatype Central distribution policy)
* osgiliath.reporting: contains the reporting configuration (checkstyle, pmd, sonar, ...)
* osgiliath.plugins: contains the global plugins configuration (weaving, creating manifests...)
* osgiliath.dependency-management: the dependency management of the projects and their versions
* osgiliath.projects.definition: the main configuration of Osgiliath private projects (where is sonar, jenkins...)
