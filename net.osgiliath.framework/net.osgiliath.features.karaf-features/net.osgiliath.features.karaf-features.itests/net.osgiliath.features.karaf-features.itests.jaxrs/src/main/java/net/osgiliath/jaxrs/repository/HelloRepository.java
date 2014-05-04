package net.osgiliath.jaxrs.repository;

/*
 * #%L
 * net.osgiliath.hello.model.jpa
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

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.osgiliath.jaxrs.HelloEntity;

//JPA accessible interface by business or route module (see business module for JMS or REST export, don't forget the template.mf cxf package export)
/**
 * 
 * @author charliemordant REST definition of the service
 */
@Path("/hello")
public interface HelloRepository {
    /**
     * find entities corrsponding to message
     */
    Collection<? extends HelloEntity> findByHelloObjectMessage(String message_p);
    /**
     * Save an entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    <S extends HelloEntity> S save(S entity);
    /**
     * Finds all entities
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    List<HelloEntity> findAll();
    /**
     * Deletes all entities
     */
    @DELETE
    void deleteAll();
}
