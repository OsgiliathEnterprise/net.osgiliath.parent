package mocks.routes;

/*
 * #%L
 * net.osgiliath.hello.routes
 * %%
 * Copyright (C) 2013 Osgiliath
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

import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;

public class HelloRouteEndpointsMock extends RouteBuilder {
//TODO Mock routing, you can also use integration test like in the business module
	@Override
	public void configure() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(new Class[]{HelloEntity.class, Hellos.class}); 
		// initialize jaxbformat from jaxbcontext
		DataFormat jaxBDataFormat = new JaxbDataFormat(ctx);
		from("{{net.osgiliath.hello.business.url.restservice}}/hello").choice().
		when(header(Exchange.HTTP_METHOD).isEqualTo(constant("GET")))
		.beanRef("helloService", "getHellos").marshal(jaxBDataFormat).
		when(header(Exchange.HTTP_METHOD).isEqualTo(constant("POST")))
		.unmarshal(jaxBDataFormat).beanRef("helloService", "persistHello(${body})").endChoice();
	}

}
