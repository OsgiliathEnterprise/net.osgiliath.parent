package net.osgiliath.jpa.cdi.daos.itests;

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

import org.junit.Ignore;

import java.util.Collection;
import javax.inject.Inject;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.jpa.cdi.daos.facade.OsgiRepositoriesProducer;
import net.osgiliath.jpa.cdi.entities.HelloEntity;
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
 * JPA test case.
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITjPACDI extends AbstractPaxExamKarafConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(ITjPACDI.class);

  /**
   *  Exported service via blueprint.xml.
   */
  @Inject
  @Filter(timeout = 60000)
  private transient OsgiRepositoriesProducer repositoryProducer;

  /**
   * probe adding the abstract test class.
   * @param builder the pax probe builder
   * @return the provisionned probe.
   */
  @ProbeBuilder
  public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
    builder.addTest(AbstractPaxExamKarafConfiguration.class);
    builder.setHeader(Constants.EXPORT_PACKAGE, "net.osgiliath.jpa.cdi.daos.itests");
    builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
    builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
    return builder;
  }
  /**
   * Send entity to persist.
   * @throws Exception not expected
   */
  @Test
 @Ignore
public void testSayHello() {
    LOG.trace("Begin integration test");
    final HelloEntity entity = new HelloEntity();
    entity.setHelloMessage("hello");
    this.repositoryProducer.save(entity);
    final Collection<? extends HelloEntity> entities = this.repositoryProducer.findAll();

    assertEquals(entities.size(), 1);
    final HelloEntity persisted = entities.iterator().next();
    assertEquals(persisted.getHelloMessage(), "hello");
    assertNotNull(persisted.getEntityId());
  }
  /**
   * Feature to test.
   * @return the feature
   */
  @Override
  protected Option featureToTest() {

    return features(
        maven()
            .artifactId("net.osgiliath.features.karaf-features.itests.feature")
            .groupId("net.osgiliath.framework").type("xml")
            .classifier("features").versionAsInProject(),
        "osgiliath-itests-jpa-cdi-data");
  }

  static {
    // uncomment to enable debugging of this test class
   //  paxRunnerVmOption = DEBUG_VM_OPTION; //NOSONAR

  }
  /**
   * Creates the default configuration.
   * @return the default configuration
   */
  @Configuration
  public Option[] config() {
    return createConfig();
  }

}
