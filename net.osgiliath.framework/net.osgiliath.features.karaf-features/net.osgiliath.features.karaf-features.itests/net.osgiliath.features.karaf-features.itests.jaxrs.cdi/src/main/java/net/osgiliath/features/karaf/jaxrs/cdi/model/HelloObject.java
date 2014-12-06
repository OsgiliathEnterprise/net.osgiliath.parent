package net.osgiliath.features.karaf.jaxrs.cdi.model;

/*
 * #%L
 * net.osgiliath.hello.model.jpa
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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

//TODO Sample of a model bean exportable by web service (xml), persistable and validatable supporting builder pattern
@Data
// equals, hashcode, getters and setters
@Builder
// builder pattern
@NoArgsConstructor
// constructor
@AllArgsConstructor
// other constructor
@XmlRootElement
// xml marshalling
/**
 * 
 * @author charliemordant
 * Element instances
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloObject implements Serializable {
  /**
   * Serial
   */
  private static final transient long serialVersionUID = 6233801298404301547L;
  /**
   * message
   */
  @XmlElement
  // XML node
  private String helloMessage;
}
