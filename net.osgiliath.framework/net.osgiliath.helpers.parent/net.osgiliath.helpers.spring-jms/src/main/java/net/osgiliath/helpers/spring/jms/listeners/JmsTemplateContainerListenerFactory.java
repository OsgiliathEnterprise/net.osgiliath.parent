package net.osgiliath.helpers.spring.jms.listeners;

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

import javax.jms.MessageListener;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * JMS container listener.
 * 
 * @author charliemordant
 *
 */
public interface JmsTemplateContainerListenerFactory {
  /**
   * Creates a Listener container.
   * 
   * @param transacted
   *          if though should have transactions
   * @param destinationName
   *          destination
   * @param messageListener
   *          the listener
   * @return the listener
   */
  DefaultMessageListenerContainer create(boolean transacted,
      String destinationName, MessageListener messageListener);

  /**
   * Creates a Listener container.
   * 
   * @param transacted
   *          if though should have transactions
   * @param destinationName
   *          destination
   * @param messageListener
   *          the listener
   * @param isPubSub
   *          publish/subscribe vs queue
   * @return the listener
   */

  DefaultMessageListenerContainer create(boolean transacted,
      String destinationName, MessageListener messageListener, boolean isPubSub);

}