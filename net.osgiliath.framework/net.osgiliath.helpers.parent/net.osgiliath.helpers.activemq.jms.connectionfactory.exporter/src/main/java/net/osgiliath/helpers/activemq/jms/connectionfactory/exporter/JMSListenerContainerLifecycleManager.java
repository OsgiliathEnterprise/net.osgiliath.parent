package net.osgiliath.helpers.activemq.jms.connectionfactory.exporter;

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

import org.springframework.jms.listener.AbstractMessageListenerContainer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JMSListenerContainerLifecycleManager {
	@Setter
	private AbstractMessageListenerContainer listenerContainer;

	public void start() {
		log.info("Starting instance: " + this.hashCode()
				+ " with destination: ["
				+ listenerContainer.getDestinationName() + "] msgListener: ["
				+ listenerContainer.getMessageListener() + "]");

		listenerContainer.start();
	}

	public boolean terminated() {
		return !listenerContainer.isRunning();
	}

	public void stop() {
		log.info("Stopping instance: " + this.hashCode());
		listenerContainer.stop();
		listenerContainer.destroy();
	}
}
