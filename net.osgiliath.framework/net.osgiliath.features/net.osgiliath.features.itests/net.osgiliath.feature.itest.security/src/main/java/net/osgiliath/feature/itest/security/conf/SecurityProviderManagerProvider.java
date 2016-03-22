package net.osgiliath.feature.itest.security.conf;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;



@ApplicationScoped
public class SecurityProviderManagerProvider {

	@Inject
	private transient AuthenticationProvider securityService;
	
	@Produces
	public AuthenticationManager createProviderManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(securityService);
		ProviderManager ret = new ProviderManager(providers);
		return ret;
	}
	
	
}
