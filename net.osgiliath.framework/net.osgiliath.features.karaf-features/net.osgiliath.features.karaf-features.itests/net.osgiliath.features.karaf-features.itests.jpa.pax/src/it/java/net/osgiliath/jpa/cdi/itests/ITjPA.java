package net.osgiliath.jpa.cdi.itests;

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
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;

import java.util.Collection;
import javax.inject.Inject;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.jpa.cdi.entities.HelloEntity;
import net.osgiliath.jpa.cdi.repository.HelloRepository;
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
//import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for JPA cdi integration
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITjPA extends AbstractPaxExamKarafConfiguration {
  private static Logger LOG = LoggerFactory.getLogger(ITjPA.class);

  // Exported service via blueprint.xml
  @Inject
  @Filter(timeout = 60000)
  private HelloRepository repository;

  // probe
  @ProbeBuilder
  public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
    builder.addTest(AbstractPaxExamKarafConfiguration.class);
    builder.setHeader(Constants.EXPORT_PACKAGE, "net.osgiliath.jpa.cdi.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
    return builder;
  }

  // @Ignore
  @Test
  public void testSayHello() throws Exception {

    HelloEntity entity = HelloEntity.builder().helloMessage("hello").build();

    entity = repository.save(entity);
    Collection<? extends HelloEntity> entities = repository.getAll();

    assertEquals(1, entities.size());
    HelloEntity persisted = entities.iterator().next();
    assertEquals(persisted.getHelloMessage(), "hello");
    assertNotNull(persisted.getEntityId());
  }

  @Override
  protected Option featureToTest() {
    return features(
        maven()
            .artifactId("net.osgiliath.features.karaf-features.itests.feature")
            .groupId("net.osgiliath.framework").type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-jpa-cdi");
  }

  static {
    // uncomment to enable debugging of this test class
    // paxRunnerVmOption = DEBUG_VM_OPTION;

  }

  // @Override
//  protected Option loggingLevel() {
//    // // TODO Auto-generated method stub
//    return logLevel(LogLevel.DEBUG);
//  }

  @Configuration
  public Option[] config() {
    return createConfig();
  }
}
