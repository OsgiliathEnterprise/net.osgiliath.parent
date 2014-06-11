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
/**
 * 
 * @author charliemordant
 * CXF CDI extension OSGI activator
 */
public class CXFHelperActivator implements BundleActivator {
    /**
     * Message body reader service tracker
     */
    private ServiceTracker readerTracker;
    /**
     * Message body writer service tracker
     */
    private ServiceTracker writerTracker;
    /**
     * Exception mapper service tracker
     */
    private ServiceTracker exceptionTracker;
    /**
     * Interceptors service tracker
     */
    private ServiceTracker interceptorsTracker;
    /**
     * Start method
     */
    @Override
    public void start(BundleContext context) throws Exception {
	this.readerTracker = new ServiceTracker<>(context, MessageBodyReader.class,
		new MessageBodyReaderProvidersServiceTracker(context));
	this.readerTracker.open(true);
	this.writerTracker = new ServiceTracker(context, MessageBodyWriter.class,
		new MessageBodyWriterProvidersServiceTracker(context));
	this.writerTracker.open(true);
	this.exceptionTracker = new ServiceTracker(context, ExceptionMapper.class,
		new ExceptionMapperProvidersServiceTracker(context));
	this.exceptionTracker.open(true);
	this.interceptorsTracker = new ServiceTracker(context, Interceptor.class,
		new InterceptorsServiceTracker(context));
	this.interceptorsTracker.open(true);
	MessageBodyReaderProvidersServiceTracker
		.handleInitialReferences(context);
	MessageBodyWriterProvidersServiceTracker
		.handleInitialReferences(context);
	ExceptionMapperProvidersServiceTracker.handleInitialReferences(context);
	InterceptorsServiceTracker.handleInitialReferences(context);

    }
    /**
     * Activator close
     */
    @Override
    public void stop(BundleContext context) throws Exception {
	this.readerTracker.close();
	this.writerTracker.close();
	this.exceptionTracker.close();
	this.interceptorsTracker.close();
    }

}
