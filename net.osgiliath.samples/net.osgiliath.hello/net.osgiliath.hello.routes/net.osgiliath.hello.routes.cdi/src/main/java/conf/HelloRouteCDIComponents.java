package conf;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.messaging.cdi
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.helpers.camel.ThrownExceptionMessageToInBodyProcessor;
import net.osgiliath.helpers.cdi.eager.Eager;

import org.apache.camel.Component;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.ops4j.pax.cdi.api.OsgiService;

/**
 * CDI configuration.
 * @author charliemordant
 */
@Slf4j
@Eager
public class HelloRouteCDIComponents {
  /**
   * Messaging component import.
   */
  @Inject
  @OsgiService(filter = "(component-type=jms)", dynamic = true)
  private transient Component jms;
  /**
   * transactional messaging component.
   */
  @Inject
  @OsgiService(filter = "(component-type=jmsXA)", dynamic = true)
  private transient Component jmsXA;

  /**
   * Exception message converter processor.
   * @return JSR303 message processor
   */
  @Produces
  @Named("thrownExceptionMessageToInBodyProcessor")
  public Processor getThrownExceptionMessageToInBodyProcessor() {
    log.info("Inject Processor in body");
    return new ThrownExceptionMessageToInBodyProcessor();
  }

  /**
   * JMS Component.
   * @return JMS component CDI export
   */
  @Produces
  @Named("jms")
  public Component getJms() {
    log.info("Inject jms");
    return this.jms;
  }

  /**
   * JMS XA component.
   * @return JMS XA component CDI export
   */
  @Produces
  @Named("jmsXA")
  public Component getJmsXA() {
    log.info("Inject jmsXA");
    return this.jmsXA;
  }

  /**
   * HTTP camel component.
   * @return HTTP component CDI export
   */
  @Produces
  @Named("http")
  public Component getHttp() {
    log.info("Inject httpComponent");
    return new HttpComponent();
  }

  /**
   * JSon dataformat.
   * @return Json component CDI export
   */
  @Produces
  @Named("json")
  public DataFormat getJacksonDataFormat() {
    return new JacksonDataFormat();
  }

  /**
   * XML to json dataformat.
   * @return XmlJson component CDI export
   */
  @Produces
  @Named("xmljson")
  public DataFormat getXmlJsonDataFormat() {
    return new XmlJsonDataFormat();
  }
}
