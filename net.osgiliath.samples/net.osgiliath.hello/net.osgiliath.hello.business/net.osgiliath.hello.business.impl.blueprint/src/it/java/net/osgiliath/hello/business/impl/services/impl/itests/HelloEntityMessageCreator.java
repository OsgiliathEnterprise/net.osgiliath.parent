package net.osgiliath.hello.business.impl.services.impl.itests;

/*
 * #%L
 * Blueprint configured business module
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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import net.osgiliath.hello.model.jpa.model.HelloEntity;

import org.springframework.jms.core.MessageCreator;
/**
 * As Pax exam does not support inner class, we made a message creator.
 * @author charliemordant
 *
 */
public class HelloEntityMessageCreator implements MessageCreator {
  /**
   * Creates a hello entity.
   * @param session the JMS session
   * @return the according message
   */
  @Override
  public Message createMessage(Session session) throws JMSException {
    return session.createObjectMessage(HelloEntity.builder()
        .helloMessage("Doe").build());
  }

}
