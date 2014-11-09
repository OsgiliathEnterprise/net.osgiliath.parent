#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package conf;

/*
 * #%L
 * CDI configured business module
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
import java.io.IOException;

import javax.enterprise.inject.Produces;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.helpers.deltaspike.configadmin.ConfigAdminAccessor;
import net.osgiliath.helpers.swagger.cdi.CXFBeanJaxrsScanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import com.wordnik.swagger.jaxrs.config.BeanConfig;

@Slf4j
public class SwaggerBeanConfig {

	@Produces
	public BeanConfig getConfig() {
		BeanConfig beanConfig = new CXFBeanJaxrsScanner(this.getClass().getClassLoader());
		BundleContext context = FrameworkUtil.getBundle(this.getClass())
				.getBundleContext();
		String protocol;
		try {
			protocol = ConfigAdminAccessor.getProperty(context,
					"jaxrs.server.protocol");

			String uri = ConfigAdminAccessor.getProperty(context,
					"jaxrs.server.uri");
			String port = ConfigAdminAccessor.getProperty(context,
					"jaxrs.server.port");

			beanConfig.setBasePath(protocol + "://" + uri + ":" + port
					+ "/cxf/helloService");
			
		} catch (IOException | InvalidSyntaxException e) {
			log.error("Error configuring Swagger bean", e);
		}
		log.info("Swagger bean configuration started");
		beanConfig.setResourcePackage("${package}");
		beanConfig.setVersion("${version}");
		beanConfig.setScan(true);
		return beanConfig;
	}

}
