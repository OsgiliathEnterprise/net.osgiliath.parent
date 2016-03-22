package itests.net.osgiliath.sample.webapp.simple.business.impl;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.sample.webapp.simple.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.simple.model.entities.HelloEntity;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Component;
import org.apache.camel.ComponentConfiguration;
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
   */
  @Test
  public void testSayHello() {
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
   */
  @Test
  public void testSayHelloValidationError() {
    if (log.isDebugEnabled()) {
      log.trace("************ start testSayHelloValidationError *************");
      log.debug("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        log.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());

      }
      log.debug("*********  End list ****************");
    }
    final Client client = ClientBuilder.newClient();
    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
    builder.post(Entity.xml(HelloEntity.builder().helloMessage("J").build()));
    log.trace("************ end testSayHelloValidationError ******************");

  }

  /**
   * Test JMS call.
   * @throws CamelExecutionException 
   * @throws IOException 
   */
  @Test
  
  public void testSayHelloJMS() throws CamelExecutionException, IOException {
    log.info("************ start testSayHelloJMS **********************");
    if (log.isDebugEnabled()) {
      log.debug("Component: " + this.jmsComponent);
      log.trace("Camel context: " + this.jmsComponent.getCamelContext());
    }
    final ProducerTemplate template = this.jmsComponent.getCamelContext()
        .createProducerTemplate();
    log.trace("Producer template: " + template);
    ObjectMapper mapper = new ObjectMapper();
    template.sendBody("jms:queue:helloServiceQueueIn", mapper.writeValueAsString(HelloEntity.builder()
        .helloMessage("Doe").build()));
    
    final ConsumerTemplate consumer = this.jmsComponent.getCamelContext()
        .createConsumerTemplate();
    final Hellos hellos = mapper.readValue(consumer.receiveBody(
        "jms:topic:helloServiceQueueOut", String.class), Hellos.class);
    assertTrue(hellos.getHelloCollection().size() > 0);
   
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
            .artifactId("net.osgiliath.sample.webapp.simple.features")
            .type("xml").classifier("features").versionAsInProject(),
       "net.osgiliath.sample.webapp.simple.business.itests");
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
