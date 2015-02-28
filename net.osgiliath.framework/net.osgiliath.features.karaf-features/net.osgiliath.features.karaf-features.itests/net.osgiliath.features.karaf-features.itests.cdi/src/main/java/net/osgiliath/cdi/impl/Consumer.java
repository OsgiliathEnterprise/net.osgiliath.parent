package net.osgiliath.cdi.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.cdi
 * %%
 * Copyright (C) 2013 Osgiliath corp
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

import javax.inject.Inject;

import net.osgiliath.cdi.IConsumer;
import net.osgiliath.cdi.IProvider;

import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 * Consumes provider's message
 * @author charliemordant Consumer of injected bean
 */
@OsgiServiceProvider(classes = { IConsumer.class })
public class Consumer implements IConsumer {
  /**
   * Injected CDI provider
   */
  @Inject
  @OsgiService(timeout = 2000, dynamic = true)
  private transient IProvider provider;

  /**
   * Consumed method by itests
   */
  public final String getHello() {
    return this.provider.getMessage();
  }
}
