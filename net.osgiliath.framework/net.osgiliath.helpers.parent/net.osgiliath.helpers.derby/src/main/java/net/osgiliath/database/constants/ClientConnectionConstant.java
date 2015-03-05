package net.osgiliath.database.constants;

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
/**
 * Database constants.
 * @author charliemordant
 *
 */
public final class ClientConnectionConstant {
  private ClientConnectionConstant() {
    //Do nothing
  }
  public static final String AUTO_START_SERVER = "serverAutoStart";
  public static final String CREATE_DATABASE = "createDatabase";
  public static final Object LOG_FILE_PROPERTY = "derbyServerLog";
  public static final String PAX_JDBC_DS_ID = "derbyclient";
  public static final int DEFAULT_PORT = 1527;
  public static final String DEFAULT_HOST = "localhost";
  public static final String LOG_FILE = "derbyServer.log";
}
