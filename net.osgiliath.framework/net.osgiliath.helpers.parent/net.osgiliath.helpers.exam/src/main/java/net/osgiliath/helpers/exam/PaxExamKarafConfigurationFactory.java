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

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PaxExamKarafConfigurationFactory {
    protected static final String COVERAGE_COMMAND = "coverage.command";
    protected static final String USER_SETTINGS_REFERENCE = "user-settings";
    protected static final String GLOBAL_SETTINGS_REFERENCE = "global-settings";
    protected static final String BUNDLE_GROUP_ID = "bundle.groupId";
    protected static final String BUNDLE_ARTIFACT_ID = "bundle.parent.artifactId";

    @SuppressWarnings("UnusedDeclaration")
    protected static final String DEBUG_VM_OPTION = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=31313";
    protected static String paxRunnerVmOption = null;
    private static final Logger LOG = LoggerFactory
	    .getLogger(PaxExamKarafConfigurationFactory.class);

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
		addCodeCoverageOption(), addMavenSettingsOptions(),
		loggingLevel(), addExtraOptions(), featureToTest());
	Option[] baseAndJVM = OptionUtils.combine(base, addJVMOptions());
	final Option vmOption = (paxRunnerVmOption != null) ? CoreOptions
		.vmOption(paxRunnerVmOption) : null;
	return OptionUtils.combine(baseAndJVM, vmOption);
    }

    private Option addMavenSettingsOptions() {
	if (System.getProperty(USER_SETTINGS_REFERENCE) != null) {
	    LOG.info("adding user reference settings "
		    + System.getProperty(USER_SETTINGS_REFERENCE));
	    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
		    "org.ops4j.pax.url.mvn.settings",
		    System.getProperty(USER_SETTINGS_REFERENCE));
	}
	if (System.getProperty(GLOBAL_SETTINGS_REFERENCE) != null) {
	    LOG.info("adding global reference settings "
		    + System.getProperty(GLOBAL_SETTINGS_REFERENCE));
	    return editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
		    "org.ops4j.pax.url.mvn.settings",
		    System.getProperty(GLOBAL_SETTINGS_REFERENCE));
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
	    LOG.info("covering code with command " + coverageCommand);
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
