package net.osgiliath.module.cdi.weld.internal;

import java.io.IOException;

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
public class UserTransactionTracker implements SingleServiceTracker.SingleServiceListener {
	private static UserTransactionTracker instance;
	private BundleContext bundleContext;
	private SingleServiceTracker configAdminTracker;

	/**
	 * Properties.
	 */
	private Collection<UserTransaction> userTransactions;

	/**
	 * Gets the registered configurations.
	 * 
	 * @return the registered configurations
	 */
	public final Collection<UserTransaction> getUserTransactions() {
		return this.userTransactions;
	}

	public UserTransactionTracker() {
		if (null == userTransactions)
			this.userTransactions = new HashSet<>();
	}

	public UserTransactionTracker(BundleContext bundleContext) throws InvalidSyntaxException {
		this();
		this.getInstance().bundleContext = bundleContext;
		this.getInstance().configAdminTracker = new SingleServiceTracker<>(bundleContext, TransactionManager.class,
				this);
		this.getInstance().configAdminTracker.open();

	}

	/**
	 * Singleton.
	 * 
	 * @param context
	 *            the bundle context
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
		this.getInstance().configAdminTracker.close();
		this.getInstance().configAdminTracker = null;
		this.getInstance().userTransactions = null;
		this.getInstance().bundleContext = null;
	}

	/**
	 * Initializes the uTTracker.
	 * 
	 * @param bundleContext
	 *            the bundle context
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

	@Override
	public void serviceFound() {
		getInstance().internalReparse();

	}

	@Override
	public void serviceLost() {
		getInstance().internalReparse();

	}

	@Override
	public void serviceReplaced() {
		getInstance().internalReparse();

	}

}
