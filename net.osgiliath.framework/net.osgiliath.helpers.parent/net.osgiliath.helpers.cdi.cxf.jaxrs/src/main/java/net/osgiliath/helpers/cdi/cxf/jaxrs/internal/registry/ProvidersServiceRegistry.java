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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.CXFHelperActivator;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class ProvidersServiceRegistry {

	private Collection<MessageBodyWriter<Object>> writers = Sets.newHashSet();
	private Collection<MessageBodyReader<Object>> readers = Sets.newHashSet();
	private Collection<ExceptionMapper<? extends Exception>> exceptionMappers = Sets
			.newHashSet();

	@Inject
	@Any
	private Instance<MessageBodyWriter<Object>> internalWriters;
	@Inject
	@Any
	private Instance<MessageBodyReader<Object>> internalReaders;

	private static ProvidersServiceRegistry instance = null;

	public static ProvidersServiceRegistry getInstance() {
		if (instance == null) {
			instance = new ProvidersServiceRegistry();
		}
		return instance;
	}

	public Collection<MessageBodyWriter<Object>> getWriters() {

		return internalWriters == null ? writers : Sets.newHashSet(Iterables
				.concat(writers, internalWriters));
	}

	public Collection<ExceptionMapper<? extends Exception>> getExceptionMappers() {
		return exceptionMappers;
	}

	public Collection<MessageBodyReader<Object>> getReaders() {
		return internalReaders == null ? readers : Sets.newHashSet(Iterables
				.concat(readers, internalReaders));
	}

	public Collection<Object> getProviders() {
		return Sets.newHashSet(Iterables.concat(Iterables.concat(
				Iterables.concat(getReaders(), getWriters()),
				getInternalProviders()), getExceptionMappers()));
	}

	private Iterable<? extends Object> getInternalProviders() {

		return Sets.<Object> newHashSet(new JSONProvider<Object>(),
				new JAXBElementProvider<Object>());
	}

}
