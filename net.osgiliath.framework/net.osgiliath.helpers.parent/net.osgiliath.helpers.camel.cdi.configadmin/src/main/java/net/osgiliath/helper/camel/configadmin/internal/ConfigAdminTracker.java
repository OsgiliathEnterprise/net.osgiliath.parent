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

import java.io.IOException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.istack.FinalArrayList;

/**
 * 
 * @author charliemordant Service tracker for camel cdi config admin properties
 *         resolution
 */
public class ConfigAdminTracker implements
	ServiceTrackerCustomizer<ConfigurationAdmin, Object> {
    /**
     * Logger
     */
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConfigAdminTracker.class);
    /**
     * Properties
     */
    private Collection<ConfigurationAdmin> admins = new HashSet<>();
    /**
     * Singleton of the tracker
     */
    private static ConfigAdminTracker instance = null;
    /**
     * Bundle context
     */
    private BundleContext context;

    /**
     * 
     * @return the singleton instance
     */
    public static ConfigAdminTracker getInstance(BundleContext context) {
	if (instance == null) {

	    instance = new ConfigAdminTracker();
	    if (context == null) {
	    instance.context = FrameworkUtil.getBundle(
		    CdiConfigAdminServiceActivator.class).getBundleContext();
	    } else {
		instance.context = context;
	    }
	    
	}
	return instance;
    }

    /**
     * Adds config
     */
    @Override
    public final Object addingService(final ServiceReference reference) {
	final ConfigurationAdmin admin = (ConfigurationAdmin) context
		.getService(reference);
	this.admins.add(admin);
	return admin;
    }

    /**
     * Modified a config
     */
    @Override
    public final void modifiedService(final ServiceReference<ConfigurationAdmin> reference,
	    Object service) {
	removedService(reference, service);
	addingService(reference);

    }
    /**
     * removed config
     */
    @Override
    public final void removedService(ServiceReference<ConfigurationAdmin> reference,
	    final Object service) {
	final ConfigurationAdmin admin = context.getService(reference);
	this.admins.remove(admin);

    }

    /**
     * Get the property with the according key
     * 
     * @param key
     *            the propertuy key
     * @return the corresponding property
     * @throws IOException
     *             read error
     * @throws InvalidSyntaxException
     *             wrong key
     */
    public String getProperty(String key) throws IOException,
	    InvalidSyntaxException {
	LOG.info("Retreiving property key: " + key);
	for (ConfigurationAdmin admin : this.admins) {
	    final Configuration[] configurations = admin
		    .listConfigurations(null);
	    if (configurations != null) {
		for (final Configuration configuration : configurations) {
		    LOG.debug("parsing configuration: "
			    + configuration.getPid());
		    final Dictionary<String, Object> dictionary = configuration
			    .getProperties();
		    final Object valObject = dictionary.get(key);
		    if (valObject != null && valObject instanceof String) {
			LOG.trace("got value: " + valObject);
			return (String) valObject;
		    }
		}
	    }
	}
	return null;
    }

}
