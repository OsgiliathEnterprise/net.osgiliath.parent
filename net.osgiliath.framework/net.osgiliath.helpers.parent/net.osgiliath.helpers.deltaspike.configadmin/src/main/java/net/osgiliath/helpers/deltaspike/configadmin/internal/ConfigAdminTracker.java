package net.osgiliath.helpers.deltaspike.configadmin.internal;

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
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.deltaspike.core.api.common.DeltaSpike;
import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.apache.deltaspike.core.spi.config.ConfigSourceProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author charliemordant Service tracker for camel cdi config admin properties
 *         resolution
 */
@Slf4j
public class ConfigAdminTracker implements
    ServiceTrackerCustomizer<ConfigurationAdmin, Object>, ConfigSourceProvider {

  /**
   * Properties
   */
  private Collection<ConfigurationAdmin> admins = new HashSet<>();

  Collection<ConfigurationAdmin> getAdmins() {
    return admins;
  }

  /**
   * Singleton of the tracker
   */
  private static ConfigAdminTracker instance = null;
  /**
   * Bundle context
   */
  private BundleContext context;
  private ServiceTracker tracker;

  /**
   * 
   * @return the singleton instance
   */
  public static synchronized ConfigAdminTracker getInstance(
      BundleContext context) {
    if (instance == null) {

      instance = new ConfigAdminTracker();
      if (context == null) {
        instance.context = FrameworkUtil.getBundle(DeltaSpike.class)
            .getBundleContext();
      } else {
        instance.context = context;
      }
      instance.tracker = new ServiceTracker(instance.context,
          ConfigurationAdmin.class,
          ConfigAdminTracker.getInstance(instance.context));
      instance.parseInitialContribution(instance.context);
      instance.tracker.open();

    }
    return instance;
  }

  public static synchronized void stop() {
    instance.tracker.close();
  }

  private void parseInitialContribution(BundleContext bundleContext) {
    try {
      final ServiceReference<?>[] admins = (ServiceReference<?>[]) context
          .getAllServiceReferences(ConfigurationAdmin.class.getName(), null);
      for (ServiceReference<?> adminRef : admins) {

        ConfigAdminTracker.getInstance(bundleContext).getAdmins()
            .add((ConfigurationAdmin) context.getService(adminRef));
      }
    } catch (InvalidSyntaxException e) {
      log.error("Error getting servicereferences of config admin", e);
    }
  }

  /**
   * Adds config
   */
  @Override
  public final Object addingService(final ServiceReference reference) {
    final ConfigurationAdmin admin = (ConfigurationAdmin) context
        .getService(reference);
    getInstance(null).admins.add(admin);
    return admin;
  }

  /**
   * Modified a config
   */
  @Override
  public final void modifiedService(
      final ServiceReference<ConfigurationAdmin> reference, Object service) {
    removedService(reference, service);
    addingService(reference);

  }

  /**
   * removed config
   */
  @Override
  public final void removedService(
      ServiceReference<ConfigurationAdmin> reference, final Object service) {
    final ConfigurationAdmin admin = context.getService(reference);
    getInstance(null).admins.remove(admin);

  }

  /**
   * Get the property with the according key
   * 
   * @param key
   *          the propertuy key
   * @return the corresponding property
   * @throws IOException
   *           read error
   * @throws InvalidSyntaxException
   *           wrong key
   */
  public String getProperty(String key) throws IOException,
      InvalidSyntaxException {
    log.info("Retreiving property key: " + key);
    for (ConfigurationAdmin admin : getInstance(null).admins) {
      final Configuration[] configurations = admin.listConfigurations(null);
      if (configurations != null) {
        for (final Configuration configuration : configurations) {
          log.debug("parsing configuration: " + configuration.getPid());
          final Dictionary<String, Object> dictionary = configuration
              .getProperties();
          final Object valObject = dictionary.get(key);
          if (valObject != null && valObject instanceof String) {
            log.trace("got value: " + valObject);
            return (String) valObject;
          }
        }
      }
    }
    return null;
  }

  public Map<String, String> getProperties() {
    Map<String, String> ret = Maps.newHashMap();
    try {
      for (ConfigurationAdmin admin : getInstance(null).admins) {
        final Configuration[] configurations = admin.listConfigurations(null);
        if (configurations != null) {
          for (final Configuration configuration : configurations) {
            log.debug("parsing configuration: " + configuration.getPid());
            final Dictionary<String, Object> dictionary = configuration
                .getProperties();
            Enumeration<String> keys = dictionary.keys();
            while (keys.hasMoreElements()) {
              String key = keys.nextElement();
              Object val = dictionary.get(key);
              if (val instanceof String) {
                ret.put(key, (String) val);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("Error retreiving configadmin property", e);
    }
    return ret;
  }

  @Override
  public List<ConfigSource> getConfigSources() {
    List<ConfigSource> ret = Lists.newArrayList();
    for (ConfigurationAdmin admin : getInstance(null).admins) {
      Configuration[] configurations;
      try {
        configurations = admin.listConfigurations(null);
        if (configurations != null) {
          for (final Configuration configuration : configurations) {
            ret.add(new ConfigSource() {

              @Override
              public boolean isScannable() {

                return true;
              }

              @Override
              public String getPropertyValue(String key) {
                final Dictionary<String, Object> dictionary = configuration
                    .getProperties();
                final Object valObject = dictionary.get(key);
                if (valObject != null && valObject instanceof String) {
                  log.trace("got value: " + valObject);
                  return (String) valObject;
                }
                return null;
              }

              @Override
              public Map<String, String> getProperties() {
                Map<String, String> ret = Maps.newHashMap();
                final Dictionary<String, Object> dictionary = configuration
                    .getProperties();
                Enumeration<String> keys = dictionary.keys();
                while (keys.hasMoreElements()) {
                  String key = keys.nextElement();
                  Object val = dictionary.get(key);
                  if (val instanceof String) {
                    ret.put(key, (String) val);
                  }
                }
                return ret;
              }

              @Override
              public int getOrdinal() {

                return 0;
              }

              @Override
              public String getConfigName() {
                // TODO Auto-generated method stub
                return configuration.getPid();
              }
            });
          }
        }
      } catch (IOException | InvalidSyntaxException e) {
        log.error("Error retreiving configadmin property", e);
      }

    }
    return ret;
  }

}
