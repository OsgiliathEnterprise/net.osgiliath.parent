package net.osgiliath.helpers.cdi.cxf.jaxrs.internal;

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

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.trackers.ExceptionMapperProvidersServiceTracker;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.trackers.InterceptorsServiceTracker;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.trackers.MessageBodyReaderProvidersServiceTracker;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.trackers.MessageBodyWriterProvidersServiceTracker;

import org.apache.cxf.interceptor.Interceptor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

public class CXFHelperActivator implements BundleActivator {
	private ServiceTracker readerTracker;
	private ServiceTracker writerTracker;
	private ServiceTracker exceptionTracker;
	private InterceptorsServiceTracker interceptorsTracker;

	@Override
	public void start(BundleContext context) throws Exception {
		readerTracker = new ServiceTracker<>(context, MessageBodyReader.class,
				new MessageBodyReaderProvidersServiceTracker(context));
		readerTracker.open(true);
		writerTracker = new ServiceTracker(context, MessageBodyWriter.class,
				new MessageBodyWriterProvidersServiceTracker(context));
		writerTracker.open(true);
		exceptionTracker = new ServiceTracker(context, ExceptionMapper.class,
				new ExceptionMapperProvidersServiceTracker(context));
		exceptionTracker.open(true);
		interceptorsTracker = new InterceptorsServiceTracker(context,
				Interceptor.class, null);
		interceptorsTracker.open(true);
		MessageBodyReaderProvidersServiceTracker
				.handleInitialReferences(context);
		MessageBodyWriterProvidersServiceTracker
				.handleInitialReferences(context);
		ExceptionMapperProvidersServiceTracker.handleInitialReferences(context);
		InterceptorsServiceTracker.handleInitialReferences(context);

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		readerTracker.close();
		writerTracker.close();
		exceptionTracker.close();
		interceptorsTracker.close();
	}

}
