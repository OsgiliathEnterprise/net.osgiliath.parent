package conf;

/*
 * #%L
 * CDI configured business module
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

import javax.enterprise.inject.Produces;
import javax.ws.rs.ApplicationPath;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.helpers.deltaspike.configadmin.internal.ConfigAdminTracker;
import net.osgiliath.helpers.swagger.cdi.CXFBeanJaxrsScanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
/**
 * Default Swagger bean configuration.
 * @author charliemordant
 *
 */
@Slf4j
public class SwaggerBeanConfig {
  /**
   * Configuration producer
   * @return the configuration
   */
  @Produces
  public BeanConfig getConfig() {
    final BeanConfig beanConfig = new CXFBeanJaxrsScanner(this.getClass()
        .getClassLoader());
    final BundleContext context = FrameworkUtil.getBundle(this.getClass())
        .getBundleContext();
    final ConfigAdminTracker tracker = ConfigAdminTracker.getInstance(context);
    String endPointURI = "";
    try {
         endPointURI = tracker.getProperty(
        "jaxrs.server.protocol") + "://" + tracker.getProperty("jaxrs.server.uri") + ":" + tracker.getProperty("jaxrs.server.port")  + "/cxf" + CXFApplication.class.getAnnotation(ApplicationPath.class).value();
      beanConfig.setBasePath(endPointURI
         );
    }
    catch (IOException | InvalidSyntaxException e) {
      log.error("Error configuring Swagger bean", e);
    }
    log.info("Swagger bean configuration started for endpoint: " + endPointURI);
    beanConfig.setResourcePackage("net.osgiliath.features.karaf.jaxrs.cdi");
    beanConfig.setScan(true);
    return beanConfig;
  }

}
