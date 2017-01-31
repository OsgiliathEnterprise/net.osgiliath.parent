package net.osgiliath.module.derby.impl;

/*
 * #%L
 * Wrapper for apache derby database
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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.jdbc.ClientDriver;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

import net.osgiliath.module.derby.constants.ClientConnectionConstant;

/**
 * Pax-JDBC derby client activator.
 * 
 * @author charliemordant
 *
 */
public class Activator implements BundleActivator {
  /**
   * Started derby servers.
   */
  private final Map<String, Map<Integer, NetworkServerControl>> startedServers = new HashMap<>();
  /**
   * Singleton.
   */
  private static Activator instance;

 private ServiceRegistration<?> dsfR;
  /**
   * Start method.
   * 
   * @param context
   *          the bundle context
   */
  @Override
  public void start(BundleContext context) throws Exception {
    final DerbyClientDatasourceFactory dsf = new DerbyClientDatasourceFactory();
    final Dictionary<String, String> props = new Hashtable<String, String>();
    props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS,
        ClientDriver.class.getName());
    props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
        ClientConnectionConstant.PAX_JDBC_DS_ID);
    dsfR = context.registerService(DataSourceFactory.class.getName(), dsf, props);
    instance = this;
  }

  /**
   * Stop method.
   * 
   * @param context
   *          the bundle context
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    for (final Map<Integer, NetworkServerControl> controls : getInstance().startedServers
        .values()) {
      for (final NetworkServerControl control : controls.values()) {
        control.shutdown();
      }
    }
    getInstance().startedServers.clear();
    dsfR.unregister();
  }

  /**
   * Singleton.
   * 
   * @return the singleton
   */
  protected static Activator getInstance() {
    return instance;
  }

  /**
   * Gets the started derby servers.
   * 
   * @return the started derby servers
   */
  protected Map<String, Collection<Integer>> getStartedServers() {
    final Map<String, Collection<Integer>> ret = new HashMap<>();
    for (final Entry<String, Map<Integer, NetworkServerControl>> keys : this.startedServers
        .entrySet()) {
      ret.put(keys.getKey(), keys.getValue().keySet());

    }
    return ret;
  }

  /**
   * Adds a network control for a server.
   * 
   * @param host
   *          DB host
   * @param port
   *          DB port
   * @param control
   *          Network control
   */
  protected void addNetworkControl(String host, int port,
      NetworkServerControl control) {
    Map<Integer, NetworkServerControl> candidate = this.startedServers
        .get(host);
    if (candidate == null) {
      candidate = new HashMap<>();
      this.startedServers.put(host, candidate);
    }
    candidate.put(port, control);
  }

}
