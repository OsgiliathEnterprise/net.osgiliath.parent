package net.osgiliath.helper.cdi.weld.internal;

/*
 * #%L
 * net.osgiliath.helper.camel.cdi.configadmin
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
import java.util.HashSet;
import javax.transaction.TransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.transaction.spi.TransactionServices;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Service uTTracker for camel cdi config admin properties resolution.
 * 
 * @author charliemordant
 */
@Slf4j
public class TransactionManagerTracker implements ServiceTrackerCustomizer<TransactionManager, Object> {

  /**
   * Properties.
   */
  private final Collection<TransactionManager> txManagers = new HashSet<>();

  /**
   * Gets the registered configurations.
   * 
   * @return the registered configurations
   */
  final Collection<TransactionManager> getAdmins() {
    return this.txManagers;
  }

  /**
   * Singleton of the uTTracker.
   */
  private static TransactionManagerTracker instance;
  /**
   * Bundle context.
   */
  private transient BundleContext context;
  /**
   * The service uTTracker.
   */
  private transient ServiceTracker tMTracker;

  /**
   * Singleton.
   * 
   * @param context
   *          the bundle context
   * @return the singleton instance
   */
  public static synchronized TransactionManagerTracker getInstance(BundleContext context) {
    if (instance == null) {
      instance = new TransactionManagerTracker();
      if (context == null) {
        instance.context = FrameworkUtil.getBundle(TransactionServices.class).getBundleContext();
      }
      else {
        instance.context = context;
      }
      instance.tMTracker = new ServiceTracker(instance.context, TransactionManager.class,
          TransactionManagerTracker.getInstance(instance.context));
      instance.parseInitialContribution(instance.context);
      instance.tMTracker.open();

    }
    return instance;
  }

  /**
   * Stops the uTTracker.
   */
  public static synchronized void stop() {
    instance.tMTracker.close();
  }

  /**
   * Initializes the uTTracker.
   * 
   * @param bundleContext
   *          the bundle context
   */
  private void parseInitialContribution(BundleContext bundleContext) {
    try {
      final ServiceReference<?>[] registeredConfigAdmins = (ServiceReference<?>[]) bundleContext
          .getAllServiceReferences(TransactionManager.class.getName(), null);
      if (null != registeredConfigAdmins) {
        for (final ServiceReference<?> adminRef : registeredConfigAdmins) {

          TransactionManagerTracker.getInstance(bundleContext).getAdmins()
              .add((TransactionManager) bundleContext.getService(adminRef));
        }
      }
    }
    catch (InvalidSyntaxException e) {
      log.error("Error getting servicereferences of config admin", e);
    }
  }

  /**
   * Adds config.
   */
  @Override
  public final Object addingService(final ServiceReference<TransactionManager> reference) {
    final TransactionManager admin = this.context.getService(reference);
    getInstance(null).txManagers.add(admin);
    return admin;
  }

  /**
   * Modified a config.
   */
  @Override
  public final void modifiedService(final ServiceReference<TransactionManager> reference, Object service) {
    removedService(reference, service);
    this.addingService(reference);

  }

  /**
   * removed config.
   */
  @Override
  public final void removedService(ServiceReference<TransactionManager> reference, final Object service) {
    final TransactionManager admin = this.context.getService(reference);
    getInstance(null).txManagers.remove(admin);

  }

}
