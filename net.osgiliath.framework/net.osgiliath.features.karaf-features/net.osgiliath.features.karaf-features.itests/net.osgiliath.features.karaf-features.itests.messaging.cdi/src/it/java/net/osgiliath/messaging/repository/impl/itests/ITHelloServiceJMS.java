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
import helper.exam.AbstractKarafPaxExamConfiguration;

import javax.inject.Inject;

import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;

import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.karaf.features.BootFinished;
import org.junit.Test;
import org.junit.runner.RunWith;
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

/**
 * TODO example of an integration test
 * @author charliemordant
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJMS extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITHelloServiceJMS.class);
	
	@Inject
	private BundleContext bundleContext;
	@Inject
	@Filter(timeout = 40000)
	private BootFinished bootFinished;
	
	@Inject
	@Filter(value="(component-type=jms)")
	private Component jmsComponent;

	
	
	//probe
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
        builder.setHeader("Export-Package", "net.osgiliath.messaging.repository.impl.itests");
        builder.setHeader("Bundle-ManifestVersion", "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        
        return builder;
    }
	@Test
	public void testSayHello() throws Exception {
		LOG.trace("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			LOG.debug("bundle: " +b.getSymbolicName() + ", state: " +b.getState() );
		}
		LOG.trace("*********  End list ****************");
		//LOG.info("JMS component on itests: " + repository.getCdiBootStrap().getJms().getCamelContext());
		//ProducerTemplate template = repository.getCdiBootStrap().getJms().getCamelContext().createProducerTemplate();
		HelloEntity entity = new HelloEntity();
 		entity.setHelloMessage("Charlie");
 		LOG.info("Sending Body");
// 		repository.directSave(entity);
// 		Thread.sleep(1000);
// 		repository.ensureDelivery();
 		ProducerTemplate template = jmsComponent.getCamelContext().createProducerTemplate();
 		template.sendBody("jms:queue:helloServiceQueueIn", entity);
//		repository.directSave(entity);
		ConsumerTemplate consumer = template.getCamelContext().createConsumerTemplate();
		LOG.info("Waiting answer");
		Hellos hellos = consumer.receiveBody("jms:queue:helloServiceQueueOut", 4000, Hellos.class);
		LOG.warn("Hellos: " + hellos);
		assertEquals(1, hellos.getEntities().size());

	}
	@Override
	protected Option featureToTest() {
		
		return features(
				maven().artifactId(
						"net.osgiliath.features.karaf-features.itests.feature")
						.groupId("net.osgiliath.framework").type("xml")
						.classifier("features").versionAsInProject(),
				"osgiliath-itests-messaging-cdi");
	}

	
}
