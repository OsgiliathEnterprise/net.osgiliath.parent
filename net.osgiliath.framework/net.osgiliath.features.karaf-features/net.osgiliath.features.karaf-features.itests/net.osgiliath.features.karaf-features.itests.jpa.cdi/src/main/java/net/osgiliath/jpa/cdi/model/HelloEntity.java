package net.osgiliath.jpa.cdi.model;

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
@Entity
public class HelloEntity implements Serializable{
	/**
	 * 
	 */
	private static final transient long serialVersionUID = -4563351879876924302L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long entityId;
	private String helloMessage;
	
	public HelloEntity() {
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getHelloMessage() {
		return helloMessage;
	}
	public void setHelloMessage(String helloMessage) {
		this.helloMessage = helloMessage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		result = prime * result
				+ ((helloMessage == null) ? 0 : helloMessage.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HelloEntity other = (HelloEntity) obj;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		if (helloMessage == null) {
			if (other.helloMessage != null)
				return false;
		} else if (!helloMessage.equals(other.helloMessage))
			return false;
		return true;
	}
	
}
