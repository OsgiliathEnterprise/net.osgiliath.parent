package net.osgiliath.helper.spring.jms.listeners;

/*
 * #%L
 * Connection factory
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
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

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;

import lombok.Setter;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;
/**
 * Listener container implementation.
 * @author charliemordant
 *
 */
public class XACapableJmsTemplateContainerListenerFactory implements
    JmsTemplateContainerListenerFactory {
  /**
   * Transaction manager.
   */
  @Setter
  private transient PlatformTransactionManager txManager;
  /**
   * No XA JMS connection factory.
   */
  @Setter
  private transient ConnectionFactory nonXAFactory;
  /**
   * XA JMS connection factory.
   */
  @Setter
  private transient ConnectionFactory xAFactory;
  /**
   * Number of concurrent consumers.
   */
  @Setter
  private transient int concurrentConsumers;
  /**
   * Timeout.
   */
  @Setter
  private transient int receiveTimeout;

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.osgiliath.helpers.spring.jms.listeners.IJmsTemplateContainerListenerFactory
   * #create(boolean, java.lang.String, javax.jms.MessageListener)
   */
  @Override
  public DefaultMessageListenerContainer create(boolean transacted,
      String destinationName, MessageListener messageListener) {
    return this.create(transacted, destinationName, messageListener, false);

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.osgiliath.helpers.spring.jms.listeners.IJmsTemplateContainerListenerFactory
   * #create(boolean, java.lang.String, javax.jms.MessageListener, boolean)
   */
  @Override
  public DefaultMessageListenerContainer create(boolean transacted,
      String destinationName, MessageListener messageListener, boolean isPubSub) {
    final ManageableDefaultJmsContainerListener container = new ManageableDefaultJmsContainerListener();
    container.setCacheLevel(DefaultMessageListenerContainer.CACHE_AUTO);
    container.setConcurrentConsumers(this.concurrentConsumers);
    container.setDestinationName(destinationName);
    container.setMessageListener(messageListener);
    container.setReceiveTimeout(this.receiveTimeout);
    container.setPubSubDomain(isPubSub);

    if (transacted) {
      addTransactedInfos(container);
    }
    else {
      addNonTransactedInfo(container);
      container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    }
    container.initialize();
    container.start();
    return container;
  }
  /**
   * Adds info for non transacted connection.
   * @param container container to provision
   */
  private void addNonTransactedInfo(DefaultMessageListenerContainer container) {
    container.setConnectionFactory(this.nonXAFactory);

  }
  /**
   * Adds info for transacted connection.
   * @param container container to provision
   */
  private void addTransactedInfos(DefaultMessageListenerContainer container) {
    container.setTransactionManager(this.txManager);
    container.setConnectionFactory(this.xAFactory);
    container.setTransactionTimeout(this.receiveTimeout);
  }

}
