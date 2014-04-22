package helper.exam;

/*
 * #%L
 * net.osgiliath.hello.business.impl
 * %%
 * Copyright (C) 2013 Osgiliath
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

import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import java.io.File;
import java.net.MalformedURLException;

import javax.inject.Inject;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General integration test declaration
 * 
 * @author charliemordant
 * 
 */
public abstract class AbstractKarafPaxExamConfiguration {
	@Inject
	protected BundleContext bundleContext;
	protected static final String COVERAGE_COMMAND = "coverage.command";
	protected static final String USER_SETTINGS_REFERENCE = "user-settings";
	protected static final String GLOBAL_SETTINGS_REFERENCE = "global-settings";

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractKarafPaxExamConfiguration.class);

	// the JVM option to set to enable remote debugging
	@SuppressWarnings("UnusedDeclaration")
	protected static final String DEBUG_VM_OPTION = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=31313";

	// the actual JVM option set, extensions may implement a static
	// initializer overwriting this value to have the configuration()
	// method include it when starting the OSGi framework JVM
	protected static String paxRunnerVmOption = null;
	static {
		// uncomment to enable debugging of this test class
		// paxRunnerVmOption = DEBUG_VM_OPTION;

	}

	@Configuration
	public Option[] config() throws MalformedURLException {
		Option[] base = options(
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
				keepRuntimeFolder(), cleanCaches(), loggingLevel(),
				junitBundles(), addCodeCoverageOption(), addExtraOptions(),
				 addMavenSettingsOptions(), featureToTest());
		OptionUtils.combine(base, addJVMOptions());
		final Option vmOption = (paxRunnerVmOption != null) ? CoreOptions
				.vmOption(paxRunnerVmOption) : null;
		return OptionUtils.combine(base, vmOption);
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
		return options(CoreOptions
		.vmOption(maxHeap), CoreOptions
		.vmOption(minHeap), CoreOptions
		.vmOption(maxPerm));
	}

	private Option addCodeCoverageOption() {
		String coverageCommand = System.getProperty(COVERAGE_COMMAND);
		if (coverageCommand != null && !coverageCommand.isEmpty()) {
			LOG.info("covering code with command " + coverageCommand);
			return CoreOptions.vmOption(coverageCommand);
		}
		return new DefaultCompositeOption();
	}

	protected abstract Option featureToTest();

	protected Option addExtraOptions() {
		return new DefaultCompositeOption();
	}
	protected Option loggingLevel() {
		return logLevel(LogLevel.INFO);
	}

}
