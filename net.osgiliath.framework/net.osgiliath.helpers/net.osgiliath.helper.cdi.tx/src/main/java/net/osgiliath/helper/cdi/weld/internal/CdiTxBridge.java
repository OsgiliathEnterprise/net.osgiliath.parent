package net.osgiliath.helper.cdi.weld.internal;

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
import static javax.transaction.Status.STATUS_ACTIVE;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;
import org.jboss.weld.transaction.spi.TransactionServices;
import lombok.Setter;
import org.osgi.framework.BundleContext;

@Slf4j
public class CdiTxBridge implements TransactionServices {
  /**
   * Configadmin service tracker.
   */
  private transient BundleContext context;

  public CdiTxBridge(BundleContext bundleContext) {
    this.context = bundleContext;
  }

  /**
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
   *      )
   * @param bundleContext
   *          the osgi bundle context
   */
  public final void start() {
    UserTransactionTracker.getInstance(this.context);
    TransactionManagerTracker.getInstance(this.context);
  }

  /**
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
   *      )
   * @param bundleContext
   *          the osgi bundle context
   */
  public final void stop() {
    UserTransactionTracker.stop();
    TransactionManagerTracker.stop();
    this.context = null;
  }

  @Override
  public void cleanup() {
    this.context = null;
  }

  @Override
  public void registerSynchronization(Synchronization synchronizedObserver) {
    Collection<TransactionManager> tx = TransactionManagerTracker.getInstance(
        this.context).getAdmins();

    try {
      if (!tx.isEmpty()) {
        tx.iterator().next().getTransaction()
            .registerSynchronization(synchronizedObserver);

      }
    }
    catch (IllegalStateException | RollbackException | SystemException e) {
      log.error("error registering userTransactions", e);

    }

  }

  @Override
  public boolean isTransactionActive() {
    try {
      return getUserTransaction().getStatus() == STATUS_ACTIVE;
    }
    catch (SystemException e) {
      throw new RuntimeException("Failed to determine transaction status", e);
    }
  }

  @Override
  public UserTransaction getUserTransaction() {
    Collection<UserTransaction> tx = UserTransactionTracker.getInstance(
        this.context).getUserTransactions();
    if (!tx.isEmpty())
      return tx.iterator().next();

    return null;
  }

}
