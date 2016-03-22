package itests.net.osgiliath.feature.itest.jaxrs;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jaxrs.web
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import net.osgiliath.feature.itest.jaxrs.model.HelloObject;
import net.osgiliath.feature.itest.jaxrs.model.Hellos;
import net.osgiliath.module.exam.AbstractPaxExamKarafConfiguration;
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
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI REST test.
 * 
 * @author charliemordant
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITCxfCdi extends AbstractPaxExamKarafConfiguration {
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ITCxfCdi.class);

  /**
   * Boot finished event.
   */
  @Inject
  @Filter(timeout = 40000)
  private BootFinished bootFinished;

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
        "itests.net.osgiliath.feature.itest.jaxrs");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");

    return builder;
  }

  /**
   * Test a web service call.
   * 
   */
  @Test
  public void testSayHello() {
    if (LOG.isTraceEnabled()) {
      LOG.trace("************ start testSayHello **********************");
    }
    final Client client = ClientBuilder.newClient();

    WebTarget target = client.target(SERVICE_BASE_URL);
    target = target.path("hello");
    final Invocation.Builder builder = target
        .request(MediaType.APPLICATION_XML);
    final HelloObject entity = HelloObject.builder().helloMessage("John")
        .build();

    builder.post(Entity.xml(entity));
    final Invocation.Builder respbuilder = target
        .request(MediaType.APPLICATION_XML);

    final Hellos hellos = respbuilder.get(Hellos.class);

    respbuilder.delete();
    client.close();
    assertEquals(1, hellos.getHelloCollection().size());
    if (LOG.isTraceEnabled()) {
      LOG.trace("************ end testSayHello **********************");
    }

  }
  /**
   * Karaf feature to provision for the test.
   * @return the Feature Option
   */
  protected Option featureToTest() {

    return features(
        maven()
            .artifactId("net.osgiliath.feature.itest.feature")
            .groupId(System.getProperty(MODULE_GROUP_ID)).type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-jaxrs-cdi");
  }

  static {
    // uncomment to enable debugging of this test class
    // paxRunnerVmOption = DEBUG_VM_OPTION; //NOSONAR

  }

  /**
   * Returns the Ops4J exam configuration.
   * 
   * @return the configuration
   */
  @Configuration
  public Option[] config() {
    return createConfig();
  }

  /**
   * Sets the logging level.
   * 
   * @return the logging level Option
   */

  @Override
  protected Option loggingLevel() {
    return logLevel(LogLevel.INFO);
  }

}