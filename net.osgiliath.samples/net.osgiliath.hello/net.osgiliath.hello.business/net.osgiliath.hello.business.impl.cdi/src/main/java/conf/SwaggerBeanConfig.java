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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.enterprise.inject.Produces;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.helper.camel.configadmin.ConfigAdminTracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

@Slf4j

public class SwaggerBeanConfig {
	@Produces
	public BeanConfig getConfig() {
		 
		BeanConfig beanConfig = new BeanConfig();
		
		BundleContext context = FrameworkUtil.getBundle(this.getClass())
				.getBundleContext();

		String protocol;
		try {
			protocol = ConfigAdminTracker.getInstance(context).getProperty(
					"jaxrs.server.protocol");

			String uri = ConfigAdminTracker.getInstance(context).getProperty(
					"jaxrs.server.uri");
			String port = ConfigAdminTracker.getInstance(context).getProperty(
					"jaxrs.server.port");

			beanConfig.setBasePath(protocol + "://" + uri + ":" + port
					+ "/cxf/helloService");
			
		} catch (IOException | InvalidSyntaxException e) {
			log.error("Error configuring Swagger bean", e);
		}
		
		log.info("Swagger bean configuration started");

		beanConfig.setScan(true);
		beanConfig.setResourcePackage("net.osgiliath.hello.business.cdi.impl");
		
		return beanConfig;
	}

}
