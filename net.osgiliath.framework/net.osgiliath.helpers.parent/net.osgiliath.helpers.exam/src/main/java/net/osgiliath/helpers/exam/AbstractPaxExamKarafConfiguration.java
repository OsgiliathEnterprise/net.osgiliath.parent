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
/**
 * General Pax exam configuration for Karaf.
 * @author charliemordant
 *
 */
@Slf4j
public abstract class AbstractPaxExamKarafConfiguration {
  /**
   * Pax Maven URL karaf property.
   */
  private static final String PAX_URL_MVN_SETTINGS_PROPERTY = "org.ops4j.pax.url.mvn.settings";
  /**
   * Pax maven url handler file location.
   */
  private static final String PAX_URL_MVN_CFG = "etc/org.ops4j.pax.url.mvn.cfg";
  /**
   * according Java property to set is jcoverage.command.
   */
  protected static final String COVERAGE_COMMAND = "jcoverage.command";
  /**
   * according Java property to set is maven.user.settings.
   */
  protected static final String CONFIGURED_MAVEN_USER_SETTINGS = "maven.user.settings";
  /**
   * according Java property to set is org.apache.maven.user-settings.
   */
  protected static final String DEFAULT_MAVEN_USER_SETTINGS = "maven.user.settings.default";
  /**
   * according Java property to set is org.apache.maven.global-settings.
   */
  protected static final String DEFAULT_MAVEN_GLOBAL_SETTINGS = "maven.global.settings.default";
  /**
   * according Java property to set is project.groupId.
   */
  protected static final String MODULE_GROUP_ID = "project.groupId";
  /**
   * according Java property to set is project.parent.artifactId.
   */
  protected static final String MODULE_PARENT_ARTIFACT_ID = "project.parent.artifactId";
  /**
   * according Java property to set is project.artifactId.
   */
  protected static final String MODULE_ARTIFACT_ID = "project.artifactId";
  /**
   * According property is maven.repos.urls.
   */
  protected static final String MAVEN_REPOS_URLS = "maven.repos.urls";

  @SuppressWarnings("UnusedDeclaration")
  /**
   * Default debug options to set if you want to debug the code
   */
  protected static final String DEBUG_VM_OPTION = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=31313";
  /**
   * Max heap size.
   */
  private static final String MAX_HEAP = "-Xmx512m";
  /**
   * Min heap size.
   */
  private static final String MIN_HEAP = "-Xms128m";
  /**
   * Max perm size.
   */
  private static final String MAX_PERM = "-XX:MaxPermSize=256m";
  
  /**
   * Pax VM options.
   */
  protected static String paxRunnerVmOption;
  /**
   * Creates default pax exam configuration for Karaf.
   * @return the default configuration
   */
  public Option[] createConfig() {
    final Option[] base = options(
        cleanCaches(),
        karafDistributionConfiguration()
            .frameworkUrl(
                maven().groupId("org.apache.karaf").artifactId("apache-karaf")
                    .type("zip").versionAsInProject())
            .name("Apache Karaf")
            .karafVersion(
                MavenUtils.getArtifactVersion("org.apache.karaf",
                    "apache-karaf"))
            .unpackDirectory(new File("target/exam/unpack/")),
        keepRuntimeFolder(), junitBundles(),
        addCodeCoverageOption(), addMavenSettingsOptions(),
        mavenReposURLOptions(), loggingLevel(), addExtraOptions(),
        featureToTest());
    final Option[] baseAndJVM = OptionUtils.combine(base, addJVMOptions());
    final Option vmOption = (paxRunnerVmOption != null) ? CoreOptions
        .vmOption(paxRunnerVmOption) : null;
    return OptionUtils.combine(baseAndJVM, vmOption);
  }
  /**
   * Sets Maven repository urls.
   * @return maven overriden URLS
   */
  private Option mavenReposURLOptions() {
    if (System.getProperty(MAVEN_REPOS_URLS) != null) {
      log.info("replacing repositories urls by: "
          + System.getProperty(MAVEN_REPOS_URLS));
      return editConfigurationFilePut(PAX_URL_MVN_CFG,
          "org.ops4j.pax.url.mvn.repositories",
          System.getProperty(MAVEN_REPOS_URLS));
    }
    return new DefaultCompositeOption();
  }
  /**
   * Overriden maven settings.
   * @return maven settings files options
   */
  private Option addMavenSettingsOptions() {
    if (System.getProperty(CONFIGURED_MAVEN_USER_SETTINGS) != null) {
      log.info("adding user reference settings "
          + System.getProperty(CONFIGURED_MAVEN_USER_SETTINGS));
      return editConfigurationFilePut(PAX_URL_MVN_CFG,
          PAX_URL_MVN_SETTINGS_PROPERTY,
          System.getProperty(CONFIGURED_MAVEN_USER_SETTINGS));
    }
    else
      if (System.getProperty(DEFAULT_MAVEN_USER_SETTINGS) != null) {
      log.info("adding user reference settings "
          + System.getProperty(DEFAULT_MAVEN_USER_SETTINGS));
      return editConfigurationFilePut(PAX_URL_MVN_CFG,
          PAX_URL_MVN_SETTINGS_PROPERTY,
          System.getProperty(DEFAULT_MAVEN_USER_SETTINGS));
    }
    if (System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS) != null) {
      log.info("adding global reference settings "
          + System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS));
      return editConfigurationFilePut(PAX_URL_MVN_CFG,
          PAX_URL_MVN_SETTINGS_PROPERTY,
          System.getProperty(DEFAULT_MAVEN_GLOBAL_SETTINGS));
    }
    return new DefaultCompositeOption();

  }
  /**
   * Increses default heap size.
   * @return Heap size Options
   */
  private Option[] addJVMOptions() {

    
    return options(CoreOptions.vmOption(MAX_HEAP),
        CoreOptions.vmOption(MIN_HEAP), CoreOptions.vmOption(MAX_PERM));
  }
  /**
   * Adds code coverage options.
   * @return code coverage options
   */
  private Option addCodeCoverageOption() {
    final String coverageCommand = System.getProperty(COVERAGE_COMMAND);
    if (coverageCommand != null && !coverageCommand.isEmpty()) {
      log.info("covering code with command " + coverageCommand);
      return CoreOptions.vmOption(coverageCommand);
    }
    return new DefaultCompositeOption();
  }
  /**
   * Default logging level.
   * @return Log level options
   */
  protected Option loggingLevel() {
    return logLevel(LogLevel.INFO);
  }
  /**
   * Additionnal options to override.
   * @return return additional options
   */
  protected Option addExtraOptions() {
    return new DefaultCompositeOption();
  }
  /**
   * Karaf feature to test.
   * @return karaf feature Option
   */
  protected abstract Option featureToTest();
}
