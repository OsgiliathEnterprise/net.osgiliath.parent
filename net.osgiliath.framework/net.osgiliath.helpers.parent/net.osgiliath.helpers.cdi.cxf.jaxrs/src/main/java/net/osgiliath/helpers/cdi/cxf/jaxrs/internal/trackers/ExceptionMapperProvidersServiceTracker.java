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

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class ExceptionMapperProvidersServiceTracker implements
	ServiceTrackerCustomizer {

    private BundleContext context;

    public ExceptionMapperProvidersServiceTracker(BundleContext context) {
	this.context = context;
    }

    // callback method if MyClass service object is registered
    public Object addingService(ServiceReference reference) {
	Object serviceObject = this.context.getService(reference);

	if (serviceObject instanceof ExceptionMapper<?>) {
	    ProvidersServiceRegistry.getInstance().getExceptionMappers()
		    .add((ExceptionMapper) serviceObject);

	}

	return reference;
	// return service object
    }

    // callback if necessary class is deregistred
    public void removedService(ServiceReference reference, Object service) {
	Object serviceObject = this.context.getService(reference);
	if (serviceObject instanceof ExceptionMapper<?>) {
	    ProvidersServiceRegistry.getInstance().getExceptionMappers()
		    .remove((ExceptionMapper) serviceObject);

	}
    }

    public static void handleInitialReferences(BundleContext context)
	    throws InvalidSyntaxException {
	@SuppressWarnings("rawtypes")
	Collection<ServiceReference<ExceptionMapper>> refs = context
		.getServiceReferences(ExceptionMapper.class, null);
	for (ServiceReference<ExceptionMapper> reference : refs) {
	    ExceptionMapper svc = context.getService(reference);
	    ProvidersServiceRegistry.getInstance().getExceptionMappers()
		    .add(svc);
	}
    }

    @Override
    public void modifiedService(ServiceReference reference, Object service) {
	// TODO Auto-generated method stub

    }

}