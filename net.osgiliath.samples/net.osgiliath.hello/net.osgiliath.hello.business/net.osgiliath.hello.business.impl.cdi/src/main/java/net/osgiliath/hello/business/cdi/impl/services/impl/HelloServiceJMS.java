package net.osgiliath.hello.business.cdi.impl.services.impl;

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

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;
import org.ops4j.pax.cdi.api.OsgiService;

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
@ContextName
public class HelloServiceJMS extends RouteBuilder implements HelloService {
    @Inject
    @OsgiService
    private HelloObjectRepository helloObjectRepository;
    @Inject
    @Uri("jms:queue:helloServiceQueueOut")
    private ProducerTemplate producer;

    @Override
    public void persistHello(@NotNull @Valid HelloEntity helloObject_p) {
	log.info("****************** Save on JMS Service **********************");
	log.info("persisting new message with jms: "
		+ helloObject_p.getHelloMessage());
	helloObjectRepository.save(helloObject_p);
	producer.sendBody(getHellos());
    }

    @Override
    public Hellos getHellos() {

	Collection<HelloEntity> helloObjects = helloObjectRepository.findAll();
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

    private Function<HelloEntity, String> helloObjectToStringFunction = new Function<HelloEntity, String>() {

	@Override
	public String apply(HelloEntity arg0) {
	    return arg0.getHelloMessage();
	}
    };

    @Override
    public void deleteAll() {
	helloObjectRepository.deleteAll();

    }

    @Override
    public void configure() throws Exception {
	from("jms:queue:helloServiceQueueIn").process(new Processor() {

	    @Override
	    public void process(Exchange exchange) throws Exception {
		persistHello((HelloEntity) exchange.getIn().getBody());

	    }

	});

    }
}
