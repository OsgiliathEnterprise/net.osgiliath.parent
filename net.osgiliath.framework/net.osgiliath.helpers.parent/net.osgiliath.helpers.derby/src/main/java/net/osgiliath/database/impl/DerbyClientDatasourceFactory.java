package net.osgiliath.database.impl;

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

import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.database.constants.ClientConnectionConstant;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.jdbc.ClientConnectionPoolDataSource40;
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.ClientDataSource40;
import org.apache.derby.jdbc.ClientDriver40;
import org.apache.derby.jdbc.ClientXADataSource40;
import org.osgi.service.jdbc.DataSourceFactory;
/**
 * DS factory.
 * @author charliemordant
 *
 */
@Slf4j
public class DerbyClientDatasourceFactory implements DataSourceFactory {
  /**
   * creates a datasource from properties.
   * @param props ds properties
   * @return The datasource
   */
  @Override
  public DataSource createDataSource(Properties props) throws SQLException {
    final ClientDataSource40 ds = new ClientDataSource40();
    setProperties(ds, props);
    return ds;
  }
  /**
   * Sets properties for datasource.
   * @param ds the DS to update
   * @param properties properties to set
   * @throws SQLException in case of unsupported DS Operations
   */
  private void setProperties(ClientDataSource ds, Properties properties)
      throws SQLException {
    final Properties props = (Properties) properties.clone();
    final String doStartServer = (String) props
        .remove(ClientConnectionConstant.AUTO_START_SERVER);
    if (doStartServer != null && Boolean.parseBoolean(doStartServer)) {
        this.doStartServer(props);
    }
   final String databaseName = (String) props
        .remove(DataSourceFactory.JDBC_DATABASE_NAME);
    if (databaseName == null) {
      throw new SQLException("missing required property "
          + DataSourceFactory.JDBC_DATABASE_NAME);
    }
    ds.setDatabaseName(databaseName);

    final String password = (String) props.remove(DataSourceFactory.JDBC_PASSWORD);
    ds.setPassword(password);

    final String user = (String) props.remove(DataSourceFactory.JDBC_USER);
    ds.setUser(user);

    final String createDatabase = (String) props
        .remove(ClientConnectionConstant.CREATE_DATABASE);
    ds.setCreateDatabase(createDatabase);

    final String serverName = (String) props
        .remove(DataSourceFactory.JDBC_SERVER_NAME);
    ds.setServerName(serverName);
    final String portNumber = (String) props
        .remove(DataSourceFactory.JDBC_PORT_NUMBER);
    if (portNumber != null) {
      ds.setPortNumber(Integer.parseInt(portNumber));
    }
    else {
      ds.setPortNumber(ClientConnectionConstant.DEFAULT_PORT);
    }
  }
  /**
   * Starts the databsases servers.
   * @param properties server's properties.
   */
  private void doStartServer(Properties properties) {
    String host = (String) properties.get(DataSourceFactory.JDBC_SERVER_NAME);
    if (host == null) {
      host = ClientConnectionConstant.DEFAULT_HOST;
    }
    final String portNumberS = (String) properties
        .get(DataSourceFactory.JDBC_PORT_NUMBER);
    final int portNumber = portNumberS == null ? ClientConnectionConstant.DEFAULT_PORT : Integer.parseInt(portNumberS);
    boolean alreadyStarted = false;
    if (Activator.getInstance().getStartedServers().containsKey(host)) {
      alreadyStarted = Activator.getInstance().getStartedServers().get(host)
          .contains(portNumber);
    }
    if (!alreadyStarted) {
      try {
        final InetAddress adress = InetAddress.getByName(host);
        final NetworkServerControl control = new NetworkServerControl(adress,
            portNumber);
        String writer = (String) properties
            .remove(ClientConnectionConstant.LOG_FILE_PROPERTY);
        if (writer == null) {
          writer = ClientConnectionConstant.LOG_FILE;
        }
        final PrintWriter printWriter = new PrintWriter(writer);
        control.start(printWriter);
        Activator.getInstance().addNetworkControl(host, portNumber, control);
      } catch (Exception e) {
        log.error("Error creating host adress", e);
      }
    }

  }
  /**
   * Creates pooling datasources.
   * @param props properties
   * @return the datasource
   */
  @Override
  public ConnectionPoolDataSource createConnectionPoolDataSource(
      Properties props) throws SQLException {
    ClientConnectionPoolDataSource40 ds = new ClientConnectionPoolDataSource40();
    setProperties(ds, props);

    return ds;
  }
  /**
   * Creates XA datasources.
   * @param props properties
   * @return the datasource
   */
  @Override
  public XADataSource createXADataSource(Properties props) throws SQLException {
    ClientXADataSource40 ds = new ClientXADataSource40();
    setProperties(ds, props);
    return ds;
  }
  /**
   * Creates Client driver.
   * @param props properties
   * @return the DS Driver
   */
  @Override
  public Driver createDriver(Properties props) throws SQLException {
    ClientDriver40 driver = new ClientDriver40();

    return driver;
  }

}
