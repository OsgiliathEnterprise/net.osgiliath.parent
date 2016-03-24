package ${package}.conf;

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
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.cdi.eager.Eager;
import ${package}.annotations.REST;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;

/**
 * Main CXF application that publishes services.
 * 
 * @author charliemordant
 *
 */
@Eager
@ApplicationPath("/sampleService")
@Slf4j
public class CXFApplication extends Application {
//  /**
//   * The injected REST endpoint.
//   */
//  @Inject
//  @REST
//  private transient SampleService sampleService;
  @Inject
  private transient SwaggerAPIAccessService swagger;
  
  /*
   * @see {@link javax.ws.rs.core.Application#getSingletons()}
   */
  @Override
  public Set<Object> getSingletons() {
	  log.error("Osgiliath: registering cxf servlet");

	
    return Sets.<Object> newHashSet(/* TODO inject your REST services here sampleService*/,swagger,
        new JAXBElementProvider<Object>(), 
        new ExceptionXmlMapper(),
    		
    		new SwaggerSerializers(),
        new ValidationExceptionMapper(),
        new JacksonJsonProvider());
  }
}
