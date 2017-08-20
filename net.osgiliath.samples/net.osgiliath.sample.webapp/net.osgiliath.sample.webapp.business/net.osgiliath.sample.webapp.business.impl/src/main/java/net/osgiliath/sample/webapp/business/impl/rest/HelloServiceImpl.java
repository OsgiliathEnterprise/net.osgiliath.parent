package net.osgiliath.sample.webapp.business.impl.rest;

/*
 * #%L
 * Helloworld sample business module
 * %%
 * Copyright (C) 2013 - 2017 Osgiliath
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

import java.util.stream.Collectors;
import javax.inject.Inject;
import net.osgiliath.sample.webapp.business.spi.annotations.REST;
import net.osgiliath.sample.webapp.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.model.daos.HelloRepository;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;
import org.ops4j.pax.cdi.api.Service;


/**
 * REST Service implementation for testing purpose.
 * 
 * @author charliemordant
 */
@REST
public class HelloServiceImpl implements HelloServiceJaxRS {
	
	/**
	 * JPA repository.
	 */
	@Inject
	@Service
	private HelloRepository repository;
	
  

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
    
    return new Hellos(repository.findAll().stream().map(input -> input.getHelloMessage()).collect(Collectors.toList()));
  }

  /**
   * Deletes all elements.
   */
  @Override
  public void deleteHellos() {
	  repository.deleteAll();
    
  }


}
