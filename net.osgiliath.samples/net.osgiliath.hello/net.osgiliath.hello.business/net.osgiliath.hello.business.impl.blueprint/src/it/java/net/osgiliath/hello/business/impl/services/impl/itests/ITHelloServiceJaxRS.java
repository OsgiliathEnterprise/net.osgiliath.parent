package net.osgiliath.hello.business.impl.services.impl.itests;

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

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.helper.exam.AbstractPaxExamKarafConfiguration;
import org.apache.karaf.features.BootFinished;
import org.junit.Before;
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
import org.springframework.jms.core.JmsOperations;

/**
 * Tests a web service call.
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJaxRS extends AbstractPaxExamKarafConfiguration {
  /**
   * The logger.
   */
  protected static final Logger LOG = LoggerFactory
      .getLogger(ITHelloServiceJaxRS.class);
  /**
   * OSGI bundle context.
   */
  @Inject
  private transient BundleContext bundleContext;
  /**
   * Bootfinished event.
   */
  @Inject
  @Filter(timeout = 40000)
  private BootFinished bootFinished;

  /**
   * JMS template.
   */
  @Inject
  @Filter(value = "(component-type=jmsXA)")
  private transient JmsOperations template;
  /**
   * exported REST address.
   */
  private static final String SERVICE_BASE_URL = "http://localhost:8181/cxf/helloService";

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
    builder.addTest(HelloEntityMessageCreator.class);
    builder.setHeader(Constants.EXPORT_PACKAGE,
        "net.osgiliath.hello.business.impl.services.impl.services.impl.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");

    return builder;
  }

  /**
   * Cleans all data.
   */
  @Before
  public void cleanMessages() {
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target
        .request(MediaType.APPLICATION_XML);
    builder.delete();
    client.close();
  }

  /**
   * Tests a webservice call.
   * 
   */
  @Test
  public void testSayHello() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        LOG.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());

      }
      LOG.debug("*********  End list ****************");
    }
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target
        .request(MediaType.APPLICATION_XML);
    builder
        .post(Entity.xml(HelloEntity.builder().helloMessage("John").build()));
    final Invocation.Builder respbuilder = target
        .request(MediaType.APPLICATION_XML);
    final Hellos hellos = respbuilder.get(Hellos.class);
    assertEquals(1, hellos.getHelloCollection().size());
    client.close();
  }

  /**
   * Tests a web service validation call (look at the stacktrace).
   * 
   */
  @Test
  public void testSayHelloValidationError() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        LOG.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());

      }
      LOG.debug("*********  End list ****************");
    }
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target
        .request(MediaType.APPLICATION_XML);
    builder.post(Entity.xml(HelloEntity.builder().helloMessage("J").build()));
    client.close();

  }

  /**
   * JMS endpoint call.
   * 
   * @throws JMSException
   *           not expected
   */
  @Test
  public void testSayHelloJMS() throws JMSException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("sending message");
    }
    this.template.send("helloServiceQueueIn", new HelloEntityMessageCreator());
    if (LOG.isDebugEnabled()) {
      LOG.debug("creating message consumer");
    }
    final Message rcv = this.template.receive("helloServiceQueueOut");
    final Hellos hellos = (Hellos) ((ObjectMessage) rcv).getObject();
    assertEquals(1, hellos.getHelloCollection().size());
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    target.request(MediaType.APPLICATION_XML);
    client.close();

  }
  /**
   * Karaf feature to test.
   * @return the feature
   */
  @Override
  protected Option featureToTest() {
    return features(
        maven().groupId(System.getProperty(MODULE_GROUP_ID))
            .artifactId(System.getProperty(MODULE_GROUP_ID) + ".features")
            .type("xml").classifier("features").versionAsInProject(),
        System.getProperty(MODULE_PARENT_ARTIFACT_ID) + ".itests.blueprint");
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
