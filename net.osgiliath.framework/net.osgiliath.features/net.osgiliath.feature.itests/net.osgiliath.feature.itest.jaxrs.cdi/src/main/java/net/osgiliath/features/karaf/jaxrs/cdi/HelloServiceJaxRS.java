package net.osgiliath.features.karaf.jaxrs.cdi;

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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.osgiliath.features.karaf.jaxrs.cdi.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.cdi.model.Hellos;

/**
 * REST service definition.
 * @author charliemordant.
 */
@Path("/hello")
public interface HelloServiceJaxRS {
  /**
   * Registering instance.
   * 
   * @param helloObject
   *          instance
   */
  @POST
  @Consumes(MediaType.APPLICATION_XML)
  void persistHello(HelloObject helloObject);

  /**
   * retrieving instances.
   * @return instances
   */
  @GET
  @Produces(MediaType.APPLICATION_XML)
  Hellos getHellos();
  /**
   * Delete all hellos.
   */
  @DELETE
  void deleteHellos();
}
