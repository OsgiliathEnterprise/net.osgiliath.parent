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

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * TODO JMS sample of Hello service exports
 * 
 * @author charliemordant
 * 
 */
@Slf4j
public class HelloServiceJMS implements HelloService {
    @Setter
    private HelloObjectRepository helloObjectRepository;
    @Setter
    private Validator validator;
    @Setter
    @Produce(uri = "jms:queue:helloServiceQueueOut")
    private ProducerTemplate producer;

    @Override
    @Consume(uri = "jms:queue:helloServiceQueueIn")
    public void persistHello(@Body HelloEntity helloObject_p) {
	log.error("****************** Save on JMS Service **********************");
	log.info("persisting new message with jms: "
		+ helloObject_p.getHelloMessage());
	Set<ConstraintViolation<HelloEntity>> validationResults = validator
		.validate(helloObject_p);
	StringBuilder errors = new StringBuilder("");

	if (!validationResults.isEmpty()) {
	    for (ConstraintViolation<HelloEntity> violation : validationResults) {
		log.info("subscription error, validating user:"
			+ violation.getMessage());
		errors.append(violation.getPropertyPath()).append(": ")
			.append(violation.getMessage().replaceAll("\"", ""))
			.append(";").append(System.lineSeparator());
	    }
	    throw new ValidationException(errors.toString());
	}
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
}
