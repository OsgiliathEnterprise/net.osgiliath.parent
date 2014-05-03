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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.osgiliath.helpers.cdi.eager.Eager;
import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;
import net.osgiliath.messaging.repository.HelloCDIRepository;

import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO Spring data jpa repository declaration

@ApplicationScoped
@Eager
@ContextName
public class HelloJMSCDIRepository implements HelloCDIRepository {
    private Logger LOG = LoggerFactory.getLogger(HelloJMSCDIRepository.class);
    private List<HelloEntity> entities = new ArrayList<HelloEntity>();

    @Inject
    @Uri("jms:queue:helloServiceQueueOut")
    private ProducerTemplate producer;
    @Inject
    @Uri("jms:queue:helloServiceQueueIn2")
    private ProducerTemplate internalProducer;
    @Inject
    @Uri("jms:queue:helloServiceQueueIn")
    private ProducerTemplate routeProducer;
    private boolean isProcessed = false;

    public <S extends HelloEntity> void directSave(@Body S entity) {
	internalProducer.sendBody(entity);
	routeProducer.sendBody(entity);

    }

    @Consume(uri = "jms:queue:helloServiceQueueIn2")
    public <S extends HelloEntity> void save(@Body S entity) {
	LOG.info("receiving message on internally (via @Consumer");
	entities.add(entity);
	Hellos hellos = new Hellos();
	Collection<HelloEntity> entities = new ArrayList<>();
	entities.add(entity);
	hellos.setEntities(entities);
	isProcessed = true;
	LOG.info("Sending hellos: " + hellos);
	producer.sendBody(hellos);
    }

    @Override
    public boolean isProcessed() {

	return isProcessed;
    }

}
