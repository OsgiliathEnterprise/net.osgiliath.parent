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
import javax.ws.rs.core.MediaType;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.helpers.exam.PaxExamKarafConfigurationFactory;
import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.cxf.jaxrs.client.WebClient;
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

/**
 * TODO example of an integration test
 * @author charliemordant
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITHelloServiceJaxRS extends PaxExamKarafConfigurationFactory {
	@Inject
	private BundleContext bundleContext;
	
	//Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 40000)
	private BootFinished bootFinished;
	//JMS template
	@Inject
	@Filter(value="(component-type=jms)")
	private Component jmsComponent;
	//exported REST adress
	private static String helloServiceBaseUrl = "http://localhost:8181/cxf/helloService";
	
	//probe
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
		builder.addTest(PaxExamKarafConfigurationFactory.class);
        builder.setHeader("Export-Package", "net.osgiliath.hello.business.impl.services.impl.services.impl.itests");
        builder.setHeader("Bundle-ManifestVersion", "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        return builder;
    }
	@Test
	public void testSayHello() throws Exception {
		System.out.println("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			System.out.println("bundle: " +b.getSymbolicName() + ", state: " +b.getState() );
		}
		System.out.println("*********  End list ****************");
		WebClient helloServiceClient = WebClient.create(helloServiceBaseUrl);
		helloServiceClient.path("/hello");
		helloServiceClient.type(MediaType.APPLICATION_XML);
		//helloServiceClient.query("helloObject", HelloObject.builder().helloMessage("John").build());
		helloServiceClient.post(HelloEntity.builder().helloMessage("John").build());
		helloServiceClient.accept(MediaType.APPLICATION_XML);
		Hellos hellos = helloServiceClient.get(Hellos.class);
		assertEquals(1, hellos.getHelloCollection().size());
		helloServiceClient.delete();
	}
	@Test
	public void testSayHelloValidationError() throws Exception {
		System.out.println("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			System.out.println("bundle: " +b.getSymbolicName() + ", state: " +b.getState() );
		}
		System.out.println("*********  End list ****************");
		WebClient helloServiceClient = WebClient.create(helloServiceBaseUrl);
		helloServiceClient.path("/hello");
		helloServiceClient.type(MediaType.APPLICATION_XML);
		//helloServiceClient.query("helloObject", HelloObject.builder().helloMessage("John").build());
		helloServiceClient.post(HelloEntity.builder().helloMessage("J").build());
		helloServiceClient.accept(MediaType.APPLICATION_XML);
		
	}
	@Test
	public void testSayHelloJMS()  {
		ProducerTemplate template = jmsComponent.getCamelContext().createProducerTemplate();
		template.sendBody("jms:queue:helloServiceQueueIn", HelloEntity.builder().helloMessage("Doe").build());
		ConsumerTemplate consumer = jmsComponent.getCamelContext().createConsumerTemplate();
		Hellos hellos = consumer.receiveBody("jms:queue:helloServiceQueueOut", Hellos.class);
		assertEquals(1, hellos.getHelloCollection().size());
		WebClient helloServiceClient = WebClient.create(helloServiceBaseUrl);
		helloServiceClient.path("/hello");
		helloServiceClient.type(MediaType.APPLICATION_XML);
		//helloServiceClient.query("helloObject", HelloObject.builder().helloMessage("John").build());
		helloServiceClient.delete();
		
		
	}
	@Override
	protected Option featureToTest() {
		return features(
				maven().groupId(System.getProperty(BUNDLE_GROUP_ID))
				.artifactId(
						System.getProperty(BUNDLE_GROUP_ID)
								+ ".features")
				.type("xml").classifier("features")
				.versionAsInProject(),
		System.getProperty(BUNDLE_ARTIFACT_ID) + ".itests.blueprint");
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
