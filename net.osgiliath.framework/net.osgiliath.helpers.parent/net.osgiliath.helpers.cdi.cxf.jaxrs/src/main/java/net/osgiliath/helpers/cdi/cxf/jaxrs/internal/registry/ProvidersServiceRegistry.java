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

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
/**
 * 
 * @author charliemordant
 * CXF providers registry
 */
public class ProvidersServiceRegistry {
    /**
     * Message body writers
     */
    private Collection<MessageBodyWriter<Object>> writers = Sets.newHashSet();
    /**
     * Message body readers
     */
    private Collection<MessageBodyReader<Object>> readers = Sets.newHashSet();
    /**
     * Exception mappers
     */
    private Collection<ExceptionMapper<? extends Exception>> exceptionMappers = Sets
	    .newHashSet();
    /**
     * Internally declared writers
     */
    @Inject
    @Any
    private Instance<MessageBodyWriter<Object>> internalWriters;
    /**
     * bundle declared readers
     */
    @Inject
    @Any
    private Instance<MessageBodyReader<Object>> internalReaders;
    /**
     * singleton instance
     */
    private static ProvidersServiceRegistry instance = null;
    /**
     * singleton
     * @return the singleton
     */
    public static ProvidersServiceRegistry getInstance() {
	if (instance == null) {
	    instance = new ProvidersServiceRegistry();
	}
	return instance;
    }
    /**
     * 
     * @return all writers
     */
    public Collection<MessageBodyWriter<Object>> getWriters() {

	return this.internalWriters == null ? this.writers : Sets.newHashSet(Iterables
		.concat(this.writers, this.internalWriters));
    }
    /**
     * 
     * @return all exception mappers
     */
    public Collection<ExceptionMapper<? extends Exception>> getExceptionMappers() {
	return this.exceptionMappers;
    }
    /**
     * 
     * @return all body readers
     */
    public Collection<MessageBodyReader<Object>> getReaders() {
	return this.internalReaders == null ? this.readers : Sets.newHashSet(Iterables
		.concat(this.readers, this.internalReaders));
    }
    /**
     * 
     * @return all providers
     */
    public Collection<Object> getProviders() {
	return Sets.newHashSet(Iterables.concat(Iterables.concat(
		Iterables.concat(this.getReaders(), this.getWriters()),
		this.getInternalProviders()), this.getExceptionMappers()));
    }
    /**
     * 
     * @return all internal providers
     */
    private Iterable<? extends Object> getInternalProviders() {

	return Sets.<Object> newHashSet(new JSONProvider<Object>(),
		new JAXBElementProvider<Object>());
    }

}
