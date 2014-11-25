package net.osgiliath.messaging.repository.impl.itests;

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
import javax.jms.Message;
import javax.jms.ObjectMessage;

import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfiguration;
import net.osgiliath.messaging.Hellos;
import net.osgiliath.messaging.repository.HelloRepository;

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
//import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsOperations;

/**
 * TODO example of an integration test
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJMS extends AbstractPaxExamKarafConfiguration {
	private static Logger LOG = LoggerFactory
			.getLogger(ITHelloServiceJMS.class);

	@Inject
	private BundleContext bundleContext;
	// Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 40000)
	private HelloRepository helloService;
	// JMS template
	@Inject
	@Filter(value = "(component-type=jmsXA)")
	private JmsOperations jmsTemplate;
	@Inject
	private BootFinished finished;

	// probe
	@ProbeBuilder
	public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
		builder.addTest(AbstractPaxExamKarafConfiguration.class);
		builder.addTest(HelloEntityMessageCreator.class);
		builder.setHeader(Constants.EXPORT_PACKAGE,
				"net.osgiliath.messaging.repository.impl.itests");
		builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
		builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
		return builder;
	}

	@Test
	public void testSayHello() throws Exception {
		LOG.trace("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			LOG.debug("bundle: " + b.getSymbolicName() + ", state: "
					+ b.getState());
		}
		LOG.trace("*********  End list ****************");
		jmsTemplate.send("HELLO.IN", new HelloEntityMessageCreator());
		
		Message message = jmsTemplate.receive("HELLO.OUT");
		Hellos hellos = (Hellos) ((ObjectMessage)message).getObject();
		assertEquals(1, hellos.getEntities().size());
		helloService.deleteAll();
	}

	@Override
	protected Option featureToTest() {
		return features(
				maven().artifactId(
						"net.osgiliath.features.karaf-features.itests.feature")
						.groupId("net.osgiliath.framework").type("xml")
						.classifier("features").versionAsInProject(),
				"osgiliath-itests-messaging");
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
