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
import org.junit.After;
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
 * TODO example of an integration test
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
@Slf4j
public class ITHelloServiceJaxRS extends AbstractPaxExamKarafConfiguration {
	@Inject
	@Filter(timeout = 400000)
	private BootFinished bootFinished;
	@Inject
	private BundleContext bundleContext;
	// JMS template
	@Inject
	@Filter(value = "(component-type=jms)")
	private Component jmsComponent;
	// exported REST adress
	private static String helloServiceBaseUrl = "http://localhost:8181/cxf/helloService";

	// probe
	@ProbeBuilder
	public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
		builder.addTest(AbstractPaxExamKarafConfiguration.class);
		builder.setHeader(Constants.EXPORT_PACKAGE,
				"net.osgiliath.hello.business.impl.services.impl.services.impl.itests");
		builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
		builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
		return builder;
	}

	@After
	public void cleanMessages() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(helloServiceBaseUrl);
		target = target.path("hello");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
		builder.delete();
		client.close();
	}

	@Test
	public void testSayHello() throws Exception {
		log.trace("************ start testSayHello **********************");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(helloServiceBaseUrl);
		target = target.path("hello");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
		builder.post(Entity.xml(HelloEntity.builder().helloMessage("John")
				.build()));
		Invocation.Builder respbuilder = target
				.request(MediaType.APPLICATION_XML);
		Hellos hellos = respbuilder.get(Hellos.class);
		assertEquals(1, hellos.getHelloCollection().size());
		client.close();

		log.trace("************ end testSayHello **********************");
	}

	@Test
	public void testSayHelloValidationError() throws Exception {
		log.trace("************ start testSayHelloValidationError **********************");
		log.debug("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			log.debug("bundle: " + b.getSymbolicName() + ", state: "
					+ b.getState());

		}
		log.debug("*********  End list ****************");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(helloServiceBaseUrl);
		target = target.path("hello");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
		builder.post(Entity
				.xml(HelloEntity.builder().helloMessage("J").build()));
		log.trace("************ end testSayHelloValidationError **********************");

	}

	@Test
	public void testSayHelloJMS() {
		log.info("************ start testSayHelloJMS **********************");
		log.debug("Component: " + jmsComponent);
		log.trace("Camel context: " + jmsComponent.getCamelContext());
		ProducerTemplate template = jmsComponent.getCamelContext()
				.createProducerTemplate();
		log.trace("Producer template: " + template);
		template.sendBody("jms:queue:helloServiceQueueIn", HelloEntity
				.builder().helloMessage("Doe").build());
		ConsumerTemplate consumer = jmsComponent.getCamelContext()
				.createConsumerTemplate();
		Hellos hellos = consumer.receiveBody("jms:queue:helloServiceQueueOut",
				Hellos.class);
		assertTrue(hellos.getHelloCollection().size() > 0);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(helloServiceBaseUrl);
		target = target.path("hello");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
		client.close();
		log.info("************ end testSayHelloJMS **********************");
	}

	@Override
	protected Option featureToTest() {

		return features(maven().groupId(System.getProperty(MODULE_GROUP_ID))
				.artifactId(System.getProperty(MODULE_GROUP_ID) + ".features")
				.type("xml").classifier("features").versionAsInProject(),
				System.getProperty(MODULE_PARENT_ARTIFACT_ID) + ".itests.cdi");
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
