package config;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jpa.cdi.daos
 * %%
 * Copyright (C) 2013 - 2015 Osgiliath
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
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.osgiliath.helpers.cdi.eager.Eager;
import org.ops4j.pax.cdi.api.OsgiService;
@ApplicationScoped
@Eager
public class EntityManagerProducer {
  @Inject
  @OsgiService(filter="(osgi.unit.name=myTestPu)",timeout=10)
  private EntityManagerFactory emf;
  @Produces // you can also make this @RequestScoped
  public EntityManager create()
  {
      return emf.createEntityManager();
  }

  public void close(@Disposes EntityManager em)
  {
      if (em.isOpen())
      {
          em.close();
      }
  }
}
