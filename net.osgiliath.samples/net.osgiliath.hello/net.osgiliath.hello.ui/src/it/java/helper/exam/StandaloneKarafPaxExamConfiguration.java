package helper.exam;

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

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfigurationFactory;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General integration test declaration
 * 
 * @author charliemordant
 * 
 */
public class StandaloneKarafPaxExamConfiguration extends AbstractPaxExamKarafConfigurationFactory {

	
	@Override
	protected Option featureToTest() {

		return features(
				maven().artifactId("net.osgiliath.hello.features")
				.groupId("net.osgiliath.hello").type("xml")
				.classifier("features")
				.versionAsInProject(), "net.osgiliath.hello.full.blueprint.itests");
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
