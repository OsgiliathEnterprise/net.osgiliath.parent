package net.osgiliath.sample.webapp.business.impl.conf;

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

import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.ws.rs.ApplicationPath;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import io.swagger.jaxrs.config.BeanConfig;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.sample.webapp.business.impl.rest.HelloServiceJaxRS;

/**
 * Swagger configuration.
 * 
 * @author charliemordant
 *
 */
@Slf4j
public class SwaggerBeanConfig {
	/**
	 * Gets the swagger configuration.
	 * 
	 * @return the swagger configuration
	 */
	@Produces
	public BeanConfig getConfig() {
		final BeanConfig beanConfig = new BeanConfig();
		String endPointURI = "";
		endPointURI = ConfigResolver.getPropertyValue("jaxrs.server.protocol") + "://"
				+ ConfigResolver.getPropertyValue("jaxrs.server.uri") + ":"
				+ ConfigResolver.getPropertyValue("jaxrs.server.port") + "/cxf"
				+ CXFApplication.class.getAnnotation(ApplicationPath.class).value();
		beanConfig.setBasePath(endPointURI);
		log.info("Swagger bean configuration started for endpoint: " + endPointURI);
		beanConfig.setResourcePackage(HelloServiceJaxRS.class.getPackage().getName());
		beanConfig.setScan(true);
		return beanConfig;
	}
}
