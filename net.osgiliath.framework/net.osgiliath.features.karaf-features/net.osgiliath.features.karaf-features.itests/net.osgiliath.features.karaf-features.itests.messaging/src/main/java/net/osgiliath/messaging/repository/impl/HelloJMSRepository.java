package net.osgiliath.messaging.repository.impl;

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

import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;
import net.osgiliath.messaging.repository.HelloRepository;

import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

//TODO Spring data jpa repository declaration
public class HelloJMSRepository implements HelloRepository {
    @Produce(uri = "jms:queue:helloServiceQueueOut")
    private ProducerTemplate producer;
    private List<HelloEntity> entities = new ArrayList<HelloEntity>();

    @Override
    public Collection<? extends HelloEntity> findByHelloObjectMessage(
	    @Body String message_p) {
	List<HelloEntity> ret = new ArrayList<HelloEntity>();
	for (HelloEntity ent : entities) {
	    if (ent.getHelloMessage().equals(message_p))
		ret.add(ent);

	}
	return ret;
    }

    @Override
    @Consume(uri = "jms:queue:helloServiceQueueIn")
    public <S extends HelloEntity> void save(@Body S entity) {
	entities.add(entity);
	producer.sendBody(findAll());

    }

    @Override
    public Hellos findAll() {
	Hellos hellos = new Hellos();
	hellos.setEntities(entities);
	return hellos;
    }

    @Override
    public void deleteAll() {
	entities.clear();
    }

}
