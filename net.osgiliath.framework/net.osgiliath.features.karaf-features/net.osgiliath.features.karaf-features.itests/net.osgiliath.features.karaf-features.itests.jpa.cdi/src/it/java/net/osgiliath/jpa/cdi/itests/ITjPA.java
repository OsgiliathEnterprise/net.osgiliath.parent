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
import helper.exam.AbstractKarafPaxExamConfiguration;

import java.util.Collection;

import javax.inject.Inject;

import net.osgiliath.jpa.cdi.model.HelloEntity;
import net.osgiliath.jpa.cdi.repository.HelloRepository;
import net.osgiliath.jpa.cdi.repository.impl.HelloJpaRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
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
 * TODO example of an integration test
 * @author charliemordant
 *
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITjPA extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITjPA.class);
	
	//Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 60000)
	private HelloRepository repository;
	
	
	//probe
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
        builder.setHeader("Export-Package", "net.osgiliath.jpa.itests");
        builder.setHeader("Bundle-ManifestVersion", "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        return builder;
    }
	@Test
	public void testSayHello() throws Exception {
		
		HelloEntity entity = new HelloEntity();
		entity.setHelloMessage("hello");
		entity = repository.save(entity);
		Collection<? extends HelloEntity> entities = repository.findAll();
		
		assertEquals(entities.size(), 1);
		HelloEntity persisted = entities.iterator().next();
		assertEquals(persisted.getHelloMessage(), "hello");
		assertNotNull(persisted.getEntityId());
	}
		
}
