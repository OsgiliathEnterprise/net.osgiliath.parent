package net.osgiliath.hello.routes;

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

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;

import lombok.Setter;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloObject;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
//TODO sample route, see apache camel and EIP keyword on the net ;)
@ContextName
public class HelloRoute extends RouteBuilder {
	
	private DataFormat helloObjectJSonFormat = new JacksonDataFormat(
			HelloObject.class, Hellos.class);
	@Inject
	@Named("thrownExceptionMessageToInBodyProcessor")
	@Setter
	private Processor thrownExceptionMessageToInBodyProcessor;
	@Inject
	@Named("xmljson")
	private DataFormat xmljson;
	@Override
	public void configure() throws Exception {
		// initialize a jaxb context to xml marshall
		
		JAXBContext ctx = JAXBContext
				.newInstance(new Class[] { HelloObject.class, Hellos.class });
		DataFormat jaxBDataFormat = new JaxbDataFormat(ctx);
		Map<String, String> xmlJsonOptions = new HashMap<String, String>();
		xmlJsonOptions.put(org.apache.camel.model.dataformat.XmlJsonDataFormat.ENCODING, "UTF-8");
		xmlJsonOptions.put(org.apache.camel.model.dataformat.XmlJsonDataFormat.SKIP_NAMESPACES, "true");
		xmlJsonOptions.put(org.apache.camel.model.dataformat.XmlJsonDataFormat.REMOVE_NAMESPACE_PREFIXES, "true");
		
		from("{{hello.helloJMSEntryPoint}}").log(LoggingLevel.INFO, "Received message: \"${in.body}\"")
		.choice()
				.when(header("httpRequestType").isEqualTo("POST"))
				.to("direct:persistObject")
				.endChoice()
				.otherwise()
				.setBody(
						simple("{error:  'Command not supported for the JaxRS queue'}"))
				.to("direct:toError");
		
		from("direct:persistObject").setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
		.unmarshal(helloObjectJSonFormat)
		.marshal(jaxBDataFormat)
		.log(LoggingLevel.INFO, "marshalled: ${body}")
		.doTry()
		.inOnly("{{net.osgiliath.hello.business.url.helloservice}}/hello")
		.to("direct:updateTopic")
		.doCatch(Exception.class).log(LoggingLevel.WARN, "Exception: " + exceptionMessage().toString())
		.to("direct:helloValidationError").end();

		from("direct:updateTopic").setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
		.inOut("{{net.osgiliath.hello.business.url.helloservice}}/hello")
		.inOut("direct:marshall")
		.to("{{hello.helloJMSEndPoint}}");
		
		from("direct:marshall").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				InputStream bodyObject = exchange.getIn().getBody(InputStream.class);
				StringWriter writer = new StringWriter();
				IOUtils.copy(bodyObject, writer);
				String theString = writer.toString();
				exchange.getIn().setBody(theString);
				
			}
		}).log("hello data retrieved from JaxRS : ${in.body}").marshal(xmljson)
//		.xmljson(Maps.newHashMap(ImmutableMap.<String, String> builder()
//				.put("forceTopLevelObject", "true").build()))
		.log(LoggingLevel.INFO, "marshalled: ${body}")
		;
		
		
		
		from("direct:helloValidationError")
		.process(thrownExceptionMessageToInBodyProcessor)
		.setBody(simple("{\"error\": \"${body}\"}"))
		.log("Subscription error: ${body}").to("{{hello.errors}}");

	}

}
