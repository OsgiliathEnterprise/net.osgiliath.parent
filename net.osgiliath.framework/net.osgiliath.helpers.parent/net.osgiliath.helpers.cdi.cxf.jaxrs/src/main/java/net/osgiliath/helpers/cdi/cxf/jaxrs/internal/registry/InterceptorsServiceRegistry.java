package net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry;

/*
 * #%L
 * net.osgiliath.helpers.cdi.cxf.jaxrs
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

import java.util.Collection;

import org.apache.cxf.interceptor.Interceptor;

import com.google.common.collect.Sets;
/**
 * 
 * @author charliemordant
 * JaxRS endpoints registry
 */
public class InterceptorsServiceRegistry {
    /**
     * Singleton instance
     */
    private static InterceptorsServiceRegistry instance = null;

    /**
     * Interceptor registry
     */
    private Collection<Interceptor> interceptors = Sets.newHashSet();
    /**
     * 
     * @return the singleton instance
     */
    public static InterceptorsServiceRegistry getInstance() {
	if (instance == null) {
	    instance = new InterceptorsServiceRegistry();
	}
	return instance;
    }
    /**
     * Getter
     * @return interceptors
     */
    public Collection<Interceptor> getInterceptors() {
	return this.interceptors;
    }


}
