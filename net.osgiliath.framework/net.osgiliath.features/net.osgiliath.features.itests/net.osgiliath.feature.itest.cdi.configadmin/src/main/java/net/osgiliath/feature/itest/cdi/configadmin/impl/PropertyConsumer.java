package net.osgiliath.feature.itest.cdi.configadmin.impl;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.feature.itest.cdi.configadmin.api.IPropertyConsumer;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.cdi.properties
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
 * Provides property.
 * @author charliemordant
 *
 */
@OsgiServiceProvider
@Slf4j
public class PropertyConsumer implements IPropertyConsumer {

	
  /*
   * (non-Javadoc)
   * 
   * @see net.osgiliath.cdi.properties.impl.IConsumer#getInjectedProperty()
   */
  @Override
  public final String getInjectedProperty() {
	  	log.info("Osgiliath: retreiving injected property");
		return ConfigResolver.getPropertyValue("injectedProperty");
  }

}
