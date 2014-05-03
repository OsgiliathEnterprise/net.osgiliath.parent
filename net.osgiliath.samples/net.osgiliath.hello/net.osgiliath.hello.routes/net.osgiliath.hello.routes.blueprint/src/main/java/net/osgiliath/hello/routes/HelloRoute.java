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

import javax.xml.bind.JAXBContext;

import lombok.Setter;
import net.osgiliath.hello.business.model.Hellos;
import net.osgiliath.hello.model.jpa.model.HelloEntity;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;

//TODO sample route, see apache camel and EIP keyword on the net ;)
public class HelloRoute extends RouteBuilder {
    private DataFormat helloObjectJSonFormat = new JacksonDataFormat(
	    HelloEntity.class, Hellos.class);
    @Setter
    private Processor thrownExceptionMessageToInBodyProcessor;
    @Setter
    private DataFormat xmljson;

    private Processor octetsStreamToStringProcessor = new Processor() {

	@Override
	public void process(Exchange exchange) throws Exception {
	    InputStream bodyObject = exchange.getIn()
		    .getBody(InputStream.class);
	    StringWriter writer = new StringWriter();
	    IOUtils.copy(bodyObject, writer);
	    String theString = writer.toString();
	    exchange.getIn().setBody(theString);

	}
    };

    @Override
    public void configure() throws Exception {
	JAXBContext ctx = JAXBContext.newInstance(new Class[] {
		HelloEntity.class, Hellos.class });
	DataFormat jaxBDataFormat = new JaxbDataFormat(ctx);

	from("{{hello.MessagingEntryPoint}}")
		.log(LoggingLevel.INFO, "Received message: \"${in.body}\"")
		.filter(header("webSocketMsgType").isNotEqualTo("heartBeat"))
		.choice()
		.when(header("httpRequestType").isEqualTo("POST"))
		.to("direct:persistObject")
		.endChoice()
		.otherwise()
		.setBody(
			simple("{error:  'Command not supported for the JaxRS queue'}"))
		.to("direct:toError");

	from("direct:persistObject")
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
		.unmarshal(helloObjectJSonFormat)
		.marshal(jaxBDataFormat)
		.log(LoggingLevel.INFO, "marshalled: ${body}")
		.doTry()
		.inOnly("{{net.osgiliath.hello.business.url.restservice}}/hello")
		.to("direct:updateTopic")
		.doCatch(Exception.class)
		.log(LoggingLevel.WARN,
			"Exception: " + exceptionMessage().toString())
		.to("direct:helloValidationError").end();

	from("direct:updateTopic")
		.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
		.inOut("{{net.osgiliath.hello.business.url.restservice}}/hello")
		.inOut("direct:marshall").to("{{hello.MessagingEndPoint}}");

	from("direct:marshall").process(octetsStreamToStringProcessor)
		.log("hello data retrieved from JaxRS : ${in.body}")
		.marshal(xmljson).log(LoggingLevel.INFO, "marshalled: ${body}");

	from("direct:helloValidationError")
		.process(thrownExceptionMessageToInBodyProcessor)
		.process(new Processor() {

		    @Override
		    public void process(Exchange exchange) throws Exception {
			exchange.getIn().setBody(
				exchange.getIn().getBody(String.class)
					.replaceAll("\"", "'")
					.replaceAll("\n", ""));
		    }
		}).setBody(simple("{\"error\": \"${body}\"}"))
		.log("Subscription error: ${body}")
		.to("{{hello.MessagingErrors}}");

    }
}
