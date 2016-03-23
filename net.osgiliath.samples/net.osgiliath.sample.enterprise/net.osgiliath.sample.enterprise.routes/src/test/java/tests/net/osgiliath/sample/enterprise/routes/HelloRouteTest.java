package tests.net.osgiliath.sample.enterprise.routes;

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

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.HttpMethod;
import net.osgiliath.sample.webapp.business.spi.HelloService;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//TODO unit test route sample
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/net.osgiliath.sample.enterprise.routes.xml" })
public class HelloRouteTest extends AbstractJUnit4SpringContextTests{

  @Produce(uri = "{{helloApp.inCamelQueueJMS}}")
  protected ProducerTemplate helloEntryPoint;
  @EndpointInject(uri = "{{helloApp.outCamelTopicJMS}}")
  protected MockEndpoint helloRouteMock;
  @Autowired
  private HelloService helloService;

  @DirtiesContext
  @Test
  public void helloRouteMustHaveBeenCalled() throws InterruptedException {
    JsonObject model = Json.createObjectBuilder().add("helloMessage", "toto")
        .build();
    Map headers = new HashMap();
    headers.put("httpRequestType", HttpMethod.POST);
    helloEntryPoint.sendBodyAndHeaders(model.toString(), headers);
    verify(helloService).persistHello((HelloEntity) anyObject());
    helloRouteMock.expectedMessageCount(1);
    helloRouteMock.assertIsSatisfied();
  }
}
