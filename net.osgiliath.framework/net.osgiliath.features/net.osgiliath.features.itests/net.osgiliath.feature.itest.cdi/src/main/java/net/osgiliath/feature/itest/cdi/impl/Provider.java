package net.osgiliath.feature.itest.cdi.impl;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;

import net.osgiliath.feature.itest.cdi.IProvider;

/**
 * Message provider.
 * @author charliemordant CDI provider class
 */
@OsgiServiceProvider
public class Provider implements IProvider {
  /**
   * CDI consumed method.
   */
  @Override
  public final String getMessage() {
    return "hello";
  }

}
