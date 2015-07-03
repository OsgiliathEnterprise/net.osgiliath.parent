package net.osgiliath.security.itests;

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

import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import javax.inject.Inject;
import net.osgiliath.helper.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.security.MUser;
import net.osgiliath.security.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Security integration test.
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITsecurity extends AbstractPaxExamKarafConfiguration {
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ITsecurity.class);
  /**
   * OSGI Bundle context.
   */
  @Inject
  private transient BundleContext bundleContext;
  /**
   * Exported service via blueprint.xml.
   */
  @Inject
  @Filter(timeout = 40000)
  private transient SecurityService securityService;

  /**
   * probe adding the abstract test class.
   * 
   * @param builder
   *          the pax probe builder
   * @return the provisionned probe.
   */
  @ProbeBuilder
  public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
    builder.addTest(AbstractPaxExamKarafConfiguration.class);
    builder.setHeader(Constants.EXPORT_PACKAGE, "security.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
    return builder;
  }

  /**
   * User authentication test.
   * 
   */
  @Test
  public void testAuthenticate() {
    if (LOG.isDebugEnabled()) {
      LOG.trace("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        LOG.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());
      }
    }
    final MUser user = new MUser();
    user.setPseudo("toto");
    user.setPassword("myPassword");
    this.securityService.onSubscription(user);
    assertTrue(this.securityService.authenticate("toto", "myPassword"));

  }

  /**
   * Karaf feature to test.
   * 
   * @return the feature
   */
  @Override
  protected Option featureToTest() {
    return features(
        maven()
            .artifactId("net.osgiliath.feature.itest.feature")
            .groupId("net.osgiliath.framework").type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-security");
  }

  static {
    // uncomment to enable debugging of this test class
    // paxRunnerVmOption = DEBUG_VM_OPTION; //NOSONAR

  }

  /**
   * Pax exam configuration creation.
   * 
   * @return the provisionned configuration
   */
  @Configuration
  public Option[] config() {
    return createConfig();
  }
}
