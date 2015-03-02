package net.osgiliath.hello.business.impl.cdi.services.impl.itests;

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
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfiguration;

import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
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

/**
 * Test a CDI web service and JMS messages.
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
@Slf4j
public class ITHelloServiceJaxRS extends AbstractPaxExamKarafConfiguration {
  /**
   * Boot finished event.
   */
  @Inject
  @Filter(timeout = 400000)
  private transient BootFinished bootFinished;
  /**
   * OSGI Bundle context.
   */
  @Inject
  private transient BundleContext bundleContext;
  /**
   * JMS template.
   */
  @Inject
  @Filter(value = "(component-type=jms)")
  private transient Component jmsComponent;
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
   * Web service call test.
   * 
   * @throws Exception
   *           not expected
   */
  @Test
  public void testSayHello() throws Exception {
    log.trace("************ start testSayHello **********************");
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

    log.trace("************ end testSayHello **********************");
  }

  /**
   * Test REST message error (look at the console trace).
   * 
   * @throws Exception
   *           not expected
   */
  @Test
  public void testSayHelloValidationError() throws Exception {
    if (log.isDebugEnabled()) {
      log.trace("************ start testSayHelloValidationError ***************");
      log.debug("************Listing **********************");
      for (Bundle b : bundleContext.getBundles()) {
        log.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());

      }
      log.debug("*********  End list ****************");
    }
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
    builder.post(Entity.xml(HelloEntity.builder().helloMessage("J").build()));
    log.trace("************ end testSayHelloValidationError **********************");

  }

  /**
   * Test JMS call.
   */
  @Test
  public void testSayHelloJMS() {
    log.info("************ start testSayHelloJMS **********************");
    if (log.isDebugEnabled()) {
      log.debug("Component: " + this.jmsComponent);
      log.trace("Camel context: " + this.jmsComponent.getCamelContext());
    }
    final ProducerTemplate template = this.jmsComponent.getCamelContext()
        .createProducerTemplate();
    log.trace("Producer template: " + template);
    template.sendBody("jms:queue:helloServiceQueueIn", HelloEntity.builder()
        .helloMessage("Doe").build());
    final ConsumerTemplate consumer = this.jmsComponent.getCamelContext()
        .createConsumerTemplate();
    final Hellos hellos = consumer.receiveBody(
        "jms:queue:helloServiceQueueOut", Hellos.class);
    assertTrue(hellos.getHelloCollection().size() > 0);
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    target
        .request(MediaType.APPLICATION_XML);
    client.close();
    log.info("************ end testSayHelloJMS **********************");
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
        System.getProperty(MODULE_PARENT_ARTIFACT_ID) + ".itests.cdi");
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
