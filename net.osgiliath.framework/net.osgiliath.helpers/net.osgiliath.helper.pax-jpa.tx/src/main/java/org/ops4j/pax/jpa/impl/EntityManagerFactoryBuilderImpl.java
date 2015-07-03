/*
 * Copyright 2013 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.jpa.impl;

/*
 * #%L
 * net.osgiliath.helper.pax-jpa.tx
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

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.ops4j.pax.jpa.impl.descriptor.PersistenceUnitInfoImpl;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

/**
 * Implements the OSGi JPA {@link EntityManagerFactoryBuilder} service.
 * 
 * NOTE: This implementation only supports container managed entity manager factories. 
 * 
 * @author Harald Wellmann
 *
 */
public class EntityManagerFactoryBuilderImpl implements EntityManagerFactoryBuilder {
        
    private PersistenceUnitInfoImpl puInfo;

    public EntityManagerFactoryBuilderImpl(PersistenceUnitInfoImpl puInfo) {
        this.puInfo = puInfo;
    }

    @Override
    public EntityManagerFactory createEntityManagerFactory(Map<String, Object> props) {
        return puInfo.getProvider().createContainerEntityManagerFactory(puInfo, props);
    }
}
