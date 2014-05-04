package net.osgiliath.helpers.cdi.cxf.jaxrs;

/*
 * #%L
 * net.osgiliath.helpers.cdi.cxf.jaxrs
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.apache.cxf.message.Message;

/**
 * 
 * @author charliemordant CXF endpoint annotation
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface CXFEndpoint {
    /**
     * Service url
     * 
     * @return the service endpoint url
     */
    @Nonbinding
    String url() default "";

    /**
     * Factory name (gets or creates this one)
     * 
     * @return the factory name
     */
    @Nonbinding
    String factoryId() default "defaultCDIFactory";

    /**
     * Message providers
     * 
     * @return interceptors classes (will automatically added to factory if not)
     */
    @Nonbinding
    Class<? extends Object>[] providersClasses() default {
	    JAXBElementProvider.class, JSONProvider.class };

    /**
     * In interceptors classes
     * @return interceptors classes (will automatically added to factory if not)
     */
    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] inInterceptors() default {};
    /**
     * out interceptors classes
     * @return interceptors classes (will automatically added to factory if not)
     */
    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] outInterceptors() default {};
    /**
     * In fault interceptors classes
     * @return interceptors classes (will automatically added to factory if not)
     */
    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] inFaultInterceptors() default {};
    /**
     * out fault interceptors classes
     * @return interceptors classes (will automatically added to factory if not)
     */
    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] outFaultInterceptors() default {};
}
