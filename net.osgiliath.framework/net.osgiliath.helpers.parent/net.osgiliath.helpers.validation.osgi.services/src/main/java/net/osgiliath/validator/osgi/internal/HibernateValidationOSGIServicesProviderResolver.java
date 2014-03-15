package net.osgiliath.validator.osgi.internal;

/*
 * #%L
 * helpers.validation.osgi.services
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



import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

import org.hibernate.validator.HibernateValidator;


/**
 * OSGi classpath aware {@link javax.validation.ValidationProviderResolver
 * ValidationProviderResolver}.
 * 
 */
public class HibernateValidationOSGIServicesProviderResolver implements ValidationProviderResolver {
	private static ValidationProviderResolver instance = null;
	
	public HibernateValidationOSGIServicesProviderResolver() {
		super();
//		if (instance == null)
//			instance = new HibernateValidationOSGIServicesProviderResolver();

	}
	public static ValidationProviderResolver getInstance() {
		if (instance == null)
			instance = new HibernateValidationOSGIServicesProviderResolver();
		return instance;
	}

	@Override
	public List<ValidationProvider<?>> getValidationProviders() {
		List<ValidationProvider<?>> providers = new ArrayList<ValidationProvider<?>>(
				1);
		providers.add(new HibernateValidator());
		return providers;
	}

	
}
