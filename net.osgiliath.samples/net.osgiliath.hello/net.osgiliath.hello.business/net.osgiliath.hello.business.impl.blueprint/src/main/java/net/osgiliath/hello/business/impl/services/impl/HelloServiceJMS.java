package net.osgiliath.hello.business.impl.services.impl;

/*
 * #%L
 * net.osgiliath.hello.business.impl
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
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * JMS sample of Hello service exports
 * 
 * @author charliemordant
 * 
 */
@Slf4j
public class HelloServiceJMS implements HelloService, MessageListener {
	/**
	 * Database persistence repository
	 */
	@Setter
	private HelloObjectRepository helloObjectRepository;
	/**
	 * Hibernate validator
	 */
	@Setter
	private Validator validator;
	/**
	 * JMS producer
	 */
	@Setter
	private JmsOperations template;

	/**
	 * JMS consumer
	 * 
	 * @throws JMSException
	 */
	@Override
	public void persistHello(HelloEntity hello) {

		
			this.log.error("****************** Save on JMS Service **********************");
			this.log.info("persisting new message with jms: "
					+ hello.getHelloMessage());
			final Set<ConstraintViolation<HelloEntity>> validationResults = validator
					.validate(hello);
			final StringBuilder errors = new StringBuilder("");

			if (!validationResults.isEmpty()) {
				for (ConstraintViolation<HelloEntity> violation : validationResults) {
					this.log.info("subscription error, validating user:"
							+ violation.getMessage());
					errors.append(violation.getPropertyPath())
							.append(": ")
							.append(violation.getMessage().replaceAll("\"", ""))
							.append(";").append(System.lineSeparator());
				}
				this.template.send("helloServiceQueueOut", new MessageCreator() {
					public Message createMessage(final Session session)
							throws JMSException {
						return session.createTextMessage(errors.toString());
					}
				});
			}
			this.helloObjectRepository.save(hello);
			this.template.send("helloServiceQueueOut", new MessageCreator() {
				public Message createMessage(final Session session)
						throws JMSException {
					return session.createObjectMessage(getHellos());
				}
			});
		
	}

	/**
	 * Returns all entities
	 */
	@Override
	public Hellos getHellos() {

		final Collection<HelloEntity> helloObjects = this.helloObjectRepository
				.findAll();
		if (helloObjects.isEmpty()) {
			throw new UnsupportedOperationException(
					"You could not call this method when the list is empty");
		}
		return Hellos
				.builder()
				.helloCollection(
						Lists.newArrayList(Iterables.transform(helloObjects,
								helloObjectToStringFunction))).build();
	}

	/**
	 * Function that transforms entities to string
	 */
	private Function<HelloEntity, String> helloObjectToStringFunction = new Function<HelloEntity, String>() {

		@Override
		public String apply(HelloEntity entity) {
			return entity.getHelloMessage();
		}
	};

	/**
	 * Deletes all entities
	 */
	@Override
	public void deleteAll() {
		this.helloObjectRepository.deleteAll();

	}

	@Override
	public void onMessage(Message message) {
		try {
			HelloEntity helloObject_p = (HelloEntity) ((ObjectMessage) message).getObject();
			persistHello(helloObject_p);
		} catch (JMSException e) {
			log.error("error receiving JMS message", e);
		}

		
	}
}
