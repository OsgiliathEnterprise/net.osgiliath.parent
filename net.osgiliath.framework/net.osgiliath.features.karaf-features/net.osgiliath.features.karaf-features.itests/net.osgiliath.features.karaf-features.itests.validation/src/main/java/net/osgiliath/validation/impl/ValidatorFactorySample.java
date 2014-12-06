package net.osgiliath.validation.impl;

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

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.validation.HelloObject;
import net.osgiliath.validation.IValidatorFactorySample;

/**
 * 
 * @author charliemordant Validation test
 */
@Slf4j
public class ValidatorFactorySample implements IValidatorFactorySample {
  @Setter
  private Validator validator;

  /**
   * Validation of a null message
   */
  public void nullMessageValidation(HelloObject object) {

    final Set<ConstraintViolation<HelloObject>> validationResults = validator
        .validate(object);
    if (!validationResults.isEmpty()) {
      throw new ConstraintViolationException(
          new HashSet<ConstraintViolation<?>>(validationResults));
    }
    if (object != null) {
      // Exception must have be thrown
      System.out.println(object.toString());
    }

  }
}
