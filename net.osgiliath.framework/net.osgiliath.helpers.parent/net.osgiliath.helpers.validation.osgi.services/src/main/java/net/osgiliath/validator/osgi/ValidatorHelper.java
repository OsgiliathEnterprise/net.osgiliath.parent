package net.osgiliath.validator.osgi;

/*
 * #%L
 * net.osgiliath.helpers.validation.osgi.services
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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.ProviderSpecificBootstrap;

import net.osgiliath.validator.osgi.internal.HibernateValidationOSGIServicesProviderResolver;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

public class ValidatorHelper {
private static ValidatorFactory validatorFactory = null;
	public static Validator getValidator() {
		if (validatorFactory == null) {
			
		  ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
  				.byProvider(HibernateValidator.class);

  		// bootstrap to properly resolve in an OSGi environment
  		validationBootStrap
  				.providerResolver(HibernateValidationOSGIServicesProviderResolver
  						.getInstance());

  		HibernateValidatorConfiguration configure = validationBootStrap
  				.configure();
  		 validatorFactory = configure/*
  													 * .constraintValidatorFactory
  													 * (new
  													 * CDIAwareConstraintValidatorFactory
  													 * ())
  													 */.buildValidatorFactory();}
  		return validatorFactory.getValidator();
	}
}
