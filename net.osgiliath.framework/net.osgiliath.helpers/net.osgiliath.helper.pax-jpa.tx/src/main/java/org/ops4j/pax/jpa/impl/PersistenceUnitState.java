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

/**
 * State of a persistence unit with respect to its OSGi service dependencies.
 * 
 * @author Harald Wellmann
 *
 */
public enum PersistenceUnitState {
    
    /**
     * Persistence unit is not assigned to a provider.
     */
    UNASSIGNED,
    
    /**
     * Persistence unit is assigned to a provider but not bound to a data source.
     */
    READY,
    
    /**
     * Persistence unit is assigned to a provider and bound to a data source.
     */
    COMPLETE
}
