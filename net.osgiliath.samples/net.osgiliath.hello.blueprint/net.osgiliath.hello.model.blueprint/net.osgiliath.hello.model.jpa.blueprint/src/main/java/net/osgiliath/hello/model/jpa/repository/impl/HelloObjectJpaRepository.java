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
import net.osgiliath.hello.model.jpa.model.HelloObject;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//TODO Spring data jpa repository declaration
public class HelloObjectJpaRepository extends SimpleJpaRepository<HelloObject, Long> implements HelloObjectRepository {
	@Setter
	private EntityManager entityManager;
	public HelloObjectJpaRepository(Class<HelloObject> domainClass,
			EntityManager em) {
		super(domainClass, em);
		setEntityManager(em);
	}
	@Override
	public Collection<? extends HelloObject> findByHelloObjectMessage(String message_p) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<HelloObject> cq = cb.createQuery(HelloObject.class);
		Root<HelloObject> helloObject = cq.from(HelloObject.class);
		cq.select(helloObject);
		Predicate where = cb.equal(helloObject.get("message"), message_p);
		cq.where(where);
		TypedQuery<HelloObject> q = entityManager.createQuery(cq);
		List<HelloObject> result = q.getResultList();
		return result;
	}
	@Override
	public <S extends HelloObject> S save(S entity) {
		return super.save(entity);
	}

	@Override
	public List<HelloObject> findAll() {
		return super.findAll();
	}
	@Override
	public void deleteAll() {
		super.deleteAll();
	}

}
