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

import java.util.Collection;

import javax.validation.spi.ValidationProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Service tracker for validation providers.
 * @author charliemordant
 */
public class OsgiServiceValidationProviderTracker implements
    ServiceTrackerCustomizer {
  /**
   * bundle context
   */
  private final transient BundleContext context;

  /**
   * CTor
   * 
   * @param context
   *          bundle context
   */
  public OsgiServiceValidationProviderTracker(BundleContext context) {
    this.context = context;
  }

  /**
   * Adding validation provider
   * @param reference the service reference to add
   * @return the service
   */
  // callback method if MyClass service object is registered
  public Object addingService(final ServiceReference reference) {
    final Object serviceObject = this.context.getService(reference);
    if (serviceObject instanceof ValidationProvider<?>
        && !HibernateValidationOSGIServicesProviderResolver.getInstance()
            .getValidationProviders().contains(serviceObject)) {
      HibernateValidationOSGIServicesProviderResolver.getInstance()
          .getValidationProviders().add((ValidationProvider<?>) serviceObject);
    }

    return reference;
  }

  /**
   * Removed validation provider
   * @param reference the service reference
   * @param service the service to remove
   */
  // callback if necessary class is deregistred
  public void removedService(final ServiceReference reference,
      final Object service) {
    final Object serviceObject = this.context.getService(reference);
    if (serviceObject instanceof ValidationProvider<?>
        && HibernateValidationOSGIServicesProviderResolver.getInstance()
            .getValidationProviders().contains(serviceObject)) {
      HibernateValidationOSGIServicesProviderResolver.getInstance()
          .getValidationProviders()
          .remove((ValidationProvider<?>) serviceObject);
    }
  }

  /**
   * Initial providers parsing
   * 
   * @param context
   *          bundle context
   * @throws InvalidSyntaxException
   *           parsing error
   */
  public static void handleInitialReferences(BundleContext context)
      throws InvalidSyntaxException {
    final Collection<ServiceReference<ValidationProvider>> refs = context
        .getServiceReferences(ValidationProvider.class, null);
    for (final ServiceReference<ValidationProvider> reference : refs) {
      HibernateValidationOSGIServicesProviderResolver.getInstance()
          .getValidationProviders().add(context.getService(reference));
    }
  }

  /**
   * Modified service
   * @param reference service reference
   * @param service service
   */
  @Override
  public void modifiedService(final ServiceReference reference,
      final Object service) {
    this.removedService(reference, service);
    this.addingService(reference);

  }

}
