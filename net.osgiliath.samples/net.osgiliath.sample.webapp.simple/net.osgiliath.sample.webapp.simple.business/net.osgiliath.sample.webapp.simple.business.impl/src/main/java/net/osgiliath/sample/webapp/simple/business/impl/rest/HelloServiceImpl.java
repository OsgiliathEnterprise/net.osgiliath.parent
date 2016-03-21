package net.osgiliath.sample.webapp.simple.business.impl.rest;

import javax.inject.Inject;

import org.ops4j.pax.cdi.api.OsgiService;

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
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.osgiliath.sample.webapp.simple.business.spi.HelloService;
import net.osgiliath.sample.webapp.simple.business.spi.annotations.REST;
import net.osgiliath.sample.webapp.simple.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.simple.model.daos.HelloRepository;
import net.osgiliath.sample.webapp.simple.model.entities.HelloEntity;


/**
 * REST Service implementation for testing purpose.
 * 
 * @author charliemordant
 */
@REST
public class HelloServiceImpl implements HelloServiceJaxRS {
	
	
	@Inject
	@OsgiService
	private transient HelloRepository repository;
	
  

  /**
   * Registering instance.
   * 
   * @param helloObject
   *          the element to save in the database
   */
  @Override
  public void persistHello(final HelloEntity helloObject) {
	  repository.save(helloObject);
  }

  /**
   * Returns registered instances.
   * 
   * @return all instances
   */
  @Override
  public Hellos getHellos() {
	  
    return new Hellos(Lists.newArrayList(Iterables.transform(repository.findAll(),
        new Function<HelloEntity, String>() {
          @Override
          public String apply(final HelloEntity input) {
            return input.getHelloMessage();
          }
        })));
  }

  /**
   * Deletes all elements.
   */
  @Override
  public void deleteHellos() {
	  repository.deleteAll();
    
  }


}
