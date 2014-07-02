package net.osgiliath.validation.cdi.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.validation
 * %%
 * Copyright (C) 2013 Osgiliath corp
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

import javax.enterprise.inject.Default;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import net.osgiliath.validation.cdi.HelloObject;
import net.osgiliath.validation.cdi.IValidatorFactorySample;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;
/**
 * 
 * @author charliemordant
 * Validation test
 */
@Default
@OsgiServiceProvider
public class ValidatorFactorySample implements IValidatorFactorySample {
    /**
     * Validation of a null message
     */
    public void nullMessageValidation(@NotNull @Valid HelloObject object) {

	if (object != null) {
	    // Exception must have be thrown
	    System.out.println(object.toString());
	}
	
    }
}
