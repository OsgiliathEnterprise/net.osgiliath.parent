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
import net.osgiliath.hello.model.jpa.model.HelloEntity;
import net.osgiliath.hello.model.jpa.repository.HelloObjectRepository;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Sample of a business service with JaxRS
 * 
 * @author charliemordant
 * 
 */
@Slf4j
public class HelloServiceJaxRS implements
	net.osgiliath.hello.business.impl.HelloServiceJaxRS {
    /**
     * Repository to persist data
     */
    @Setter
    private HelloObjectRepository helloObjectRepository;
    /**
     * JSR 303 validator
     */
    // JSR 303 validator
    @Setter
    private Validator validator;
    /**
     * Saves the object or throw an exception if the Object is not valid
     */
    @Override
    public final void persistHello(final HelloEntity helloObject_p) {
	this.log.info("persisting new message with jaxrs: "
		+ helloObject_p.getHelloMessage());
	final Set<ConstraintViolation<HelloEntity>> validationResults = validator
		.validate(helloObject_p);
	final StringBuilder errors = new StringBuilder("");
	if (!validationResults.isEmpty()) {
	    for (ConstraintViolation<HelloEntity> violation : validationResults) {
		this.log.info("subscription error, validating user:"
			+ violation.getMessage());
		errors.append(violation.getPropertyPath()).append(": ")
		.append(violation.getMessage().replaceAll("\"", "")).append(";");
	    }
	    throw new ValidationException(errors.toString());
	}
	this.helloObjectRepository.save(helloObject_p);

    }
    /**
     * get all hellos
     */
    @Override
    public Hellos getHellos() {
	final Collection<HelloEntity> helloObjects = this.helloObjectRepository.findAll();
	if (helloObjects.isEmpty()) {
	    throw new UnsupportedOperationException(
		    "You should not call this method when there is no Hello yet !");
	}
	return Hellos
		.builder()
		.helloCollection(
			Lists.newArrayList(Iterables.transform(helloObjects,
				this.helloObjectToStringFunction))).build();
    }
    /**
     * Function that transforms helloEntity to String
     */
    // Guava function waiting for Java 8
    private Function<HelloEntity, String> helloObjectToStringFunction = new Function<HelloEntity, String>() {

	@Override
	public String apply(HelloEntity arg0) {
	    return arg0.getHelloMessage();
	}
    };
    /**
     * Deletes all entities
     */
    @Override
    public void deleteAll() {
	this.helloObjectRepository.deleteAll();
    }

}
