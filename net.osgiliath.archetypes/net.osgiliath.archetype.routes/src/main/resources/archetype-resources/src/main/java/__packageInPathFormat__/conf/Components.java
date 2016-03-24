package {package}.conf;

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
import net.osgiliath.helper.camel.ThrownExceptionMessageToInBodyProcessor;
import net.osgiliath.helper.cdi.eager.Eager;

import org.apache.camel.Component;
import org.apache.camel.Processor;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.ops4j.pax.cdi.api.OsgiService;
/**
 * CDI injected camel components.
 * @author charliemordant
 *
 */
@Slf4j
@Eager
public class Components {
    /**
     * JMS component.
     */
	@Inject
	@OsgiService(filter = "(component-type=jms)", dynamic = true)
	private transient Component jms;
    /**
     * JMS transacted component.
     */
	
	@Inject
	@OsgiService(filter = "(component-type=jmsXA)", dynamic = true)
	private transient Component jmsXA;
    /**
     * Validation exception transformer component.
     * @return the processor
     */		
	@Produces
	@Named("thrownExceptionMessageToInBodyProcessor")
	public Processor getThrownExceptionMessageToInBodyProcessor() {
		LOG.info("Inject Processor in body");
		return new ThrownExceptionMessageToInBodyProcessor();
	}
	/**
	 * JMS cdi producer.
	 * @return the JMS component
	 */
	@Produces
	@Named("jms")
	public Component getJms() {
		LOG.info("Inject jms");
		return this.jms;
	}
	/**
     * JMS XA cdi producer.
     * @return the JMS XA component
     */
	@Produces
	@Named("jmsXA")
	public Component getJmsXA() {
		LOG.info("Inject jmsXA");
		return this.jmsXA;
	}
	/**
	 * HTTP camel component.
	 * @return the HTTP component.
	 */
	@Produces
	@Named("http")
	public Component getHttp() {
		LOG.info("Inject httpComponent");
		return new HttpComponent();
	}
	
	/**
	 * JSON camel dataformt.
	 * @return the dataformat.
	 */
	@Produces
	@Named("json")
	public DataFormat getJacksonDataFormat() {
		return new JacksonDataFormat();
	}
	/**
	 * XML to Json dataformat.
	 * @return the dataformat.
	 */
	@Produces
	@Named("xmljson")
	public DataFormat getXmlJsonDataFormat() {
		return new XmlJsonDataFormat();
	}
}
