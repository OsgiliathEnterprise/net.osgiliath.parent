package net.osgiliath.messaging.repository;

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

import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;

/**
 * JMS repository definition.
 * @author charliemordant
 */
public interface HelloRepository {
  /**
   * finds entity by message
   * 
   * @param message
   *          the message
   * @return corrsponding entities
   */
  Collection<? extends HelloEntity> findByHelloObjectMessage(String message);

  /**
   * Entity persistence
   * 
   * @param entity
   *          to save
   * @return the saved elements
   */
  <S extends HelloEntity> void save(S entity);

  /**
   * gets all entities
   * 
   * @return all entities
   */
  Hellos findAll();

  /**
   * deletes all entities
   */
  void deleteAll();
}
