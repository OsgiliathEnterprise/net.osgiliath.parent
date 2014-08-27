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

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.helper.camel.configadmin.ConfigAdminTracker;
import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;
import net.osgiliath.helpers.cdi.eager.Eager;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;

@Eager
@Slf4j
@CXFEndpoint(url = "/helloService", providersClasses={JSONProvider.class, JAXBElementProvider.class, ResourceListingProvider.class, ApiDeclarationProvider.class})
@Path("/api-docs")
@Api("/api-docs")
@Produces(value={MediaType.APPLICATION_JSON})
public class SwaggerBeanConfig extends ApiListingResourceJSON{
//	@GET
//	@Produces(value={MediaType.APPLICATION_JSON})
	@javax.enterprise.inject.Produces
	public BeanConfig getConfig() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setResourcePackage("net.osgiliath.hello.business.cdi.impl");
		beanConfig.setVersion("0.0.6");
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
		beanConfig.setTitle("Business module");
		beanConfig.setDescription("This is a business module");
		beanConfig.setContact("masterdev@wondermail.org");
		beanConfig.setLicense("Apache Licence 2.0");
		beanConfig
				.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
		beanConfig.setScan(true);
		return beanConfig;
	}

}
