package net.osgiliath.messaging.cdi.repository.impl.itests;

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

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import javax.inject.Inject;
import net.osgiliath.helper.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.messaging.cdi.HelloEntity;
import net.osgiliath.messaging.cdi.Hellos;
import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.karaf.features.BootFinished;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JMS messaging with CDI integration test.
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJMS extends AbstractPaxExamKarafConfiguration {
  private static final Logger LOG = LoggerFactory
      .getLogger(ITHelloServiceJMS.class);
  /**
   * OSGI bundle context.
   */
  @Inject
  private transient BundleContext bundleContext;
  /**
   * Boofinished event.
   */
  @Inject
  @Filter(timeout = 40000)
  private transient BootFinished bootFinished;
  /**
   * Camel JMS component.
   */
  @Inject
  @Filter(value = "(component-type=jms)")
  private transient Component jmsComponent;

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
    builder.setHeader(Constants.EXPORT_PACKAGE,
        "net.osgiliath.messaging.repository.impl.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");

    return builder;
  }

  /**
   * JMS call.
   * 
   */
  @Test
  public void testSayHello() {
    if (LOG.isDebugEnabled()) {
      LOG.trace("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        LOG.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());
      }
      LOG.trace("*********  End list ****************");
    }
    final HelloEntity entity = new HelloEntity();
    entity.setHelloMessage("Charlie");
    LOG.info("Sending Body");
    final ProducerTemplate template = this.jmsComponent.getCamelContext()
        .createProducerTemplate();
    template.sendBody("jms:queue:helloServiceQueueIn", entity);
    final ConsumerTemplate consumer = template.getCamelContext()
        .createConsumerTemplate();
    LOG.info("Waiting answer");
    final Hellos hellos = consumer.receiveBody(
        "jms:queue:helloServiceQueueOut", 4000, Hellos.class);
    LOG.warn("Hellos: " + hellos);
    assertEquals(1, hellos.getEntities().size());

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
        "osgiliath-itests-messaging-cdi");
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

  @Override
  protected Option loggingLevel() {

    return logLevel(LogLevel.INFO);
  }

}
