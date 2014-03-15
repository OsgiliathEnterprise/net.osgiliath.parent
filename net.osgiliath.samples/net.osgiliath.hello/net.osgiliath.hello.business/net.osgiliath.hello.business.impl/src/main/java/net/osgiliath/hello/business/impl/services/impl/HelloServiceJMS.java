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

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloObject;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;
import net.osgiliath.validator.osgi.ValidatorHelper;

import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
/**
 * TODO JMS sample of Hello service exports
 * @author charliemordant
 *
 */
@Slf4j
@OsgiServiceProvider(classes= {HelloService.class})
public class HelloServiceJMS implements HelloService {
	@Setter
	@Inject @OsgiService
	private HelloObjectRepository helloObjectRepository;
	@Setter
	@Produce(uri = "jms:queue:helloServiceQueueOut")
	private ProducerTemplate producer;
	@Override
	@Consume(uri = "jms:queue:helloServiceQueueIn")
	public void persistHello(@NotNull @Valid @Body HelloObject helloObject_p) {
		log.error("****************** Save on JMS Service **********************");
		log.info("persisting new message with jms: " + helloObject_p.getHelloMessage());
//		Set<ConstraintViolation<HelloObject>> validationResults = ValidatorHelper.getValidator()
//				.validate(helloObject_p);
//		String errors = "";
//		if (!validationResults.isEmpty()) {
//			for (ConstraintViolation<HelloObject> violation : validationResults) {
//				log.info("subscription error, validating user:"
//						+ violation.getMessage());
//				errors += violation.getPropertyPath() + ": "
//						+ violation.getMessage().replaceAll("\"", "") + ";";
//			}
//			throw new ValidationException(errors);
//		}
		helloObjectRepository.save(helloObject_p);
		producer.sendBody(getHellos());
	}

	@Override
	public Hellos getHellos() {
		
		Collection<HelloObject> helloObjects = helloObjectRepository.findAll();
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

	private Function<HelloObject, String> helloObjectToStringFunction = new Function<HelloObject, String>() {

		@Override
		public String apply(HelloObject arg0) {
			return arg0.getHelloMessage();
		}
	};

	@Override
	public void deleteAll() {
		helloObjectRepository.deleteAll();
		
	}
}
