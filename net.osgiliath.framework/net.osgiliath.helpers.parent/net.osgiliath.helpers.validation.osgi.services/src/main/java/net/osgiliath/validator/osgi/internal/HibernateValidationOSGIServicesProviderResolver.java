package net.osgiliath.validator.osgi.internal;

/*
 * #%L
 * helpers.validation.osgi.services
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

import java.util.List;

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

import org.hibernate.validator.HibernateValidator;

import com.google.common.collect.Lists;

/**
 * OSGi classpath aware {@link javax.validation.ValidationProviderResolver
 * ValidationProviderResolver}.
 * 
 */
public class HibernateValidationOSGIServicesProviderResolver implements
    ValidationProviderResolver {
  /**
   * Singleton instance
   */
  private static ValidationProviderResolver instance = null;
  /**
   * Validation providers
   */
  private List<ValidationProvider<?>> providers = Lists.newArrayList();

  /**
   * private CTor
   */
  private HibernateValidationOSGIServicesProviderResolver() {
    super();

  }

  /**
   * Singleton
   * 
   * @return the Singleton instance
   */
  public synchronized static ValidationProviderResolver getInstance() {
    if (instance == null) {
      instance = new HibernateValidationOSGIServicesProviderResolver();
      ((HibernateValidationOSGIServicesProviderResolver) instance).providers
          .add(new HibernateValidator());
    }
    return instance;
  }

  /**
   * gets providers.
   * @return the validation providers
   */
  @Override
  public List<ValidationProvider<?>> getValidationProviders() {
    return this.providers;
  }

}
