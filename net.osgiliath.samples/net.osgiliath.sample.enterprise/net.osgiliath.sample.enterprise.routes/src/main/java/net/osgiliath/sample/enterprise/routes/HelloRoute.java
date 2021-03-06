package net.osgiliath.sample.enterprise.routes;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import lombok.Setter;
import net.osgiliath.sample.webapp.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.io.IOUtils;

/**
 * sample route, see apache camel and EIP keyword on the net ;).
 * 
 * @author charliemordant
 */
@ContextName(value = "camelctx")
public class HelloRoute extends RouteBuilder {
  /**
   * Json Dataformat.
   */
  private final DataFormat helloObjectJSonFormat = new JacksonDataFormat(
      HelloEntity.class, Hellos.class);
  /**
   * JSR303 Validation message processor.
   */
  @Inject
  @Named("thrownExceptionMessageToInBodyProcessor")
  @Setter
  private Processor thrownExceptionMessageToInBodyProcessor;
  /**
   * XmlJson processor.
   */
  @Inject
  @Named("xmljson")
  private DataFormat xmljson;
  /**
   * changes inputstream to string.
   */
  private Processor octetsStreamToStringProcessor = new Processor() {

    @Override
    public void process(Exchange exchange) throws Exception {
      final InputStream bodyObject = exchange.getIn()
          .getBody(InputStream.class);
      final StringWriter writer = new StringWriter();
      IOUtils.copy(bodyObject, writer);
      final String theString = writer.toString();
      exchange.getIn().setBody(theString);

    }
  };

  /**
   * Messaging route.
   */
  @Override
  public void configure() throws Exception {
    final JAXBContext ctx = JAXBContext
        .newInstance(new Class[] { HelloEntity.class, Hellos.class, });
    final DataFormat jaxBDataFormat = new JaxbDataFormat(ctx);

    from("properties:{{helloApp.inCamelQueueJMS}}")
        .log(LoggingLevel.INFO, "Received message: \"${in.body}\"").choice()
        .when(header("httpRequestType").isEqualTo(HttpMethod.POST))
        .to("direct:persistObject").endChoice().otherwise()
        .setBody(
            simple("{error:  'Command not supported for the JaxRS queue'}"))
        .to("direct:toError");

    from("direct:persistObject")
        .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_XML))
        .unmarshal(this.helloObjectJSonFormat).marshal(jaxBDataFormat)
        .log(LoggingLevel.INFO, "marshalled: ${body}").doTry()
        .inOnly("properties:{{helloApp.restEndpoint}}").to("direct:updateTopic")
        .doCatch(Exception.class)
        .log(LoggingLevel.WARN, "Exception while persisting message")
        .to("direct:helloValidationError").end();

    from("direct:updateTopic")
        .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.GET))
        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_XML))
        .inOut("properties:{{helloApp.restEndpoint}}").inOut("direct:marshall")
        .to("properties:{{helloApp.outCamelTopicJMS}}");

    from("direct:marshall").process(this.octetsStreamToStringProcessor)
        .log("hello data retrieved from JaxRS : ${in.body}")
        .marshal(this.xmljson).log(LoggingLevel.INFO, "marshalled: ${body}");

    from("direct:helloValidationError")
        .process(this.thrownExceptionMessageToInBodyProcessor)
        .process(exchange -> exchange.getIn()
            .setBody(exchange.getIn().getBody(String.class)
                .replaceAll("\"", "'").replaceAll("\n", "")))
        .setBody(simple("{\"error\": \"${body}\"}"))
        .log("Subscription error: ${body}")
        .to("properties:{{helloApp.outCamelErrorQueueJMS}}");

  }

}
