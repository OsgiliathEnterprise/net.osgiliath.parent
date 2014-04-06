package net.osgiliath.helpers.cdi.cxf.jaxrs.internal.trackers;

/*
 * #%L
 * net.osgiliath.helpers.cdi.cxf.jaxrs
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
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

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.InterceptorsServiceRegistry;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.apache.cxf.interceptor.Interceptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class InterceptorsServiceTracker extends ServiceTracker {

	public InterceptorsServiceTracker(BundleContext context, Class clazz,
			ServiceTrackerCustomizer customizer) {
		super(context, clazz, customizer);
		// TODO Auto-generated constructor stub
	}

	// callback method if MyClass service object is registered
	public Object addingService(ServiceReference reference) {
		Object serviceObject = this.context.getService(reference);
		
		if (serviceObject instanceof Interceptor) {
			InterceptorsServiceRegistry.getInstance().getInterceptors()
					.add((Interceptor) serviceObject);

		}

		return reference;
		// return service object
	}

	// callback if necessary class is deregistred
	public void removedService(ServiceReference reference, Object service) {
		Object serviceObject = this.context.getService(reference);
		if (serviceObject instanceof Interceptor) {
			InterceptorsServiceRegistry.getInstance().getInterceptors()
					.remove((Interceptor) serviceObject);

		}
	}
	
	public static void handleInitialReferences(BundleContext context)
			throws InvalidSyntaxException {
		Collection<ServiceReference<Interceptor>> refs = context
				.getServiceReferences(Interceptor.class, null);
		for (ServiceReference<Interceptor> reference : refs) {
			Interceptor svc = context.getService(reference);
			svc.toString();
			InterceptorsServiceRegistry.getInstance().getInterceptors()
					.add(svc);
		}
	}
	
}