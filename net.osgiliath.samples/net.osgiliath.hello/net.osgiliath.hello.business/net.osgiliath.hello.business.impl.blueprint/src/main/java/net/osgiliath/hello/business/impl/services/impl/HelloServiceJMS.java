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
import javax.validation.Validator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

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
  private transient HelloObjectRepository helloObjectRepository;
  /**
   * Hibernate validator
   */
  @Setter
  private transient Validator validator;
  /**
   * JMS producer
   */
  @Setter
  private transient JmsOperations template;

  /**
   * JMS consumer
   * 
   * @throws JMSException
   */
  @Override
  public void persistHello(HelloEntity hello) {

    log
        .error("****************** Save on JMS Service **********************");
    log
        .info("persisting new message with jms: " + hello.getHelloMessage());
    final Set<ConstraintViolation<HelloEntity>> validationResults = this.validator
        .validate(hello);
    final StringBuilder errors = new StringBuilder("");

    if (!validationResults.isEmpty()) {
      for (final ConstraintViolation<HelloEntity> violation : validationResults) {
        log.info("subscription error, validating user:"
            + violation.getMessage());
        errors.append(violation.getPropertyPath()).append(": ")
            .append(violation.getMessage().replaceAll("\"", "")).append(';')
            .append(System.lineSeparator());
      }
      this.template.send("helloServiceQueueErrors", new MessageCreator() {
        public Message createMessage(final Session session) throws JMSException {
          return session.createTextMessage(errors.toString());
        }
      });
    }
    this.helloObjectRepository.save(hello);
    this.template.send("helloServiceQueueOut", new MessageCreator() {
      public Message createMessage(final Session session) throws JMSException {
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
  private transient Function<HelloEntity, String> helloObjectToStringFunction = new Function<HelloEntity, String>() {

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
  /**
   * Method called on message reception
   * @param message received message
   */
  @Override
  public void onMessage(Message message) {
    try {
      final HelloEntity helloObject_p = (HelloEntity) ((ObjectMessage) message)
          .getObject();
      persistHello(helloObject_p);
    }
    catch (JMSException e) {
      log.error("error receiving JMS message", e);
    }

  }
}
