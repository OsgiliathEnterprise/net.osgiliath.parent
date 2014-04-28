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
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigAdminTracker implements
		ServiceTrackerCustomizer<ConfigurationAdmin, Object> {
	private static final Logger LOG = LoggerFactory
			.getLogger(ConfigAdminTracker.class);

	private Collection<ConfigurationAdmin> admins = new HashSet<>();
	private static ConfigAdminTracker instance = null;
	private BundleContext context;

	public static ConfigAdminTracker getInstance() {
		if (instance == null) {
			instance = new ConfigAdminTracker();
		}
		return instance;
	}

	public static ConfigAdminTracker getInstance(BundleContext bundleContext) {
		if (instance == null) {
			instance = new ConfigAdminTracker();
			instance.context = bundleContext;
		}
		return instance;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		ConfigurationAdmin admin = (ConfigurationAdmin) context
				.getService(reference);
		admins.add(admin);
//		try {
//			addConfigSources(admin);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InvalidSyntaxException e) {
//			e.printStackTrace();
//		}
		return admin;
	}

	@Override
	public void modifiedService(ServiceReference<ConfigurationAdmin> reference,
			Object service) {
		ConfigurationAdmin admin = context.getService(reference);
		admins.remove(admin);
		admins.add(admin);
//		ConfigResolver.freeConfigSources();
//		try {
//			addConfigSources(admin);
//		} catch (IOException | InvalidSyntaxException e) {
//
//			e.printStackTrace();
//		}

	}

	@Override
	public void removedService(ServiceReference<ConfigurationAdmin> reference,
			Object service) {
		ConfigurationAdmin admin = context.getService(reference);
		admins.remove(admin);
		//ConfigResolver.freeConfigSources();

	}

//	public void addConfigSources(ConfigurationAdmin admin) throws IOException,
//			InvalidSyntaxException {
//		List<ConfigSource> sources = new ArrayList<>();
//		Configuration[] configurations = admin.listConfigurations(null);
//		if (configurations != null) {
//			for (final Configuration configuration : configurations) {
//				LOG.info("Discovered configuration: " + configuration.getPid());
//				final Dictionary<String, Object> dictionary = configuration
//						.getProperties();
//
//				ConfigSource source = new ConfigSource() {
//
//					@Override
//					public boolean isScannable() {
//						return true;
//					}
//
//					@Override
//					public String getPropertyValue(String key) {
//						LOG.debug("Retreiving property with key: " + key);
//						Object valObject = dictionary.get(key);
//						if (valObject instanceof String) {
//							LOG.trace("got value: " + valObject);
//							return (String) dictionary.get(valObject);
//						}
//						return null;
//					}
//
//					@Override
//					public Map<String, String> getProperties() {
//						Map<String, String> ret = new HashMap<String, String>();
//						Enumeration<String> keysEnumeration = dictionary.keys();
//						while (keysEnumeration.hasMoreElements()) {
//							String key = keysEnumeration.nextElement();
//							Object valObject = dictionary.get(key);
//							if (valObject instanceof String) {
//								ret.put(key, (String) valObject);
//							}
//						}
//						return ret;
//					}
//
//					@Override
//					public int getOrdinal() {
//						return 0;
//					}
//
//					@Override
//					public String getConfigName() {
//						return configuration.getBundleLocation();
//					}
//				};
//				sources.add(source);
//
//			}
//			ConfigResolver.addConfigSources(sources);
//		}
//
//	}

	public String getProperty(String key) throws IOException, InvalidSyntaxException {
		LOG.info("Retreiving property key: " + key);
		for (ConfigurationAdmin admin : admins) {
			Configuration[] configurations = admin.listConfigurations(null);
			if (configurations != null) {
				for (final Configuration configuration : configurations) {
					LOG.debug("parsing configuration: "
							+ configuration.getPid());
					final Dictionary<String, Object> dictionary = configuration
							.getProperties();
					
					Object valObject = dictionary.get(key);
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
