package conf;

/*
 * #%L
 * net.osgiliath.hello.business.impl
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

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;

@WebServlet(urlPatterns = { "/*" }, initParams = {
	@WebInitParam(name = "jaxrs.providers", value = "org.apache.cxf.jaxrs.provider.JAXBElementProvider,org.apache.cxf.jaxrs.provider.json.JSONProvider"),
	// @WebInitParam(name="jaxrs.serviceClasses",
	// value="net.osgiliath.features.karaf.jaxrs.web.impl.HelloServiceImpl"),
	// @WebInitParam(name="jaxrs.address", value="/helloService")
	@WebInitParam(name = "javax.ws.rs.Application", value = "conf.JaxRSApplication")

}

)
public class JaxRSServicesProviderServlet extends CXFNonSpringJaxrsServlet {

}
