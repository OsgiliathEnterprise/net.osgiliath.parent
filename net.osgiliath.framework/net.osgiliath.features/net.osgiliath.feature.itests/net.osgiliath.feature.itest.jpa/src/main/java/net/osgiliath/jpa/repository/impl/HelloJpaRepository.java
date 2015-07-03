package net.osgiliath.jpa.repository.impl;

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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.Setter;
import net.osgiliath.jpa.model.HelloEntity;
import net.osgiliath.jpa.model.HelloEntity_;
import net.osgiliath.jpa.repository.HelloRepository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Spring data jpa repository declaration.
 * 
 * @author charliemordant
 */
public class HelloJpaRepository extends SimpleJpaRepository<HelloEntity, Long>
    implements HelloRepository {
  /**
   * Entity manager.
   */
  @Setter
  private transient EntityManager entityManager;

  /**
   * Ctor.
   * 
   * @param domainClass
   *          class
   * @param em
   *          entity manager
   */
  public HelloJpaRepository(final Class<HelloEntity> domainClass,
      final EntityManager entityManager) {
    super(domainClass, entityManager);
    setEntityManager(entityManager);
  }

  /**
   * Finds by helloMessage.
   * 
   * @param message
   *          the message to find elements from
   * @return all corresponding entities
   */
  @Override
  public final Collection<? extends HelloEntity> findByHelloObjectMessage(
      final String message) {
    final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
    final CriteriaQuery<HelloEntity> criteria = builder
        .createQuery(HelloEntity.class);
    final Root<HelloEntity> helloObject = criteria.from(HelloEntity.class);
    criteria.select(helloObject);
    final Predicate where = builder.equal(
        helloObject.get(HelloEntity_.helloMessage), message);
    criteria.where(where);
    final TypedQuery<HelloEntity> query = this.entityManager
        .createQuery(criteria);
    return query.getResultList();
  }

}
