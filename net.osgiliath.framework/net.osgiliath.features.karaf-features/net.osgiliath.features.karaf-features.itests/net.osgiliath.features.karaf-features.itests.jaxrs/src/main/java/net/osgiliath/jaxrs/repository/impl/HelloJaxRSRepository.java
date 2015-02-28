package net.osgiliath.jaxrs.repository.impl;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.osgiliath.jaxrs.HelloEntity;
import net.osgiliath.jaxrs.Hellos;
import net.osgiliath.jaxrs.repository.HelloRepository;

/**
 * Simple Jaxrs Repository
 * @author charliemordant Implementation of the REST service
 */
public class HelloJaxRSRepository implements HelloRepository {
  /**
   * Model element list.
   */
  private final List<HelloEntity> entities = new ArrayList<HelloEntity>();

  /**
   * find entities corrsponding to message
   */
  @Override
  public final Collection<? extends HelloEntity> findByHelloObjectMessage(
      final String message) {
    final List<HelloEntity> ret = new ArrayList<HelloEntity>();
    for (final HelloEntity ent : this.entities) {
      if (ent.getHelloMessage().equals(message)) {
        ret.add(ent);
      }
    }
    return ret;
  }

  /**
   * Save an entity
   */
  @Override
  public final <S extends HelloEntity> S save(final S entity) {
    this.entities.add(entity);
    return entity;
  }

  /**
   * Finds all entities
   */
  @Override
  public final Hellos findAll() {
    return Hellos.builder().helloCollection(this.entities).build();
  }

  /**
   * Deletes all entities
   */
  @Override
  public void deleteAll() {
    this.entities.clear();
  }

}
