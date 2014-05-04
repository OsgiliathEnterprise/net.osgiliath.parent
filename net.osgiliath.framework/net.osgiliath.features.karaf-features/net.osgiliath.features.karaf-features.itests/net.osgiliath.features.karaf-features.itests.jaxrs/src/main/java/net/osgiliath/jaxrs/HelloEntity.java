package net.osgiliath.jaxrs;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jpa
 * %%
 * Copyright (C) 2013 Osgiliath corp
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
/**
 * 
 * @author charliemordant
 * Sample Model Object
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class HelloEntity {
    /**
     * Id
     */
    @XmlElement
    private Long entityId;
    /**
     * helloMessage
     */
    @XmlElement
    private String helloMessage;

}
