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

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * {@link SecurityService} implementation
 * 
 * @author Charlie
 * 
 */
@Slf4j
public class LoginPasswordSecurityService implements SecurityService {
  /**
   * The {@link AuthenticationManager}
   */
  private AuthenticationManager authenticationManager;

  /**
   * The {@link PasswordEncoder}
   */
  private PasswordEncoder passwordEncoder;
  /**
   * The {@link SaltSource}
   */
  private SaltSource saltSource;

  public AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }

  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  public PasswordEncoder getPasswordEncoder() {
    return passwordEncoder;
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public SaltSource getSaltSource() {
    return saltSource;
  }

  public void setSaltSource(SaltSource saltSource) {
    this.saltSource = saltSource;
  }

  /**
   * {@inheritDoc}
   */

  public boolean authenticate(String username, String password) {

    Authentication aut = new UsernamePasswordAuthenticationToken(username,
        password);
    try {
      aut = this.authenticationManager.authenticate(aut);
      SecurityContextHolder.getContext().setAuthentication(aut);
    } catch (Exception e) {
      log.error("error while authenticating", e);
      return false;
    }
    return aut.isAuthenticated();
  }

  /**
   * {@inheritDoc}
   */

  public final MUser onSubscription(final MUser subscriptionMessageIn) {
    final MAuthority auth = new MAuthority();
    auth.setAuthority(AUTHORITY.MEMBER);
    subscriptionMessageIn.getAuthorities().add(auth);
    final String password = subscriptionMessageIn.getPassword();
    final String saltedPassword = this.passwordEncoder.encodePassword(password,
        this.saltSource.getSalt(subscriptionMessageIn));
    subscriptionMessageIn.setPassword(saltedPassword);
    RepositoryUserDetailsService.getUsers().add(subscriptionMessageIn);
    return subscriptionMessageIn;

  }
}
