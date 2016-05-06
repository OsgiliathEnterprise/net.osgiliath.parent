package net.osgiliath.module.deltaspike.configadmin.karaf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.apache.deltaspike.core.spi.config.ConfigSourceProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.cdi.weld.api.SingleServiceTracker;

/**
 * Karaf configuration admin listener.
 * 
 * @author charliemordant
 *
 */
@Slf4j
public class KarafConfigAdminListener implements ConfigurationListener,
    SingleServiceTracker.SingleServiceListener, ConfigSourceProvider {
  /**
   * Singleton instance.
   */
  private static KarafConfigAdminListener instance;
  /**
   * OSGi BundleContext.
   */
  private BundleContext bundleContext;
  /**
   * Config admin service listener.
   */
  private ServiceRegistration<ConfigurationListener> registration;
  /**
   * Service tracker.
   */
  private SingleServiceTracker configAdminTracker;
  /**
   * Cached configurations.
   */
  private Collection<Configuration> configurations;
  /**
   * Deltaspike properties to skip.
   */
  private Collection<String> skippedProperties = new HashSet<>(Arrays.asList(
      "org.apache.deltaspike.core.spi.activation.ClassDeactivator",
      "org.apache.deltaspike.ProjectStage", "javax.faces.PROJECT_STAGE",
      "faces.PROJECT_STAGE",
      "deltaspike.bean-manager.delegate_lookup.Production",
      "deltaspike.bean-manager.delegate_lookup",
      "deltaspike.interceptor.priority.Production",
      "deltaspike.interceptor.priority"));

  /**
   * Ctor. Initializes the cache.
   */
  public KarafConfigAdminListener() {
    if (null == configurations)
      this.configurations = new HashSet<>();
  }

  /**
   * Ctor taking osgi context. initializes service listeners.
   * 
   * @param bundleContext
   *          the OSGi {@link BundleContext}
   * @throws InvalidSyntaxException
   *           service registration error.
   */
  public KarafConfigAdminListener(BundleContext bundleContext)
      throws InvalidSyntaxException {
    this();
    getInstance().bundleContext = bundleContext;
    getInstance().registration = bundleContext
        .registerService(ConfigurationListener.class, this, null);
    getInstance().configAdminTracker = new SingleServiceTracker<>(bundleContext,
        ConfigurationAdmin.class, this);
    getInstance().configAdminTracker.open();

  }

  /**
   * The singleton instance.
   * 
   * @return the instance.
   */
  public static KarafConfigAdminListener getInstance() {
    if (null == instance) {
      instance = new KarafConfigAdminListener();
    }
    return instance;
  }

  /**
   * Stops listening.
   */
  public void stop() {
    getInstance().registration.unregister();
    getInstance().configAdminTracker.close();
    getInstance().configAdminTracker = null;
    getInstance().registration = null;
    getInstance().bundleContext = null;

  }

  @Override
  public void configurationEvent(ConfigurationEvent event) {
    try {
      switch (event.getType()) {
      case ConfigurationEvent.CM_DELETED:
        reparse();
        break;
      case ConfigurationEvent.CM_UPDATED:
        reparse();
        break;
      default:
        break;
      }
    } catch (Exception e) {

      log.error("Problem processing Configuration Event {}", event, e);
    }

  }

  @Override
  public void serviceFound() {

    getInstance().reparse();
  }

  /**
   * Reparses config admin.
   */
  public void reparse() {
    try {
      ConfigResolver.freeConfigSources();
      internalReparse();
    } catch (Exception e) {
      log.error("error parsing configadmin", e);

    }
  }

  /**
   * internal function to reparse.
   * 
   * @throws IOException
   *           configuration read error.
   * @throws InvalidSyntaxException
   *           service listening error.
   */
  public void internalReparse() throws IOException, InvalidSyntaxException {
    ConfigurationAdmin configAdmin = (ConfigurationAdmin) getInstance().configAdminTracker
        .getService();
    if (null != configAdmin) {
      Configuration[] configs = configAdmin.listConfigurations(null);
      if (configs != null) {
        log.debug("parsing configuration admin sources");
        if (null != getInstance().configurations) {
          getInstance().configurations.clear();
          for (Configuration config : configs) {
            log.debug("adding configuration to deltaspike configadmin with pid "
                + config.getPid());
            getInstance().configurations.add(config);
          }
        }
      }
    }
  }

  @Override
  public void serviceLost() {
    getInstance().reparse();
  }

  @Override
  public void serviceReplaced() {
    getInstance().reparse();
  }

  @Override
  public List<ConfigSource> getConfigSources() {
    List<ConfigSource> ret = new ArrayList<>();
    if (null != getInstance().configurations) {
      for (Configuration config : getInstance().configurations) {
        ret.add(new ConfigSource() {

          @Override
          public boolean isScannable() {

            return true;
          }

          @Override
          public String getPropertyValue(String key) {

            log.debug("Osgiliath: retreiving property: " + key
                + ", from CM factory pid: " + config.getPid());
            String res = getConfigValue(config, key);
            if (null != res) {
              log.info("ds configadmin retreived value: " + res);
              return res;
            }
            if (getInstance().configurations.size() == ret.size()
                && this.equals(ret.get(ret.size() - 1))
                && !skippedProperties.contains(key)) {
              int cpt = 0;
              while (cpt < 10) {
                try {
                  internalReparse();
                  for (Configuration config : getInstance().configurations) {
                    res = getConfigValue(config, key);
                    if (null != res) {

                      return res;
                    }
                  }
                  try {// fucking async configadmin
                    Thread.sleep(1000);
                  } catch (InterruptedException e) {
                    log.error("error waiting config update", e);
                  }
                } catch (IOException | InvalidSyntaxException e1) {
                  log.error("error waiting config update", e1);

                }
                cpt += 1;
              }
            }
            return null;
          }

          public String getConfigValue(Configuration config, String key) {
            if (null != config && null != config.getProperties()) {
              final Object valObject = config.getProperties().get(key);
              if (valObject instanceof String) {
                return (String) valObject;
              }
            }
            return null;
          }

          @Override
          public Map<String, String> getProperties() {

            final Map<String, String> ret = new HashMap<>();
            final Dictionary<String, ?> dictionary = config.getProperties();
            ;
            if (null != dictionary) {
              final Enumeration<String> keys = dictionary.keys();
              if (null != keys) {
                while (keys.hasMoreElements()) {
                  final String key = keys.nextElement();
                  final Object val = dictionary.get(key);
                  if (val instanceof String) {
                    ret.put(key, (String) val);
                  }
                }
              }
            }
            return ret;

          }

          @Override
          public int getOrdinal() {

            return 407;
          }

          @Override
          public String getConfigName() {
            return config.getPid();

          }
        });
      }
    }
    return ret;
  }

}
