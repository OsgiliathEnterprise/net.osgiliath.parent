package net.osgiliath.helpers.exam;

/*
 * #%L
 * net.osgiliath.helpers.exam
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
@Slf4j
public abstract class AbstractPaxExamKarafConfiguration {
	/**
	 * according Java property to set is jcoverage.command 
	 */
    protected static final String COVERAGE_COMMAND = "coverage.command";
    /**
	 * according Java property to set is maven.settings
	 */
    protected static final String CONFIGURED_MAVEN_USER_SETTINGS = "maven.user.settings";
    /**
	 * according Java property to set is org.apache.maven.user-settings
	 */
    protected static final String DEFAULT_MAVEN_USER_SETTINGS = "maven.user.settings.default";
    /**
	 * according Java property to set is org.apache.maven.global-settings
	 */
    protected static final String DEFAULT_MAVEN_GLOBAL_SETTINGS = "maven.global.settings.default";
    /**
   	 * according Java property to set is project.groupId
   	 */
    protected static final String MODULE_GROUP_ID = "module.groupId";
    /**
   	 * according Java property to set is project.parent.artifactId
   	 */
    protected static final String MODULE_PARENT_ARTIFACT_ID = "module.parent.artifactId";
    /**
     * According property is maven.repos
     */
    protected static final String MAVEN_REPOS_URLS = "maven.repos.urls";

    @SuppressWarnings("UnusedDeclaration")
    protected static final String DEBUG_VM_OPTION = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=31313";
    protected static String paxRunnerVmOption = null;
    

    public Option[] createConfig() {
	Option[] base = options(
		cleanCaches(),
		karafDistributionConfiguration()
			.frameworkUrl(
				maven().groupId("org.apache.karaf")
					.artifactId("apache-karaf").type("zip")
					.versionAsInProject())
			.name("Apache Karaf")
			.karafVersion(
				MavenUtils.getArtifactVersion(
					"org.apache.karaf", "apache-karaf"))
			.unpackDirectory(new File("target/exam/unpack/")),
		keepRuntimeFolder(), cleanCaches(), junitBundles(),
		addCodeCoverageOption(), addMavenSettingsOptions(), mavenReposURLOptions(),
		loggingLevel(), addExtraOptions(), featureToTest());
	Option[] baseAndJVM = OptionUtils.combine(base, addJVMOptions());
	final Option vmOption = (paxRunnerVmOption != null) ? CoreOptions
		.vmOption(paxRunnerVmOption) : null;
	return OptionUtils.combine(baseAndJVM, vmOption);
    }

    private Option mavenReposURLOptions() {
    	if (System.getProperty(MAVEN_REPOS_URLS) != null) {
    	    log.info("replacing repositories urls by: " + System.getProperty(MAVEN_REPOS_URLS));
    	    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
    			    "org.ops4j.pax.url.mvn.repositories",
    			    System.getProperty(MAVEN_REPOS_URLS));
    	}
    	return new DefaultCompositeOption();
	}

	private Option addMavenSettingsOptions() {
		if (System.getProperty(CONFIGURED_MAVEN_USER_SETTINGS) != null) {
		    log.info("adding user reference settings "
			    + System.getProperty(CONFIGURED_MAVEN_USER_SETTINGS));
		    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
			    "org.ops4j.pax.url.mvn.settings",
			    System.getProperty(DEFAULT_MAVEN_USER_SETTINGS));
		} else if (System.getProperty(DEFAULT_MAVEN_USER_SETTINGS) != null) {
	    log.info("adding user reference settings "
		    + System.getProperty(DEFAULT_MAVEN_USER_SETTINGS));
	    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
		    "org.ops4j.pax.url.mvn.settings",
		    System.getProperty(DEFAULT_MAVEN_USER_SETTINGS));
	}
	if (System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS) != null) {
	    log.info("adding global reference settings "
		    + System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS));
	    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
		    "org.ops4j.pax.url.mvn.settings",
		    System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS));
	}
	return new DefaultCompositeOption();

    }

    private Option[] addJVMOptions() {

	String maxHeap = "-Xmx512m";
	String minHeap = "-Xms128m";
	String maxPerm = "-XX:MaxPermSize=256m";
	return options(CoreOptions.vmOption(maxHeap),
		CoreOptions.vmOption(minHeap), CoreOptions.vmOption(maxPerm));
    }

    private Option addCodeCoverageOption() {
	String coverageCommand = System.getProperty(COVERAGE_COMMAND);
	if (coverageCommand != null && !coverageCommand.isEmpty()) {
	    log.info("covering code with command " + coverageCommand);
	    return CoreOptions.vmOption(coverageCommand);
	}
	return new DefaultCompositeOption();
    }

    protected Option loggingLevel() {
	return logLevel(LogLevel.INFO);
    }

    protected Option addExtraOptions() {
	return new DefaultCompositeOption();
    }

    protected abstract Option featureToTest();
}
