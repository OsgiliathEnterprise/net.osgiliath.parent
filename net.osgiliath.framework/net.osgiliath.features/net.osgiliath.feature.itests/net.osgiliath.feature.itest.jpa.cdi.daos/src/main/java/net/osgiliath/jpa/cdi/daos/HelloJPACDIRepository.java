package net.osgiliath.jpa.cdi.daos;

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

import net.osgiliath.jpa.cdi.entities.HelloEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
@Repository
public interface HelloJPACDIRepository extends EntityRepository<HelloEntity, Long> {
//  @Query("from HelloEntity h where h.helloMessage = ?1")
//  HelloEntity findByHelloMessage(String message);
}
