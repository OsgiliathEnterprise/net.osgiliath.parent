package net.osgiliath.helpers.swagger.cdi;

/*
 * #%L
 * net.osgiliath.helpers.swagger.cdi
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
import java.util.Dictionary;
import java.util.Hashtable;

import javax.ws.rs.ext.MessageBodyWriter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
/**
 * Exports swagger readers and writers service for CDI modules.
 * @author charliemordant
 *
 */
public class SwaggerCdiActivator implements BundleActivator {
  /**
   * start
   * @param context bundle context
   */
  @Override
  public void start(BundleContext context) throws Exception {
    final ResourceListingProvider resourceListingProvider = new ResourceListingProvider();
    final Dictionary<String, String> filter = new Hashtable<String, String>();
    filter.put("mapper.type", "resourceListingProvider");
    context.registerService(MessageBodyWriter.class, resourceListingProvider,
        filter);
    final ApiDeclarationProvider apiDeclarationProvider = new ApiDeclarationProvider();
    final Dictionary<String, String> filter2 = new Hashtable<String, String>();
    filter.put("mapper.type", "apiDeclarationProvider");
    context.registerService(MessageBodyWriter.class, apiDeclarationProvider,
        filter2);

  }
  /**
   * stop.
   * @param context Bundle context
   * 
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    final Collection<ServiceReference<ResourceListingProvider>> references = context
        .getServiceReferences(ResourceListingProvider.class,
            "(mapper.type=resourceListingProvider)");
    for (final ServiceReference<ResourceListingProvider> reference : references) {
      context.ungetService(reference);
    }

    final Collection<ServiceReference<ApiDeclarationProvider>> references2 = context
        .getServiceReferences(ApiDeclarationProvider.class,
            "(mapper.type=apiDeclarationProvider)");
    for (final ServiceReference<ApiDeclarationProvider> reference : references2) {
      context.ungetService(reference);
    }
  }

}
