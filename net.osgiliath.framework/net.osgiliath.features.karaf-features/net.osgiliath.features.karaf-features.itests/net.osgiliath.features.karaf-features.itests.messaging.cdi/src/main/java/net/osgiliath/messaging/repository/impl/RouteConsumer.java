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

import java.util.ArrayList;
import java.util.Collection;

import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.Hellos;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.properties.PropertiesComponent;
import org.ops4j.pax.cdi.api.OsgiService;
@ContextName
public class RouteConsumer extends RouteBuilder{
	@Override
	public void configure() throws Exception {
		from("{{messaging.routequeuein}}").log(LoggingLevel.INFO, "received JMS message on the queue").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				 Hellos hellos = new Hellos();
				 HelloEntity entity = exchange.getIn().getBody(HelloEntity.class);
				 Collection<HelloEntity> entities = new ArrayList<>();
				 entities.add(entity);
				 hellos.setEntities(entities);
				 exchange.getOut().setBody(hellos);
				
			}
		}).to("jms:queue:helloServiceQueueOut");
		
	}
	
}
