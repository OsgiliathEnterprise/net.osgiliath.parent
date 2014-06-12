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

import javax.annotation.PreDestroy;
import javax.enterprise.inject.Produces;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

import net.osgiliath.helper.camel.configadmin.ConfigAdminTracker;
import net.osgiliath.helpers.cdi.eager.Eager;

import com.wordnik.swagger.jaxrs.config.BeanConfig;


@Eager
public class SwaggerBeanConfig {
    private ServiceTracker tracker;
    @Produces
    public BeanConfig getConfig() {
	BeanConfig beanConfig = new BeanConfig();
	beanConfig.setResourcePackage("net.osgiliath.hello.business.impl");
	beanConfig.setVersion("0.0.6");
	BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
	
	String protocol;
	try {
	    protocol = ConfigAdminTracker.getInstance(context).getProperty("jaxrs.server.protocol");
	
	String uri = ConfigAdminTracker.getInstance(context).getProperty("jaxrs.server.uri");
	String port = ConfigAdminTracker.getInstance(context).getProperty("jaxrs.server.port");
	
	beanConfig.setBasePath(protocol + "://" + uri + ":" + port + "/cxf/helloService");
	} catch (IOException | InvalidSyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	beanConfig.setTitle("Business module");
	beanConfig.setDescription("This is a business module");
	beanConfig.setContact("masterdev@wondermail.org");
	beanConfig.setLicense("Apache Licence 2.0");
	beanConfig.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
	beanConfig.setScan(true);
	return beanConfig;
    }
    @PreDestroy
    public void closeTracker() {
	tracker.close();
    }

}
