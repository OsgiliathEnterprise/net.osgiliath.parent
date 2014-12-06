package net.osgiliath.features.karaf.features.itests.jaxrs.web.cdi;

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

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import net.osgiliath.features.karaf.jaxrs.web.cdi.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.web.cdi.model.Hellos;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfiguration;
import org.apache.karaf.features.BootFinished;
import org.junit.Ignore;
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

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITPaxWebCxf extends AbstractPaxExamKarafConfiguration {
  private static Logger LOG = LoggerFactory.getLogger(ITPaxWebCxf.class);

  // Exported service via blueprint.xml
  @Inject
  @Filter(timeout = 400000)
  private BootFinished bootFinished;
  // JMS template
  // @Inject
  // @Filter(value="(component-type=jms)")
  // private Component jmsComponent;
  // exported REST adress
  private static String helloServiceBaseUrl = "http://localhost:8181/helloService/cxf";

  // probe
  @ProbeBuilder
  public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
    builder.addTest(AbstractPaxExamKarafConfiguration.class);
    builder.setHeader(Constants.EXPORT_PACKAGE,
        "net.osgiliath.hello.business.impl.services.impl.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");

    return builder;
  }

  @Test
  @Ignore
  public void testSayHello() throws Exception {
    LOG.trace("************ start testSayHello **********************");

    Client client = ClientBuilder.newClient();

    WebTarget target = client.target(helloServiceBaseUrl);
    target = target.path("hello");
    Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
    HelloObject entity = HelloObject.builder().helloMessage("John").build();

    builder.post(Entity.xml(entity));
    Invocation.Builder respbuilder = target.request(MediaType.APPLICATION_XML);

    Hellos hellos = respbuilder.get(Hellos.class);

    respbuilder.delete();
    client.close();
    assertEquals(1, hellos.getHelloCollection().size());
    LOG.trace("************ end testSayHello **********************");
  }

  @Override
  protected Option featureToTest() {
    return features(
        maven()
            .artifactId("net.osgiliath.features.karaf-features.itests.feature")
            .groupId("net.osgiliath.framework").type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-jaxrs-web-cdi");
  }

  static {
    // uncomment to enable debugging of this test class
    // paxRunnerVmOption = DEBUG_VM_OPTION;

  }

  @Configuration
  public Option[] config() {
    return createConfig();
  }
}
