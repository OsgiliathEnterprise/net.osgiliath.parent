package net.osgiliath.hello.business.spi.services;

/*
 * #%L
 * net.osgiliath.hello.business.spi
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;

import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloObject;
/**
 * TODO you can remove this declaration after seen the implementation
 * @author charliemordant
 *
 */

public interface HelloService {
	
	void persistHello(@NotNull @Valid HelloObject helloMessage_p);
	Hellos getHellos();
	void deleteAll();

}
