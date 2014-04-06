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

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class MessageBodyWriterProvidersServiceTracker implements ServiceTrackerCustomizer {

	private BundleContext context;
	
	public MessageBodyWriterProvidersServiceTracker(BundleContext context) {
		this.context = context;
	}

	// callback method if MyClass service object is registered
	public Object addingService(ServiceReference reference) {
		Object serviceObject = this.context.getService(reference);
		if (serviceObject instanceof MessageBodyWriter<?>) {
			ProvidersServiceRegistry.getInstance().getWriters()
					.add((MessageBodyWriter) serviceObject);

		}
		

		return serviceObject;
		// return service object
	}

	// callback if necessary class is deregistred
	public void removedService(ServiceReference reference, Object service) {
		Object serviceObject = this.context.getService(reference);
		if (serviceObject instanceof MessageBodyWriter<?>) {
			ProvidersServiceRegistry.getInstance().getWriters()
					.remove((MessageBodyWriter) serviceObject);

		}
		
	}
	
	public static void handleInitialReferences(BundleContext context)
			throws InvalidSyntaxException {
		Collection<ServiceReference<MessageBodyWriter>> refs = context
				.getServiceReferences(MessageBodyWriter.class, null);
		for (ServiceReference<MessageBodyWriter> reference : refs) {
			MessageBodyWriter svc = context.getService(reference);
			svc.toString();
			ProvidersServiceRegistry.getInstance().getWriters()
					.add(svc);
		}
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub
		
	}
}