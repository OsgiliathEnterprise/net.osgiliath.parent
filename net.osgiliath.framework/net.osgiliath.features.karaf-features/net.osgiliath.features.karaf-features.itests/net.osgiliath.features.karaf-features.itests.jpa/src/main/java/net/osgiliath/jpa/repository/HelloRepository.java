package net.osgiliath.jpa.repository;

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

import net.osgiliath.jpa.model.HelloEntity;

/**
 * JPA accessible interface by business or route module
 *         (see business module for JMS or REST export, don't forget the
 *         osgi.bnd cxf package export).
 * @author charliemordant 
 */
public interface HelloRepository {
  /**
   * Finds by message.
   * 
   * @param message
   *          message
   * @return corresponding entities
   */
  Collection<? extends HelloEntity> findByHelloObjectMessage(String message);

  /**
   * Saves.
   * 
   * @param entity
   *          the entity
   * @return saved entity
   */
  <S extends HelloEntity> S save(S entity);

  /**
   * Finds all entities.
   * @return all entities
   */
  List<HelloEntity> findAll();

  /**
   * deletes all.
   */
  void deleteAll();
}
