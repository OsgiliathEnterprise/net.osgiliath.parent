package net.osgiliath.hello.business.spi.services;

/*
 * #%L
 * net.osgiliath.hello.business.spi
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;

/**
 * Business service interface declaration.
 * 
 * @author charliemordant
 * 
 */

public interface HelloService {
  /**
   * Saves an entity
   * 
   * @param helloMessage
   *          entity to save
   */
  void persistHello(@NotNull @Valid HelloEntity helloMessage);

  /**
   * get entities
   * 
   * @return entities
   */
  Hellos getHellos();

  /**
   * Delete entities
   */
  void deleteAll();

}
