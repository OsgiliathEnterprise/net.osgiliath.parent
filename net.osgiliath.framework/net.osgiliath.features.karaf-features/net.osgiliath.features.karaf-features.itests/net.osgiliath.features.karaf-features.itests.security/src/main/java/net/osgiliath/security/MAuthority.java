package net.osgiliath.security;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.security
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

import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

/**
 * Represent an Authority for right access management.
 * 
 * @author Charlie
 * 
 */

@SuppressWarnings("serial")
public class MAuthority implements GrantedAuthority, Serializable {

  /**
   * The Authority
   */
  @Setter
  private AUTHORITY authority;

  /**
   * returns authority String representation
   * @return the authority
   */
  @Override
  public String getAuthority() {
    return this.authority.getAuth();
  }

}
