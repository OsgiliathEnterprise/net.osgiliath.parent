package net.osgiliath.module.cdi.weld.internal;

import static javax.transaction.Status.STATUS_ACTIVE;

import java.util.Collection;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;

/*
 * #%L
 * Aries tx CDI bridge
 * %%
 * Copyright (C) 2013 - 2015 Osgiliath
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

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.transaction.spi.TransactionServices;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

/**
 * Weld bridge for CDI.
 * 
 * @author charliemordant
 *
 */
@Slf4j
public class CdiTxBridge implements TransactionServices {
  /**
   * Configadmin service tracker.
   */
  private BundleContext context;
  /**
   * Config admin tracker.
   */
  private TransactionManagerTracker tmtracker;
  /**
   * User transaction tracker.
   */
  private UserTransactionTracker uttracker;

  /**
   * Ctor.
   * 
   * @param bundleContext
   *          the bundle context.
   */
  public CdiTxBridge(BundleContext bundleContext) {
    this.context = bundleContext;
  }

  /**
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
   *      ) starts the different trackers
   */
  public final void start() {
    try {
      tmtracker = new TransactionManagerTracker(this.context);
      uttracker = new UserTransactionTracker(this.context);
    } catch (InvalidSyntaxException e) {
      log.error("error instantiating tm tracker", e);
    }

  }

  /**
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
   *      ) Stops the trackers.
   */
  public final void stop() {
    if (null != tmtracker) {
      tmtracker.stop();
    }
    tmtracker = null;
    if (null != uttracker) {
      uttracker.stop();
    }
    uttracker = null;
    this.context = null;
  }

  /**
   * Cleanup trackers.
   */
  @Override
  public void cleanup() {
    stop();

  }

  /**
   * registers the synchronized tm trackers with cdi.
   * 
   * @param synchronizedObserver
   *          observers.
   */
  @Override
  public void registerSynchronization(Synchronization synchronizedObserver) {
    Collection<TransactionManager> tx = TransactionManagerTracker.getInstance()
        .getAdmins();

    try {
      if (!tx.isEmpty()) {
        tx.iterator().next().getTransaction()
            .registerSynchronization(synchronizedObserver);

      }
    } catch (IllegalStateException | RollbackException | SystemException e) {
      log.error("error registering userTransactions", e);

    }

  }

  /**
   * Tells if a transaction is currently active.
   */
  @Override
  public boolean isTransactionActive() {
    try {
      return getUserTransaction().getStatus() == STATUS_ACTIVE;
    } catch (SystemException e) {
      throw new RuntimeException("Failed to determine transaction status", e);
    }
  }

  /**
   * Retreives the user transaction.
   */
  @Override
  public UserTransaction getUserTransaction() {
    Collection<UserTransaction> tx = UserTransactionTracker.getInstance()
        .getUserTransactions();
    if (!tx.isEmpty()) {
      return tx.iterator().next();
    }
    return null;
  }

}
