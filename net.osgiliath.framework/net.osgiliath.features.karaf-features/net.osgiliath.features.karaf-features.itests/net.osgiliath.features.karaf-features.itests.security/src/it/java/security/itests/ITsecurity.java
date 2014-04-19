package security.itests;

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

import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import helper.exam.AbstractKarafPaxExamConfiguration;

import javax.inject.Inject;

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

import security.MUser;
import security.SecurityService;

/**
 * TODO example of an integration test
 * @author charliemordant
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITsecurity extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITsecurity.class);
	
	@Inject
	private BundleContext bundleContext;
	//Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 40000)
	private SecurityService securityService;
	
	
	
	//probe
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
        builder.setHeader("Export-Package", "security.itests");
        builder.setHeader("Bundle-ManifestVersion", "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        return builder;
    }
	@Test
	public void testAuthenticate() throws Exception {
		LOG.trace("************Listing **********************");
		for (Bundle b : bundleContext.getBundles()) {
			LOG.debug("bundle: " +b.getSymbolicName() + ", state: " +b.getState() );
		}
		MUser user = new MUser();
		user.setPseudo("toto");
		user.setPassword("myPassword");
		securityService.onSubscription(user);
		assertTrue(securityService.authenticate("toto", "myPassword"));
		
		
	}
	@Override
	protected Option featureToTest() {
		return features(
				maven().artifactId(
						"net.osgiliath.features.karaf-features.itests.feature")
						.groupId("net.osgiliath.framework").type("xml")
						.classifier("features").versionAsInProject(),
				"osgiliath-itests-security");
	}
		
}
