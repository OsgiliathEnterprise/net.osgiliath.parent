package conf;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;



@ApplicationScoped
public class BaseEncodingProvider {

	@Produces
	public SaltSource createSaltSource() {
		ReflectionSaltSource ret = new ReflectionSaltSource();
		ret.setUserPropertyToUse("pseudo");
		return ret;
	}
	@Produces
	public PasswordEncoder createPasswordEncoder() {
		ShaPasswordEncoder pwdEncoder = new ShaPasswordEncoder(512);
		pwdEncoder.setEncodeHashAsBase64(true);
		return pwdEncoder;
		
	}
	
	
}
