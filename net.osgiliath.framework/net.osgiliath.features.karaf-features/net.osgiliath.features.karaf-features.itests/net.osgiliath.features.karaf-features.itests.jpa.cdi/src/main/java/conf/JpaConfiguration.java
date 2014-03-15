package conf;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.ops4j.pax.cdi.api.OsgiService;
@ApplicationScoped
public class JpaConfiguration {
	private static final String emfContainerConstant = "(org.apache.aries.jpa.container.managed=true)(org.apache.aries.jpa.default.unit.name=false)(org.apache.aries.jpa.proxy.factory=true)";
	// or manual bootstrapping
	@Inject
	@OsgiService(filter = "(&(osgi.unit.name=myTestPu) "+emfContainerConstant+")")
	private EntityManagerFactory emf;
	@Produces
	@Default
	@RequestScoped
	protected EntityManager createEntityManager() {
		return this.emf.createEntityManager();
	}

	protected void closeEntityManager(@Disposes @Default EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
