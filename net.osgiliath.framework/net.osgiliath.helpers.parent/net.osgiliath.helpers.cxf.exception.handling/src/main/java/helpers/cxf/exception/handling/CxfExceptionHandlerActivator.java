package helpers.cxf.exception.handling;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import helpers.cxf.exception.handling.jaxrs.mapper.ExceptionXmlMapper;

import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/*
 * #%L
 * net.osgiliath.helpers.cxf.exception.handling
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

public class CxfExceptionHandlerActivator implements BundleActivator{
	@Override
	public void start(BundleContext context) throws Exception {
		ExceptionXmlMapper exceptionXmlMapperService = new ExceptionXmlMapper();
		Dictionary<String, String> filter = new Hashtable<String, String>();
		filter.put("mapper.type", "xmlExceptionInBodyResponse");
		context.registerService(ExceptionMapper.class, exceptionXmlMapperService, filter);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Collection<ServiceReference<ExceptionMapper>> references = context.getServiceReferences(ExceptionMapper.class, "(mapper.type=xmlExceptionInBodyResponse)");
		for (ServiceReference<ExceptionMapper> reference : references) {
			context.ungetService(reference);
		}
	}

}
