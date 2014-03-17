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

import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import javax.validation.spi.ValidationProvider;

import net.osgiliath.validator.osgi.internal.HibernateValidationOSGIServicesProviderResolver;
import net.osgiliath.validator.osgi.internal.OsgiServiceValidationProviderTracker;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class JSR303Activator implements BundleActivator {
	private OsgiServiceValidationProviderTracker tracker;

	@Override
	public void start(BundleContext context) throws Exception {

		tracker = new OsgiServiceValidationProviderTracker(context,
				ValidationProvider.class, null);

		tracker.open();
		ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
				.byProvider(HibernateValidator.class);
//
//		// bootstrap to properly resolve in an OSGi environment
		validationBootStrap
				.providerResolver(HibernateValidationOSGIServicesProviderResolver
						.getInstance());
//
		HibernateValidatorConfiguration configure = validationBootStrap
				.configure();
		ValidatorFactory validatorFactory = configure/*
									 * .constraintValidatorFactory (new
									 * CDIAwareConstraintValidatorFactory ())
									 */.buildValidatorFactory();
		/* */
		// Validator validator = validatorFactory.getValidator();
		// configure Spring to autowire our constraints not mandatory
		// configure.constraintValidatorFactory(new
		// SpringConstraintValidatorFactory(this.applicationContext
		// .getAutowireCapableBeanFactory()));

		// now that we've done configuring the ValidatorFactory, let's build it

		context.registerService(ValidatorFactory.class.getName(),
				validatorFactory, null);

	}

	

	@Override
	public void stop(BundleContext context) throws Exception {
		ServiceReference<ValidatorFactory> reference = context
				.getServiceReference(ValidatorFactory.class);
		context.ungetService(reference);
		//
		tracker.close();

	}

}
