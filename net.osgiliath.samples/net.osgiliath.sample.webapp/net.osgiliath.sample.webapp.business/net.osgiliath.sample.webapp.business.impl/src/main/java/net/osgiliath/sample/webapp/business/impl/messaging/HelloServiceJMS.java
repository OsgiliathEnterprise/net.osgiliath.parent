package net.osgiliath.sample.webapp.business.impl.messaging;

/*
 * #%L
 * Helloworld sample business module
 * %%
 * Copyright (C) 2013 - 2016 Osgiliath
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.sample.webapp.business.spi.HelloService;
import net.osgiliath.sample.webapp.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.model.daos.HelloRepository;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.ops4j.pax.cdi.api.OsgiService;

/**
 * JMS sample of Hello service exports.
 * 
 * @author charliemordant
 * 
 */
@Slf4j
@ApplicationScoped
@ContextName
public class HelloServiceJMS extends RouteBuilder implements HelloService {
	private final transient DataFormat helloObjectJSonFormat = new JacksonDataFormat(
		      HelloEntity.class, Hellos.class);
	/**
	 * The repository.
	 */
	@Inject
	@OsgiService
	private transient HelloRepository helloObjectRepository;
	/**
	 * JMS producer.
	 */
	@Inject
	@Uri("jms:topic:helloServiceQueueOut")
	private transient ProducerTemplate producer;

	/**
	 * saves element
	 * 
	 * @param helloObject
	 *            element to save
	 */
	@Override
	public void persistHello(@NotNull @Valid HelloEntity helloObject) {
		log.info("****************** Save on JMS Service **********************");
		log.info("persisting new message with jms: " + helloObject.getHelloMessage());
		this.helloObjectRepository.save(helloObject);
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.producer.sendBody( mapper.writeValueAsString(getHellos()));
		} catch (CamelExecutionException | JsonProcessingException e) {
			log.error("exception marshalling messages", e);
			
		}
	}

	/**
	 * Returns all elements.
	 * 
	 * @return all elements
	 */
	@Override
	public Hellos getHellos() {

		final Collection<HelloEntity> helloObjects = this.helloObjectRepository.findAll();
		if (helloObjects.isEmpty()) {
			throw new UnsupportedOperationException("You could not call this method when the list is empty");
		}
		return Hellos.builder()
				.helloCollection(helloObjects.stream().map(x -> x.getHelloMessage()).collect(Collectors.toList())).build();
	}

	
	/**
	 * Deletes all elements.
	 */
	@Override
	public void deleteHellos() {
		this.helloObjectRepository.deleteAll();

	}

	/**
	 * receives JMS message.
	 * 
	 * @throws Exception
	 *             Persistence error
	 */
	@Override
	public void configure() throws Exception {
		from("properties:{{helloApp.inQueueJMS}}"/*"jms:queue:helloServiceQueueIn"*/)
		.log(LoggingLevel.INFO, "received jms message: ${body}").unmarshal(this.helloObjectJSonFormat).process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				HelloServiceJMS.this.persistHello((HelloEntity) exchange.getIn().getBody());
			}

		});

	}

}
