package net.osgiliath.feature.itest.messaging.repository.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.osgiliath.feature.itest.messaging.Hellos;
import org.apache.camel.Body;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.cdi.Uri;


/**
 * CDI consumer via routes.
 * 
 * @author charliemordant
 */
@Slf4j
@ApplicationScoped
@ContextName(value="camelctx")
public class AnnotatedConsumer {
  
  @Inject
  @Uri("jms:queue:helloServiceQueueOut")
  ProducerTemplate out;
  
	/**
	 * Message consuming route.
	 */
	@Consume(uri = "jms:queue:helloServiceQueueOut1")
	public void configure(@Body Hellos body) throws Exception {
	  log.info("received body with camel POJO annotations");
	  out.sendBody(body);

	}

}
