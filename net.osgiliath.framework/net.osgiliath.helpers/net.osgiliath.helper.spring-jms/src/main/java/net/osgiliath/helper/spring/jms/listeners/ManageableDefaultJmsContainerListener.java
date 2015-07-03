package net.osgiliath.helper.spring.jms.listeners;

/*
 * #%L
 * Connection factory
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

import org.springframework.jms.listener.DefaultMessageListenerContainer;
/**
 * Adds shutdown info to the default spring container listener.
 * @author charliemordant
 *
 */
public class ManageableDefaultJmsContainerListener extends
    DefaultMessageListenerContainer {
  /**
   * Shutdowns gracefully.
   */
  public void shutdownGracefully() {
    this.stop();
    this.destroy();
  }

}
