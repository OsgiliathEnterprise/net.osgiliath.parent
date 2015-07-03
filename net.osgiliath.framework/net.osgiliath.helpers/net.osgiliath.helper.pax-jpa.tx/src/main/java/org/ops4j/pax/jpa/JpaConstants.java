/*
 * Copyright 2012 Harald Wellmann.
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
package org.ops4j.pax.jpa;

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
 * Constants for property keys defined in OSGi or JPA specs but missing in API classes.
 * 
 * @author Harald Wellmann
 *
 */
public class JpaConstants {

    public static final String JPA_DRIVER = "javax.persistence.jdbc.driver";
    public static final String JPA_URL = "javax.persistence.jdbc.url";
    public static final String JPA_USER = "javax.persistence.jdbc.user";
    public static final String JPA_PASSWORD = "javax.persistence.jdbc.password";
    
    public static final String JPA_PROVIDER = "javax.persistence.provider";
    public static final String JPA_MANIFEST_HEADER = "Meta-Persistence";
    public static final String JPA_PERSISTENCE_XML = "META-INF/persistence.xml";
    
    public static final String PU_NAME = "osgi.unit.name";
    public static final String PU_VERSION = "osgi.unit.version";
    public static final String PU_PROVIDER = "osgi.unit.provider";
    

    /** Hidden utility class constructor. */
    private JpaConstants() {
    }
}
