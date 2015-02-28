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

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.osgi.framework.BundleContext;

/**
 * Activator for config admin listener.
 * @author charliemordant
 */
@Slf4j
public class CdiConfigAdminServiceStarter {
  /**
   * Configadmin service tracker
   */
  @Setter
  private transient BundleContext context;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
   */
  public final void start(final BundleContext bundleContext) throws Exception {
    this.context = bundleContext;
    ConfigAdminTracker.getInstance(this.context);

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext )
   */
  public final void stop(final BundleContext context) throws Exception {
    this.context = null;
    ConfigAdminTracker.stop();
  }

}
