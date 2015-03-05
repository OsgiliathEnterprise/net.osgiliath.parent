package net.osgiliath.hello.model.jpa.repository.impl;

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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.Setter;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.model.HelloEntity_;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Spring data jpa repository declaration.
 * @author charliemordant
 */
public class HelloObjectJpaRepository extends
    SimpleJpaRepository<HelloEntity, Long> implements HelloObjectRepository {
  /**
   * Blueprint injected entityManager.
   */
  @Setter
  private transient EntityManager entityManager;

  /**
   * Ctor.
   * 
   * @param domainClass
   *          clazz
   * @param em
   *          entity Manager
   */
  public HelloObjectJpaRepository(Class<HelloEntity> domainClass,
      EntityManager entityManager) {
    super(domainClass, entityManager);
    setEntityManager(entityManager);
  }

  /**
   * Query to find elements by message.
   * @param message message to find element from
   * @return all corresponding entities
   * 
   */
  @Override
  public Collection<? extends HelloEntity> findByHelloObjectMessage(
      final String message) {
    final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
    final CriteriaQuery<HelloEntity> query = builder.createQuery(HelloEntity.class);
    final Root<HelloEntity> helloObject = query.from(HelloEntity.class);
    query.select(helloObject);
    final Predicate where = builder.equal(
        helloObject.get(HelloEntity_.helloMessage), message);
    query.where(where);
    final TypedQuery<HelloEntity> instanciatedQuery = this.entityManager.createQuery(query);
    return instanciatedQuery.getResultList();
  }

  /**
   * Saves an entity.
   * @param entity element to save
   * @return the persisted entity
   */
  @Override
  public <S extends HelloEntity> S save(S entity) {
    return super.save(entity);
  }

  /**
   * gets all entities.
   * @return all entities
   */
  @Override
  public List<HelloEntity> findAll() {
    return super.findAll();
  }

  /**
   * deletes all entities.
   */
  @Override
  public void deleteAll() {
    super.deleteAll();
  }

}
