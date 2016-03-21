package conf;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.messaging.cdi
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.camel.Component;

import org.apache.camel.component.properties.DefaultPropertiesParser;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.properties.PropertiesParser;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.ops4j.pax.cdi.api.OsgiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Components configuration with CDI.
 * @author charliemordant
 */
@ApplicationScoped
public class CDIMessagingComponents {
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory
      .getLogger(CDIMessagingComponents.class);
  /**
   * OSGI service import.
   */
  @Inject
  @OsgiService(filter = "(component-type=jms)", dynamic = true)
  private transient Component jms;

  /**
   * Producer.
   * @return the jms component
   */
  @Produces
  @Named("jms")
  public final Component getJms() {
    LOG.info("Inject jms route");
    return this.jms;
  }
  
//  @Produces
//  @ApplicationScoped
//  @Named("properties")
//  PropertiesComponent properties(PropertiesParser parser) {
//      PropertiesComponent component = new PropertiesComponent();
//      component.setPropertiesParser(parser);
//      return component;
//  }
//   
//  // PropertiesParser bean that uses DeltaSpike to resolve properties
//  static class DeltaSpikeParser extends DefaultPropertiesParser {
//      @Override
//      public String parseProperty(String key, String value, Properties properties) {
//          return ConfigResolver.getPropertyValue(key);
//      }
//  }

}
