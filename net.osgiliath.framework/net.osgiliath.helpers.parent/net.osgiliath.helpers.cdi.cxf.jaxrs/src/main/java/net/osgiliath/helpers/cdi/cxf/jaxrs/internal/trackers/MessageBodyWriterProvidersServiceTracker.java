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

import javax.ws.rs.ext.MessageBodyWriter;

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * 
 * @author charliemordant Message body writer OSGI service tracker
 */
public class MessageBodyWriterProvidersServiceTracker implements
    ServiceTrackerCustomizer {
  /**
   * bundle context
   */
  private final BundleContext context;

  /**
   * Ctor
   * 
   * @param context
   *          the bundle context
   */
  public MessageBodyWriterProvidersServiceTracker(BundleContext context) {
    this.context = context;
  }

  /**
   * Service tracker added service
   */
  // callback method if MyClass service object is registered
  public Object addingService(final ServiceReference reference) {
    final Object serviceObject = this.context.getService(reference);
    if (serviceObject instanceof MessageBodyWriter<?>) {
      ProvidersServiceRegistry.getInstance().getWriters()
          .add((MessageBodyWriter) serviceObject);

    }

    return serviceObject;
    // return service object
  }

  /**
   * remove service
   */
  // callback if necessary class is deregistred
  public void removedService(final ServiceReference reference,
      final Object service) {
    final Object serviceObject = this.context.getService(reference);
    if (serviceObject instanceof MessageBodyWriter<?>) {
      ProvidersServiceRegistry.getInstance().getWriters()
          .remove((MessageBodyWriter) serviceObject);

    }

  }

  /**
   * Initial call to tracker
   * 
   * @param context
   *          bundle context
   * @throws InvalidSyntaxException
   *           parsing error
   */
  public static void handleInitialReferences(final BundleContext context)
      throws InvalidSyntaxException {
    final Collection<ServiceReference<MessageBodyWriter>> refs = context
        .getServiceReferences(MessageBodyWriter.class, null);
    for (ServiceReference<MessageBodyWriter> reference : refs) {
      final MessageBodyWriter svc = context.getService(reference);
      svc.toString();
      ProvidersServiceRegistry.getInstance().getWriters().add(svc);
    }
  }

  /**
   * Modification of a service
   */
  @Override
  public void modifiedService(ServiceReference reference, Object service) {
    this.removedService(reference, service);
    this.addingService(reference);

  }
}