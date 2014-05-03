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

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface CXFEndpoint {
    @Nonbinding
    String url() default "";

    @Nonbinding
    String factoryId() default "defaultCDIFactory";

    @Nonbinding
    Class<? extends Object>[] providersClasses() default {
	    JAXBElementProvider.class, JSONProvider.class };

    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] inInterceptors() default {};

    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] outInterceptors() default {};

    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] inFaultInterceptors() default {};

    @Nonbinding
    Class<? extends Interceptor<? extends Message>>[] outFaultInterceptors() default {};
}
