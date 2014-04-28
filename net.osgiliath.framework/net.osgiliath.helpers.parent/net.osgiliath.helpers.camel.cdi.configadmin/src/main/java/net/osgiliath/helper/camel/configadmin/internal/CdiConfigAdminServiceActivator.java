package net.osgiliath.helper.camel.configadmin.internal;

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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

public class CdiConfigAdminServiceActivator implements BundleActivator {
	private ServiceTracker tracker;
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		tracker = new ServiceTracker(bundleContext, ConfigurationAdmin.class,  ConfigAdminTracker.getInstance(bundleContext));
		tracker.open();
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		tracker.close();
		
	}

}
