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

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
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
 * Sample of a business service with JaxRS and CDI
 * 
 * @author charliemordant
 * 
 */
@Slf4j
@OsgiServiceProvider
@CXFEndpoint(url = "/helloService", providersClasses = {
	JAXBElementProvider.class, JSONProvider.class, ExceptionXmlMapper.class })
public class HelloServiceJaxRS implements
	net.osgiliath.hello.business.impl.HelloServiceJaxRS {
    /**
     * JPA persistence repository
     */
    @Inject
    @OsgiService
    private HelloObjectRepository helloObjectRepository;

   
    /**
     * persistence module
     */
    @Override
    public void persistHello(@NotNull @Valid HelloEntity helloObject_p) {
	log.info("persisting new message with jaxrs: "
		+ helloObject_p.getHelloMessage());
	this.helloObjectRepository.save(helloObject_p);

    }
    /**
     * Gets hello entities
     */
    @Override
    public Hellos getHellos() {
	final Collection<HelloEntity> helloObjects = helloObjectRepository.findAll();
	if (helloObjects.isEmpty()) {
	    throw new UnsupportedOperationException(
		    "You could not call this method when there are no helloObjects");
	}
	return Hellos
		.builder()
		.helloCollection(
			Lists.newArrayList(Iterables.transform(helloObjects,
				helloObjectToStringFunction))).build();
    }
    /**
     * converts entities to Strings
     */
    // Guava function waiting for Java 8
    private Function<HelloEntity, String> helloObjectToStringFunction = new Function<HelloEntity, String>() {

	@Override
	public String apply(HelloEntity arg0) {
	    return arg0.getHelloMessage();
	}
    };
    /**
     * deletes all entities
     */
    @Override
    public void deleteAll() {
	log.info("deleting all datas");
	this.helloObjectRepository.deleteAll();
    }

}
