package net.osgiliath.validator.osgi;

/*
 * #%L
 * net.osgiliath.helpers.validation.osgi.services
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

import javax.validation.Validator;

import net.osgiliath.validator.osgi.internal.ValidatorFactorySingleton;

/**
 * gets the validator.
 * 
 * @author charliemordant
 */
public class ValidatorHelper {

  /**
   * private Ctor so that it can't be instantiated.
   */
  private ValidatorHelper() {
    super();
  }

  /**
   * The validator.
   * 
   * @return the singleton validator
   */
  public static Validator getValidator() {

    return ValidatorFactorySingleton.getValidatorFactory().getValidator();
  }
}
