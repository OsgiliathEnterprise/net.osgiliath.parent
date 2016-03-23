package net.osgiliath.sample.webapp.business.impl.conf;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;

/*
 * #%L
 * Osgiliath integration tests JaxRS CDI
 * %%
 * Copyright (C) 2013 - 2015 Osgiliath
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

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Sets;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.cdi.eager.Eager;
import net.osgiliath.module.cxf.exception.handling.jaxrs.mapper.ExceptionXmlMapper;
import net.osgiliath.sample.webapp.business.impl.rest.HelloServiceJaxRS;
import net.osgiliath.sample.webapp.business.spi.HelloService;
import net.osgiliath.sample.webapp.business.spi.annotations.REST;

/**
 * Main CXF application that publishes services.
 * 
 * @author charliemordant
 *
 */
@Eager
@ApplicationPath("/helloService")
@Slf4j
public class CXFApplication extends Application {
//  /**
//   * The injected REST endpoint.
//   */
  @Inject
  @REST
  private transient HelloService helloService;
  @Inject
  private transient SwaggerAPIAccessService swagger;
  
  /*
   * @see {@link javax.ws.rs.core.Application#getSingletons()}
   */
  @Override
  public Set<Object> getSingletons() {
	  log.error("Osgiliath: registering cxf servlet");
	  Swagger2Feature feature = new Swagger2Feature();
	  
	    // customize some of the properties
	    feature.setBasePath("/api");
	    feature.setResourcePackage(HelloServiceJaxRS.class.getPackage().getName());
	  /*BeanManager bm = CDI.current().getBeanManager();
	  Bean<HelloServiceJaxRS> bean= (Bean<HelloServiceJaxRS>) bm.getBeans(HelloServiceJaxRS.class).iterator().next();
	  CreationalContext<HelloServiceJaxRS> ctx = bm.createCreationalContext(bean);
	  HelloServiceJaxRS helloService = (HelloServiceJaxRS) bm.getReference(bean, HelloServiceJaxRS.class, ctx);*/
	 // log.error("Osgiliath: got bean helloservice via cdi");
    return Sets.<Object> newHashSet(helloService,swagger,
        //new JAXBElementProvider<Object>(), 
        //new ExceptionXmlMapper(),
    		
    		new SwaggerSerializers(),
        new ValidationExceptionMapper(),
       // new JAXRSBeanValidationInInterceptor(),
       // new JAXRSBeanValidationOutInterceptor(),
        
        new JacksonJsonProvider());
  }
}
