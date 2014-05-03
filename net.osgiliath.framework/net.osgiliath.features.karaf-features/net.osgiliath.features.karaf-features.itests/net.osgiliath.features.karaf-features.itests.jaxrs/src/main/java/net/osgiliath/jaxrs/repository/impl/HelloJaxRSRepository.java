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
import net.osgiliath.jaxrs.repository.HelloRepository;

//TODO Spring data jpa repository declaration
public class HelloJaxRSRepository implements HelloRepository {

    private List<HelloEntity> entities = new ArrayList<HelloEntity>();

    @Override
    public Collection<? extends HelloEntity> findByHelloObjectMessage(
	    String message_p) {
	List<HelloEntity> ret = new ArrayList<HelloEntity>();
	for (HelloEntity ent : entities) {
	    if (ent.getHelloMessage().equals(message_p))
		ret.add(ent);

	}
	return ret;
    }

    @Override
    public <S extends HelloEntity> S save(S entity) {
	entities.add(entity);
	return entity;
    }

    @Override
    public List<HelloEntity> findAll() {
	return entities;
    }

    @Override
    public void deleteAll() {
	entities.clear();
    }

}
