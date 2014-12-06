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
import net.osgiliath.validator.osgi.internal.ValidatorFactorySingleton;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * 
 * @author charliemordant Activator for JSR303 provider services
 */
public class JSR303Activator implements BundleActivator {
  /**
   * Validation providers tracker
   */
  private ServiceTracker tracker;

  /**
   * Start method
   */
  @Override
  public void start(BundleContext context) throws Exception {

    this.tracker = new ServiceTracker(context, ValidationProvider.class,
        new OsgiServiceValidationProviderTracker(context));

    this.tracker.open();
    OsgiServiceValidationProviderTracker.handleInitialReferences(context);

    // now that we've done configuring the ValidatorFactory, let's build it

    context.registerService(ValidatorFactory.class.getName(),
        ValidatorFactorySingleton.getValidatorFactory(), null);

  }

  /**
   * Stop validation providers bundle
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    final ServiceReference<ValidatorFactory> reference = context
        .getServiceReference(ValidatorFactory.class);
    context.ungetService(reference);
    //
    this.tracker.close();

  }

}
