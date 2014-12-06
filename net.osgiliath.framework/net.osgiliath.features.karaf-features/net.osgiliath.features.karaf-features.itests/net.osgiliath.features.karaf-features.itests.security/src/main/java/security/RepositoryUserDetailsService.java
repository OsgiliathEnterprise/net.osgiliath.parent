package security;

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

import java.util.Collection;
import java.util.HashSet;

import lombok.Getter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Spring security {@link UserDetailsService}
 * 
 * @author Charlie
 * 
 */
public class RepositoryUserDetailsService implements UserDetailsService {
  /**
   * Retrieves the user model jaxrs adress
   */
  @Getter
  private static Collection<UserDetails> users = new HashSet<UserDetails>();

  /**
   * {@inheritDoc}
   */
  @Override
  public UserDetails loadUserByUsername(final String arg0)
      throws UsernameNotFoundException {
    for (UserDetails details : this.users) {
      if (details.getUsername().equals(arg0))
        return details;
    }
    throw new UsernameNotFoundException("no user with this pseudo");

  }

}
