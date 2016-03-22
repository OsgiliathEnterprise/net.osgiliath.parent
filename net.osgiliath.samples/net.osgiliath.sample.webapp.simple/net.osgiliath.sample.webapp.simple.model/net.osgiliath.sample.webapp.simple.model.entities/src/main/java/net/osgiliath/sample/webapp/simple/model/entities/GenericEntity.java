package net.osgiliath.sample.webapp.simple.model.entities;

/*
 * #%L
 * net.osgiliath.raw.model.jpa
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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

/**
 * General persistence entity.
 * @author charliemordant
 */
@Data
@MappedSuperclass
// @XmlRootElement on your child class
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericEntity {
  /**
   * Identifier.
   */
  @XmlElement
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long entityId;
  /**
   * Element version.
   */
  @Version
  private Long version;

}