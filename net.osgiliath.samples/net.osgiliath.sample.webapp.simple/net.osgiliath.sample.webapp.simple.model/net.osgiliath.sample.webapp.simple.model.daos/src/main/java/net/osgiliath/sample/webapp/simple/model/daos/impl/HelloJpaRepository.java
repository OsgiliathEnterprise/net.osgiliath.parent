package net.osgiliath.sample.webapp.simple.model.daos.impl;

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

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import net.osgiliath.module.spring.data.jpa.DelegatingSimpleJpaRepository;
import net.osgiliath.sample.webapp.simple.model.daos.HelloRepository;
import net.osgiliath.sample.webapp.simple.model.entities.HelloEntity;
import net.osgiliath.sample.webapp.simple.model.entities.HelloEntity_;

/**
 * Spring data jpa repository declaration.
 * 
 * @author charliemordant
 */
@OsgiServiceProvider(classes = { HelloRepository.class })
@Singleton
@Transactional
public class HelloJpaRepository extends DelegatingSimpleJpaRepository<HelloEntity, Long> implements HelloRepository {
	/**
	 * Entity manager.
	 */

	@PersistenceContext(unitName = "myTestPu")
	private EntityManager em;
	@PersistenceUnit( unitName = "myTestPu")
	private EntityManagerFactory emf;

	/**
	 * Finds by helloMessage.
	 * 
	 * @param message
	 *            the message to find elements from
	 * @return all corresponding entities
	 */
	@Override
	public final Collection<? extends HelloEntity> findByHelloObjectMessage(final String message) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<HelloEntity> criteria = builder.createQuery(HelloEntity.class);
		final Root<HelloEntity> helloObject = criteria.from(HelloEntity.class);
		criteria.select(helloObject);
		final Predicate where = builder.equal(helloObject.get(HelloEntity_.helloMessage), message);
		criteria.where(where);
		final TypedQuery<HelloEntity> query = this.em.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	@PostConstruct
	public void postConstruct() {
		instanciateDelegateRepository(emf, em, HelloEntity.class);
	}

	@Override
	public List<HelloEntity> findAll() {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<HelloEntity> criteria = builder.createQuery(HelloEntity.class);
		final Root<HelloEntity> helloObject = criteria.from(HelloEntity.class);
		criteria.select(helloObject);
		final TypedQuery<HelloEntity> query = this.em.createQuery(criteria);
		return query.getResultList();
	}

}
