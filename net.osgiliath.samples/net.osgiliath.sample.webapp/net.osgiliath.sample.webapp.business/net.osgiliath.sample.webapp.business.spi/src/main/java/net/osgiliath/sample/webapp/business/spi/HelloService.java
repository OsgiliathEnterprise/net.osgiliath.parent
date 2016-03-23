package net.osgiliath.sample.webapp.business.spi;

/*
 * #%L
 * Business service API and service provider declaration
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.osgiliath.sample.webapp.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;

/**
 * REST service definition.
 * 
 * @author charliemordant.
 */

public interface HelloService {
	/**
	 * Registering instance.
	 * 
	 * @param helloObject
	 *            instance
	 */
	void persistHello(@NotNull @Valid HelloEntity helloObject);

	/**
	 * retrieving instances.
	 * 
	 * @return instances
	 */
	Hellos getHellos();

	/**
	 * Delete all hellos.
	 */
	void deleteHellos();
}
