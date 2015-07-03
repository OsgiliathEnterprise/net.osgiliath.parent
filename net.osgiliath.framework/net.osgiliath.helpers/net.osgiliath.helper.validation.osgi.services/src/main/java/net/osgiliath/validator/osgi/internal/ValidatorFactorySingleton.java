package net.osgiliath.validator.osgi.internal;

/*
 * #%L
 * Helper for JSR303 (bean validation) with OSGI
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

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.ProviderSpecificBootstrap;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

/**
 * The validator factory singleton.
 * @author charliemordant
 */
public class ValidatorFactorySingleton {
  /**
   * factory singleton.
   */
  private static transient ValidatorFactory validatorFactory = null;

  /**
   * returns the validatorfactory.
   * @return the singleton validatorfactory
   */
  public static synchronized ValidatorFactory getValidatorFactory() {
    if (validatorFactory == null) {
      final ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
          .byProvider(HibernateValidator.class);

      // bootstrap to properly resolve in an OSGi environment
      validationBootStrap
          .providerResolver(HibernateValidationOSGIServicesProviderResolver
              .getInstance());

      final HibernateValidatorConfiguration configure = validationBootStrap
          .configure();
      validatorFactory = configure./*constraintValidatorFactory (new
                                    CDIAwareConstraintValidatorFactory ())
                                   .*/buildValidatorFactory();
    }
    return validatorFactory;
  }
}
