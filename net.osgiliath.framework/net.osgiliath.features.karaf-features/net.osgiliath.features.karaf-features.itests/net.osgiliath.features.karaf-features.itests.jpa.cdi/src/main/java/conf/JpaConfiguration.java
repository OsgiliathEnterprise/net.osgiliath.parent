package conf;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jpa.cdi
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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
