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
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import jline.internal.Log;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfigurationFactory;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * TODO example of an integration test
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJaxRS extends
		AbstractPaxExamKarafConfigurationFactory {
	protected static final Logger log = LoggerFactory
			.getLogger(ITHelloServiceJaxRS.class);
	@Inject
	private BundleContext bundleContext;
	// Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 40000)
	private BootFinished bootFinished;

	// JMS template
	@Inject
	@Filter(value = "(component-type=jmsXA)")
	private JmsTemplate template;
	// exported REST adress
	private static String helloServiceBaseUrl = "http://localhost:8181/cxf/helloService";

	// probe
	@ProbeBuilder
	public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
		builder.addTest(AbstractPaxExamKarafConfigurationFactory.class);
		builder.setHeader(Constants.EXPORT_PACKAGE,
				"net.osgiliath.hello.business.impl.services.impl.services.impl.itests");
		builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
		builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");

		return builder;
	}

	@Test
	public void testSayHello() throws Exception {
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
		builder.post(Entity.xml(HelloEntity.builder().helloMessage("John")
				.build()));
		Invocation.Builder respbuilder = target
				.request(MediaType.APPLICATION_XML);
		Hellos hellos = respbuilder.get(Hellos.class);
		assertEquals(1, hellos.getHelloCollection().size());
		builder.delete();
		client.close();
	}

	@Test
	public void testSayHelloValidationError() throws Exception {
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
		client.close();

	}

	@Test
	public void testSayHelloJMS() throws JMSException {
		log.debug("sending message");
		template.send("helloServiceQueueIn", new MessageCreator(){

			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createObjectMessage(HelloEntity
						.builder().helloMessage("Doe").build());
			}});
		log.debug("creating message consumer");
		Message rcv = template.receive("helloServiceQueueOut");
		Hellos hellos = (Hellos) ((ObjectMessage)rcv).getObject();
		
		assertEquals(1, hellos.getHelloCollection().size());
	
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(helloServiceBaseUrl);
		target = target.path("hello");
		Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
		builder.delete();
		client.close();

	}

	@Override
	protected Option featureToTest() {
		return features(maven().groupId(System.getProperty(MODULE_GROUP_ID))
				.artifactId(System.getProperty(MODULE_GROUP_ID) + ".features")
				.type("xml").classifier("features").versionAsInProject(),
				System.getProperty(MODULE_PARENT_ARTIFACT_ID)
						+ ".itests.blueprint");
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
