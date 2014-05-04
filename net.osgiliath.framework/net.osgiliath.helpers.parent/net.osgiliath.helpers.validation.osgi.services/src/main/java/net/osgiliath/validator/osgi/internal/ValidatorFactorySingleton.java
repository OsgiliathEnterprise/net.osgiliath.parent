package net.osgiliath.validator.osgi.internal;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.ProviderSpecificBootstrap;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
/**
 * 
 * @author charliemordant
 * The validator factory singleton
 */
public class ValidatorFactorySingleton {
    /**
     * factory singleton
     */
    private static ValidatorFactory validatorFactory = null;
    /**
     * 
     * @return the singleton validator
     */
    public static ValidatorFactory getValidatorFactory() {
	if (validatorFactory == null) {

	    final ProviderSpecificBootstrap<HibernateValidatorConfiguration> validationBootStrap = Validation
		    .byProvider(HibernateValidator.class);

	    // bootstrap to properly resolve in an OSGi environment
	    validationBootStrap
		    .providerResolver(HibernateValidationOSGIServicesProviderResolver
			    .getInstance());

	    final HibernateValidatorConfiguration configure = validationBootStrap
		    .configure();
	    validatorFactory = configure/*
					 * .constraintValidatorFactory (new
					 * CDIAwareConstraintValidatorFactory
					 * ())
					 */.buildValidatorFactory();
	}
	return validatorFactory;
    }
}
