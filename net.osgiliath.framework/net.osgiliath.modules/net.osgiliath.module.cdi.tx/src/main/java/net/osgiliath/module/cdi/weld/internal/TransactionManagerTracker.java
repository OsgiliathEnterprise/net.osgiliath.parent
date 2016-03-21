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

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.cdi.weld.api.SingleServiceTracker;

/**
 * Service uTTracker for camel cdi config admin properties resolution.
 * 
 * @author charliemordant
 */
@Slf4j
public class TransactionManagerTracker implements SingleServiceTracker.SingleServiceListener {
	private static TransactionManagerTracker instance;
	private BundleContext bundleContext;
	private SingleServiceTracker configAdminTracker;
	/**
	 * Properties.
	 */
	private Collection<TransactionManager> txManagers;

	/**
	 * Gets the registered configurations.
	 * 
	 * @return the registered configurations
	 */
	final Collection<TransactionManager> getAdmins() {
		return this.txManagers;
	}

	public TransactionManagerTracker() {
		if (null == txManagers)
			this.txManagers = new HashSet<>();
	}

	public TransactionManagerTracker(BundleContext bundleContext) throws InvalidSyntaxException {
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
	public static synchronized TransactionManagerTracker getInstance() {
		if (null == instance) {
			instance = new TransactionManagerTracker();
		}
		return instance;
	}

	/**
	 * Stops the uTTracker.
	 */
	public synchronized void stop() {
		this.getInstance().configAdminTracker.close();
		this.getInstance().configAdminTracker = null;
		this.getInstance().txManagers = null;
		this.getInstance().bundleContext = null;
	}

	public void internalReparse() {
		if (null != TransactionManagerTracker.getInstance()
				&& null != TransactionManagerTracker.getInstance().getAdmins()) {
			TransactionManagerTracker.getInstance().getAdmins().clear();
			try {
				final ServiceReference<?>[] registeredConfigAdmins = (ServiceReference<?>[]) bundleContext
						.getAllServiceReferences(TransactionManager.class.getName(), null);
				if (null != registeredConfigAdmins) {
					for (final ServiceReference<?> adminRef : registeredConfigAdmins) {

						TransactionManagerTracker.getInstance().getAdmins()
								.add((TransactionManager) bundleContext.getService(adminRef));
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
