package net.osgiliath.jpa.cdi.repository.impl;

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
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.jpa.cdi.entities.HelloEntity;
import net.osgiliath.jpa.cdi.repository.HelloRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 * @author charliemordant JPA implementation with CDI injection
 */

@OsgiServiceProvider
@Slf4j
public class HelloRepositoryImpl implements HelloRepository {

  /**
   * Entity manager
   */
  @Inject
  private EntityManager entityManager;
//  @Inject
//  @OsgiService
//  private UserTransaction userTransaction;

  /**
   * save method
   */
  @Transactional
  public HelloEntity save(final HelloEntity entity) {
//    log.info("Persisting hello with message: " + entity.getHelloMessage());
//    try {
//      userTransaction.begin();
//      entityManager.joinTransaction();
      entityManager.persist(entity);
      entityManager.flush();
//      userTransaction.commit();
//    }
//    catch (NotSupportedException | SystemException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
//     log.error("Error persisting the data", e);
//    }
    return entity;
  }

  /**
   * gets all
   */
  public Collection<HelloEntity> getAll() {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<HelloEntity> cq = cb.createQuery(HelloEntity.class);
    final Root<HelloEntity> helloObject = cq.from(HelloEntity.class);
    cq.select(helloObject);

    final TypedQuery<HelloEntity> q = entityManager.createQuery(cq);
    final List<HelloEntity> result = q.getResultList();
    log.info("Returning : " + result.size() + " hellomessages");
    return result;

  }

  /**
   * deletes all
   */
  public void deleteAll() {
    for (HelloEntity entity : getAll()) {
      entityManager.remove(entity);
    }
  }
}
