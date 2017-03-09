package net.osgiliath.feature.itest.security.conf;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;



@ApplicationScoped
public class SecurityAuthenticationProvider {

	@Inject
	private transient SaltSource saltSource;
	@Inject
	private transient PasswordEncoder passwordEncoder;
	@Inject
	private transient UserDetailsService userDetailsService;
	
	@Produces
	public AuthenticationProvider createAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		provider.setSaltSource(saltSource);
		return provider;
	}
	
	
}
