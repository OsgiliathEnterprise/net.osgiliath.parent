package net.osgiliath.feature.itest.security;

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

import javax.inject.Inject;
import org.ops4j.pax.cdi.api.Component;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.api.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * {@link SecurityService} implementation.
 * 
 * @author Charlie
 * 
 */
@Slf4j
@Component
@Service
public class LoginPasswordSecurityService implements SecurityService {
	/**
	 * The {@link AuthenticationManager}.
	 */
	@Inject
	private transient AuthenticationManager authenticationManager;

	/**
	 * The {@link PasswordEncoder}.
	 */
	@Inject
	private transient PasswordEncoder passwordEncoder;
	/**
	 * The {@link SaltSource}.
	 */
	@Inject
	private transient SaltSource saltSource;

	/**
	 * gets the {@link AuthenticationManager}.
	 * 
	 * @return the {@link AuthenticationManager}
	 */
	public AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	/**
	 * sets the {@link AuthenticationManager}.
	 * 
	 * @param authenticationManager
	 *            the {@link AuthenticationManager}
	 */
	public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * gets the {@link PasswordEncoder}.
	 * 
	 * @return the {@link PasswordEncoder}
	 */
	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	/**
	 * sets the {@link PasswordEncoder}.
	 * 
	 * @param passwordEncoder
	 *            the {@link PasswordEncoder}
	 */
	public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * gets the {@link SaltSource}.
	 * 
	 * @return the {@link SaltSource}
	 */
	public SaltSource getSaltSource() {
		return this.saltSource;
	}

	/**
	 * sets the {@link SaltSource}.
	 * 
	 * @param saltSource
	 *            the {@link SaltSource}
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return if the user is authenticated
	 */
	@Override
	public boolean authenticate(String username, String password) {

		Authentication aut = new UsernamePasswordAuthenticationToken(username, password);
		try {
			aut = this.authenticationManager.authenticate(aut);
			SecurityContextHolder.getContext().setAuthentication(aut);
		} catch (AuthenticationException e) {
			log.error("error while authenticating", e);
			return false;
		}
		return aut.isAuthenticated();
	}

	/**
	 * {@inheritDoc}.
	 * 
	 * @param subscriptionMessageIn
	 *            the received message
	 * @return the user
	 */
	@Override
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
