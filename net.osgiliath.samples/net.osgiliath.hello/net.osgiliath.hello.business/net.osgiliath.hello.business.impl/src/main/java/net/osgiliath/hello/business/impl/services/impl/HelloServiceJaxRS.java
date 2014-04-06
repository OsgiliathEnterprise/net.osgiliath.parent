package net.osgiliath.hello.business.impl.services.impl;

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

import helpers.cxf.exception.handling.jaxrs.mapper.ExceptionXmlMapper;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloObject;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;
import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
/**
 * Sample of a business service
 * @author charliemordant
 *
 */
@Slf4j
//@Default @OsgiServiceProvider(classes= {HelloServiceJaxRS.class})
@OsgiServiceProvider
@CXFEndpoint(url="/helloService", providersClasses= {JAXBElementProvider.class, JSONProvider.class, ExceptionXmlMapper.class})
public class HelloServiceJaxRS implements net.osgiliath.hello.business.impl.HelloServiceJaxRS {
	//TODO you can use annotation intra bundle, but its not so compatible with blueprint xml file @Inject @OsgiService(dynamic=true)
	@Setter
	@Inject @OsgiService
	private HelloObjectRepository helloObjectRepository;
	//JSR 303 validator
	
	@Override
	public void persistHello(@NotNull @Valid  HelloObject helloObject_p) {
		log.info("persisting new message with jaxrs: " + helloObject_p.getHelloMessage());
		helloObjectRepository.save(helloObject_p);
		
	}

	@Override
	public Hellos getHellos() {
		Collection<HelloObject> helloObjects = helloObjectRepository.findAll();
		if (helloObjects.isEmpty()) {
			throw new UnsupportedOperationException("You could not call this method when there are no helloObjects");
		}
		return Hellos.builder().helloCollection(Lists.newArrayList(Iterables.transform(helloObjects, helloObjectToStringFunction))).build();
	}
	//Guava function waiting for Java 8
	private Function<HelloObject, String> helloObjectToStringFunction = new Function<HelloObject, String>() {

		@Override
		public String apply(HelloObject arg0) {
			return arg0.getHelloMessage();
		}
	};
	
	@Override
	public void deleteAll() {
		helloObjectRepository.deleteAll();
	}
	

}
