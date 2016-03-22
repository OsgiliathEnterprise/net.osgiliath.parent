package net.osgiliath.sample.webapp.simple.business.impl.rest;

/*
 * #%L
 * CDI configured business module
 * %%
 * Copyright (C) 2013 - 2016 Osgiliath
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.osgiliath.sample.webapp.simple.business.spi.HelloService;
import net.osgiliath.sample.webapp.simple.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.simple.model.entities.HelloEntity;

/**
 * see how it configures REST web services URIs.
 * 
 * @author charliemordant
 *
 */

@Path("/hello")
@Api(value = "hello", description = "The hello resource access")
public interface HelloServiceJaxRS extends HelloService {
  /**
   * Saves Hello.
   * @param helloObject element to save
   */
  @POST
  @Override
  @Consumes(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  void persistHello(@NotNull @Valid HelloEntity helloObject);

  /**
   * Gets hellos.
   * @return all elements
   */
  @GET
  @Override
  @Produces(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @ApiOperation(value = "Find all hellos", notes = "More notes about this method", response = Hellos.class)
  Hellos getHellos();

  /**
   * deletes all hello.
   */
  @Override
  @DELETE
  void deleteHellos();
}
