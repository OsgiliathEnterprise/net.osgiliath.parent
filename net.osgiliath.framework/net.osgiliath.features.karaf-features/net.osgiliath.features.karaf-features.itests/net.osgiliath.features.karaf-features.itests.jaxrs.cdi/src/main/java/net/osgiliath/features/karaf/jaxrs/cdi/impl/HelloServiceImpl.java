package net.osgiliath.features.karaf.jaxrs.cdi.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jaxrs.web
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import net.osgiliath.features.karaf.jaxrs.cdi.HelloServiceJaxRS;
import net.osgiliath.features.karaf.jaxrs.cdi.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.cdi.model.Hellos;
import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;
import net.osgiliath.helpers.cdi.eager.Eager;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * 
 * @author charliemordant REST Service implementation for testing purpose (the
 *         only noticeable thing is the CXFEndpoint annotation
 */
@Eager
@ApplicationScoped
@CXFEndpoint(url = "/helloService", providersClasses = {
	JAXBElementProvider.class, JSONProvider.class, ExceptionXmlMapper.class })
public class HelloServiceImpl implements HelloServiceJaxRS {
    /**
     * Instances elements
     */
    private Collection<HelloObject> objects = new ArrayList<HelloObject>();

    /**
     * Registering instance
     */
    @Override
    public final void persistHello(final HelloObject helloObject) {
	this.objects.add(helloObject);

    }

    /**
     * Returns registered instances
     */
    @Override
    public Hellos getHellos() {

	return new Hellos(Lists.newArrayList(Iterables.transform(this.objects,
		new Function<HelloObject, String>() {

		    @Override
		    public String apply(final HelloObject input) {
			return input.getHelloMessage();
		    };
		})));
    }

}
