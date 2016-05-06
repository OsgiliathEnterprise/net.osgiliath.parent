package net.osgiliath.module.cdi.weld.internal;

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
import javax.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.cdi.weld.api.SingleServiceTracker;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * Service uTTracker for camel cdi config admin properties resolution.
 * 
 * @author charliemordant
 */
@Slf4j
public class UserTransactionTracker
    implements SingleServiceTracker.SingleServiceListener {
  /**
   * Singleton instance.
   */
  private static UserTransactionTracker instance;
  /**
   * Bundle context.
   */
  private BundleContext bundleContext;
  /**
   * Main tracker.
   */
  private SingleServiceTracker configAdminTracker;

  /**
   * Properties.
   */
  private Collection<UserTransaction> userTransactions;

  /**
   * Default Ctor.
   */
  public UserTransactionTracker() {
    if (null == userTransactions)
      this.userTransactions = new HashSet<>();
  }

  /**
   * Setter with contexts: registers listeners/tracker.
   * 
   * @param bundleContext
   *          the bundle context.
   * @throws InvalidSyntaxException
   *           wrong filter.
   */
  public UserTransactionTracker(BundleContext bundleContext)
      throws InvalidSyntaxException {
    this();
    getInstance().bundleContext = bundleContext;
    getInstance().configAdminTracker = new SingleServiceTracker<>(bundleContext,
        TransactionManager.class, this);
    getInstance().configAdminTracker.open();

  }

  /**
   * Gets the registered configurations.
   * 
   * @return the registered configurations
   */
  public final Collection<UserTransaction> getUserTransactions() {
    return this.userTransactions;
  }

  /**
   * Singleton.
   * 
   * @return the singleton instance
   */
  public static synchronized UserTransactionTracker getInstance() {
    if (null == instance) {
      instance = new UserTransactionTracker();
    }
    return instance;
  }

  /**
   * Stops the uTTracker.
   */
  public synchronized void stop() {
    getInstance().configAdminTracker.close();
    getInstance().configAdminTracker = null;
    getInstance().userTransactions = null;
    getInstance().bundleContext = null;
  }

  /**
   * Initializes the uTTracker.
   * 
   */

  public void internalReparse() {
    if (null != TransactionManagerTracker.getInstance()
        && null != TransactionManagerTracker.getInstance().getAdmins()) {
      TransactionManagerTracker.getInstance().getAdmins().clear();
      try {
        final ServiceReference<?>[] registeredConfigAdmins = (ServiceReference<?>[]) bundleContext
            .getAllServiceReferences(UserTransaction.class.getName(), null);
        if (null != registeredConfigAdmins) {
          for (final ServiceReference<?> adminRef : registeredConfigAdmins) {

            UserTransactionTracker.getInstance().getUserTransactions()
                .add((UserTransaction) bundleContext.getService(adminRef));
          }
        }
      } catch (InvalidSyntaxException e) {
        log.error("Error getting servicereferences of config admin", e);
      }
    }
  }

  /**
   * Reparses on new service.
   */
  @Override
  public void serviceFound() {
    getInstance().internalReparse();

  }

  /**
   * Reparses on lost service.
   */
  @Override
  public void serviceLost() {
    getInstance().internalReparse();

  }

  /**
   * Reparses on service replacement.
   */
  @Override
  public void serviceReplaced() {
    getInstance().internalReparse();

  }

}
