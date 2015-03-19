package conf;

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

import net.osgiliath.helpers.cxf.exception.handling.jaxrs.mapper.ExceptionXmlMapper;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Sets;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
/**
 * REST services publishing
 * @author charliemordant
 *
 */
@ApplicationPath("/sampleService")
public class CXFApplication extends Application {
  
     // @Inject private transient HelloServiceJaxRS helloService;
      @Inject private transient ApiListingResourceJSON swaggerService;
       /**
        * Services to publish
        * @return the services
        */
      @Override
      public Set<Object> getSingletons() {
          return Sets.< Object >newHashSet(
             // helloService,
              this.swaggerService,
              new JAXBElementProvider<Object>(),
              new ExceptionXmlMapper(),
              new ResourceListingProvider(),
              new ApiDeclarationProvider(),
              new JacksonJsonProvider());
      }
}
