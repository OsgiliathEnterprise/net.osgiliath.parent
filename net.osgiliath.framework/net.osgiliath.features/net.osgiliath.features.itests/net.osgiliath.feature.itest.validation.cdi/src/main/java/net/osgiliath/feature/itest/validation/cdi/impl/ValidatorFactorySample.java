package net.osgiliath.feature.itest.validation.cdi.impl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.feature.itest.validation.cdi.HelloObject;
import net.osgiliath.feature.itest.validation.cdi.IValidatorFactorySample;
import org.ops4j.pax.cdi.api.Component;
import org.ops4j.pax.cdi.api.Service;

/**
 * Validation test.
 * @author charliemordant
 */
@Component
@Service
@Slf4j
public class ValidatorFactorySample implements IValidatorFactorySample {
  /**
   * Validation of a null message.
   * @param object element to save
   */
  public void nullMessageValidation(@NotNull @Valid HelloObject object) {

    if (object != null) {
      log.error("Exception should have been thrown!" + object.toString());
    }

  }
}
