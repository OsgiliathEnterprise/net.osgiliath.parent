package net.osgiliath.feature.itest.persistence.daos;

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

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.osgiliath.feature.itest.persistence.entities.HelloEntity;

/**
 * JPA accessible interface by business or route module
 *         (see business module for JMS or REST export, don't forget the
 *         osgi.bnd cxf package export).
 * @author charliemordant 
 */

public interface HelloRepository extends JpaRepository<HelloEntity, Long>
{
  /**
   * Finds by message.
   * 
   * @param message
   *          message
   * @return corresponding entities
   */
  Collection<? extends HelloEntity> findByHelloObjectMessage(String message);

  
}
