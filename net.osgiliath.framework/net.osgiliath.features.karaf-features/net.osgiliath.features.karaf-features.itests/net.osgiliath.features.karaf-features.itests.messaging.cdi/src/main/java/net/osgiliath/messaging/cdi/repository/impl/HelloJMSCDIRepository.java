package net.osgiliath.messaging.cdi.repository.impl;

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
import net.osgiliath.messaging.cdi.HelloEntity;
import net.osgiliath.messaging.cdi.Hellos;
import net.osgiliath.messaging.cdi.repository.HelloCDIRepository;

import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JMS Service implementation.
 * @author charliemordant
 */
@ApplicationScoped
@Eager
@ContextName
public class HelloJMSCDIRepository implements HelloCDIRepository {
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory
      .getLogger(HelloJMSCDIRepository.class);
  /**
   * entities registry.
   */
  private final transient List<HelloEntity> entities = new ArrayList<HelloEntity>();
  /**
   * message producer for integration test.
   */
  @Inject
  @Uri("jms:queue:helloServiceQueueOut")
  private transient ProducerTemplate producer;
  /**
   * message producer for internal consuming.
   */
  @Inject
  @Uri("jms:queue:helloServiceQueueIn2")
  private transient ProducerTemplate internalProducer;
  /**
   * Producer for the route.
   */
  @Inject
  @Uri("jms:queue:helloServiceQueueIn")
  private transient ProducerTemplate routeProducer;

  /**
   * Saves entities.
   * @param entity element to save
   * @return the saved element
   */
  public <S extends HelloEntity> void directSave(@Body S entity) {
    this.internalProducer.sendBody(entity);
    this.routeProducer.sendBody(entity);

  }

  /**
   * Internal consumer (not working for now).
   * 
   * @param entity
   *          to consume
   * @return the persisted element
   */
  @Consume(uri = "jms:queue:helloServiceQueueIn2")
  public <S extends HelloEntity> void save(@Body S entity) {
    LOG.info("receiving message on internally (via @Consumer");
    this.entities.add(entity);
    final Hellos hellos = new Hellos();
    final Collection<HelloEntity> retEntities = new ArrayList<>();
    retEntities.add(entity);
    hellos.setEntities(retEntities);
    LOG.info("Sending hellos: " + hellos);
    this.producer.sendBody(hellos);
  }

}
