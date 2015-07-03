package net.osgiliath.cdi.properties.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.cdi.properties
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

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import javax.inject.Inject;
import net.osgiliath.cdi.properties.api.IPropertyConsumer;
import net.osgiliath.helper.exam.AbstractPaxExamKarafConfiguration;
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
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Test Deltaspike property injection.
 * @author charliemordant
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITPropertyInjection extends AbstractPaxExamKarafConfiguration {
  private static final Logger LOG = LoggerFactory
      .getLogger(ITPropertyInjection.class);
  /**
   *  OSGI exported deltaspike consumer.
   */
  @Inject
  @Filter(timeout = 40000)
  private transient IPropertyConsumer consumer;
  /**
   * probe adding the abstract test class.
   * @param builder the pax probe builder
   * @return the provisionned probe.
   */
  @ProbeBuilder
  public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.addTest(AbstractPaxExamKarafConfiguration.class);
    builder.setHeader(Constants.EXPORT_PACKAGE,
        "net.osgiliath.cdi.properties.impl");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
    return builder;
  }
  /**
   * Test an injected property.
   */
  @Test
  public void testSayHello() {
    LOG.info("consumer should be injected");
    assertEquals(this.consumer.getInjectedProperty(), "hello");
  }
  /**
   * Karaf feature to test.
   * @return the feature
   */
  @Override
  protected Option featureToTest() {
    return features(
        maven()
            .artifactId("net.osgiliath.feature.itest.feature")
            .groupId(System.getProperty(MODULE_GROUP_ID)).type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-cdi-properties");
  }

  static {
    // uncomment to enable debugging of this test class
    // paxRunnerVmOption = DEBUG_VM_OPTION; //NOSONAR

  }
  /**
   * Pax exam configuration creation.
   * @return the provisionned configuration
   */
  @Configuration
  public Option[] config() {
    return createConfig();
  }

}
