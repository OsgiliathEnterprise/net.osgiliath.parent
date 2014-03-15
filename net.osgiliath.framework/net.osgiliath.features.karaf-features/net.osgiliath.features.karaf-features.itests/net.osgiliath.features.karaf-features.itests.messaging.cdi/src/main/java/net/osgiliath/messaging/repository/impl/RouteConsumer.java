package net.osgiliath.messaging.repository.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.messaging.cdi
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
@ApplicationScoped
@ContextName
public class RouteConsumer extends RouteBuilder{
	private boolean processed = false;
	@Override
	public void configure() throws Exception {
		from("jms:queue:helloServiceQueueIn").log(LoggingLevel.INFO, "received JMS message on the queue").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				processed = true;
				
			}
		});
		
	}
	public boolean isDelivered() {
		return processed;
	}
}
