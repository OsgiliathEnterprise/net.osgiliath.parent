package net.osgiliath.features.jpa.cdi.model;

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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class HelloEntity implements Serializable {
    /**
	 * 
	 */
    private static final transient long serialVersionUID = -4563351879876924302L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long entityId;
    private String helloMessage;

}
