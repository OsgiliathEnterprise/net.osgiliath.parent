package net.osgiliath.helper.deltaspike.configadmin.internal;

/*
 * #%L
 * Helper configAdmin cdi support
 * %%
 * Copyright (C) 2013 - 2015 Osgiliath
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

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.FrameworkUtil;
import net.osgiliath.helper.deltaspike.configadmin.ConfigAdminAccessor;
import java.util.Map;
import org.apache.deltaspike.core.spi.config.ConfigSource;
@Slf4j
public class ConfigAdminConfigSource implements ConfigSource {

  @Override
  public int getOrdinal() {
   
    return 0;
  }

  @Override
  public Map<String, String> getProperties() {
    
    return ConfigAdminAccessor.getProperties(FrameworkUtil.getBundle(ConfigAdminConfigSource.class).getBundleContext());
  }

  @Override
  public String getPropertyValue(String key) {
   
    try {
      return ConfigAdminAccessor.getProperty(FrameworkUtil.getBundle(ConfigAdminConfigSource.class).getBundleContext(), key);
    }
    catch (IOException e) {
      log.error("IO exception while querying the config admin accessor for a property", e);
    }
    catch (InvalidSyntaxException e) {
      log.error("Invalid syntax exception while querying the config admin accessor for a property", e);
    }
    return null;
  }

  @Override
  public String getConfigName() {
    
    return "configAdmin";
  }

  @Override
  public boolean isScannable() {
    return false;
  }

}
