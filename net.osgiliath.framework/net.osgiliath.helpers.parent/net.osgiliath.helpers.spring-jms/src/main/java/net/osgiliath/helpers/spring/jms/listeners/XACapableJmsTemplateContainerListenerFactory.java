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

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;

import lombok.Setter;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;


public class XACapableJmsTemplateContainerListenerFactory implements JmsTemplateContainerListenerFactory {
	@Setter
	private PlatformTransactionManager txManager;
	@Setter
	private ConnectionFactory nonXAFactory;
	@Setter
	private ConnectionFactory xAFactory;
	@Setter
	private int concurrentConsumers;
	@Setter
	private int receiveTimeout;
	
	
	/* (non-Javadoc)
	 * @see net.osgiliath.helpers.spring.jms.listeners.IJmsTemplateContainerListenerFactory#create(boolean, java.lang.String, javax.jms.MessageListener)
	 */
	@Override
	public DefaultMessageListenerContainer create(boolean transacted,
			String destinationName, MessageListener messageListener) {
		return create(transacted, destinationName, messageListener, false);

	}

	/* (non-Javadoc)
	 * @see net.osgiliath.helpers.spring.jms.listeners.IJmsTemplateContainerListenerFactory#create(boolean, java.lang.String, javax.jms.MessageListener, boolean)
	 */
	@Override
	public DefaultMessageListenerContainer create(boolean transacted,
			String destinationName, MessageListener messageListener,
			boolean isPubSub) {
		ManageableDefaultJmsContainerListener container = new ManageableDefaultJmsContainerListener();
		container.setCacheLevel(DefaultMessageListenerContainer.CACHE_AUTO);
		container.setConcurrentConsumers(concurrentConsumers);
		container.setDestinationName(destinationName);
		container.setMessageListener(messageListener);
		container.setReceiveTimeout(receiveTimeout);
		container.setPubSubDomain(isPubSub);
		
		if (transacted) {
			addTransactedInfos(container);
		} else {
			addNonTransactedInfo(container);
			container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		}
		container.initialize();
		container.start();
		return container;
	}

	private void addNonTransactedInfo(DefaultMessageListenerContainer container) {
		container.setConnectionFactory(nonXAFactory);

	}

	private void addTransactedInfos(DefaultMessageListenerContainer container) {
		container.setTransactionManager(txManager);
		container.setConnectionFactory(xAFactory);
		container.setTransactionTimeout(receiveTimeout);
	}

}
