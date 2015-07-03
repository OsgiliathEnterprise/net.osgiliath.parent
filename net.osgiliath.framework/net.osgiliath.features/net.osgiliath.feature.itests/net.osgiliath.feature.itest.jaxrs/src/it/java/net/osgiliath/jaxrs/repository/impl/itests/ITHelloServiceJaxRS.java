package net.osgiliath.jaxrs.repository.impl.itests;

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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import net.osgiliath.helper.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.jaxrs.HelloEntity;
import net.osgiliath.jaxrs.Hellos;
import org.apache.karaf.features.BootFinished;
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
 * REST integration tests.
 * 
 * @author charliemordant
 * 
 */

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJaxRS extends AbstractPaxExamKarafConfiguration {
  /**
   * OSGI bundleContext.
   */
  @Inject
  private transient BundleContext bundleContext;
  /**
   * Bootfinished event.
   */
  @Inject
  @Filter(timeout = 60000)
  private transient BootFinished bootFinished;
  /**
   * Logger.
   */
  protected static final Logger LOG = LoggerFactory
      .getLogger(ITHelloServiceJaxRS.class);

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
    builder
        .setHeader(Constants.EXPORT_PACKAGE,
            "net.osgiliath.helpers.exam, net.osgiliath.jaxrs.repository.impl.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
    return builder;
  }

  /**
   * Web service call.
   * 
   */
  @Test
  public void testSayHello() {
    if (LOG.isDebugEnabled()) {
      LOG.trace("************Listing **********************");
      for (final Bundle b : this.bundleContext.getBundles()) {
        LOG.debug("bundle: " + b.getSymbolicName() + ", state: " + b.getState());

      }
      LOG.trace("************end Listing **********************");
    }
    final Client client = ClientBuilder.newClient();

    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
    final HelloEntity entity = HelloEntity.builder().helloMessage("Charlie").build();

    builder.post(Entity.xml(entity));
    final Invocation.Builder respbuilder = target.request(MediaType.APPLICATION_XML);

    final Hellos hellos = respbuilder.get(Hellos.class);

    assertEquals(1, hellos.getHelloCollection().size());
    respbuilder.delete();
    client.close();

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
            .groupId("net.osgiliath.framework").type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-jaxrs");
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
