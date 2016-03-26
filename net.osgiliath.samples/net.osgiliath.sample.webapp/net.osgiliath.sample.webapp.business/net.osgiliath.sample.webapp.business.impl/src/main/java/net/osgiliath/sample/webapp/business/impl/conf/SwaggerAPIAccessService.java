package net.osgiliath.sample.webapp.business.impl.conf;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

/*
 * #%L
 * CDI configured business module
 * %%
 * Copyright (C) 2013 - 2016 Osgiliath
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Swagger service.
 * 
 * @author charliemordant
 *
 */
public class SwaggerAPIAccessService extends ApiListingResource {
  /**
   * Swagger configuration.
   */
  @Inject
  private BeanConfig config;

  /**
   * Creates configuration.
   */
  @PostConstruct
  private void injectConfig() {
    this.config.toString();
  }

}
