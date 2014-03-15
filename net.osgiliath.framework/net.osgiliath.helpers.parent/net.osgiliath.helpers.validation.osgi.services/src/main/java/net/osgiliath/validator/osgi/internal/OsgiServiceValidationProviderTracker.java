package net.osgiliath.validator.osgi.internal;

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

import javax.validation.spi.ValidationProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class OsgiServiceValidationProviderTracker extends ServiceTracker {

	public OsgiServiceValidationProviderTracker(BundleContext context,
			Class clazz, ServiceTrackerCustomizer customizer) {
		super(context, clazz, customizer);
		// TODO Auto-generated constructor stub
	}

	// callback method if MyClass service object is registered
	public Object addingService(ServiceReference reference) {
		Object serviceObject = this.context.getService(reference);
		if (serviceObject instanceof ValidationProvider<?>) {
			if (!HibernateValidationOSGIServicesProviderResolver.getInstance()
					.getValidationProviders().contains(serviceObject))
				HibernateValidationOSGIServicesProviderResolver.getInstance()
						.getValidationProviders()
						.add((ValidationProvider<?>) serviceObject);
		}

		return reference;
		// return service object
	}

	// callback if necessary class is deregistred
	public void removedService(ServiceReference reference, Object service) {
		Object serviceObject = this.context.getService(reference);
		if (serviceObject instanceof ValidationProvider<?>) {
			if (HibernateValidationOSGIServicesProviderResolver.getInstance()
					.getValidationProviders().contains(serviceObject))
				HibernateValidationOSGIServicesProviderResolver.getInstance()
						.getValidationProviders()
						.remove((ValidationProvider<?>) serviceObject);
		}
	}

}
