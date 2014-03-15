package conf;

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
import javax.enterprise.inject.Produces;
import javax.validation.Validator;

import net.osgiliath.validator.osgi.ValidatorHelper;

public class CDIValidator {
	
	@Produces
	@org.hibernate.validator.cdi.HibernateValidator
	@Default
	public Validator createValidator() {

//		ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
//				.byProvider(HibernateValidator.class);
//
//		// bootstrap to properly resolve in an OSGi environment
//		validationBootStrap
//				.providerResolver(HibernateValidationOSGIServicesProviderResolver
//						.getInstance());
//
//		HibernateValidatorConfiguration configure = validationBootStrap
//				.configure();
//		ValidatorFactory validatorFactory = configure.buildValidatorFactory();
//		Validator validator = validatorFactory.getValidator();
		return ValidatorHelper.getValidator();
	}
}
