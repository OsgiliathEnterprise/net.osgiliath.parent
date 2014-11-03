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

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;
import net.osgiliath.messaging.repository.HelloRepository;

import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 * @author charliemordant JMS repository implementation for itests purpose
 */
@Slf4j
public class HelloJMSRepository implements HelloRepository, MessageListener {
	/**
	 * JMS producer
	 */
	// @Produce(uri = "jms:queue:helloServiceQueueOut")
	@Setter
	private JmsOperations producer;
	
	
	/**
	 * instances registry
	 */
	private List<HelloEntity> entities = new ArrayList<HelloEntity>();

	/**
	 * finds entities by message
	 */
	@Override
	public final Collection<? extends HelloEntity> findByHelloObjectMessage(
			/*@Body*/ final String message_p) {
		final List<HelloEntity> ret = new ArrayList<HelloEntity>();
		for (HelloEntity ent : this.entities) {
			if (ent.getHelloMessage().equals(message_p))
				ret.add(ent);
		}
		return ret;
	}

	/**
	 * Consumer method for instance save
	 */
	// @Override
	// @Consume(uri = "jms:queue:helloServiceQueueIn")
	public final <S extends HelloEntity> void save(/* @Body */final S entity) {
		this.entities.add(entity);
		this.producer.send("HELLO.OUT", new MessageCreator() {

			@Override
			public Message createMessage(Session arg0) throws JMSException {
				log.info("returning persisted message");
				return arg0.createObjectMessage(findAll());
			}
		});

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

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			HelloEntity entity = (HelloEntity) objectMessage.getObject();
			log.info("received message for persistance");
			save(entity);

		} catch (JMSException e) {
			log.error("error parsing jms message", e);
		}

	}

}
