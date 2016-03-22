package net.osgiliath.feature.itest.validation.cdi.conf;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.validation
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

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.validation.Validator;

import net.osgiliath.validator.osgi.ValidatorHelper;

/**
 * CDI bootstrap configuration class.
 * @author charliemordant
 */
public class CDIValidator {
  /**
   * cdi injected validator.
   * 
   * @return the validator (with good classloader)
   */
  @Produces
  @org.hibernate.validator.cdi.HibernateValidator
  @Default
  public final Validator createValidator() {
    return ValidatorHelper.getValidator();
  }
}
