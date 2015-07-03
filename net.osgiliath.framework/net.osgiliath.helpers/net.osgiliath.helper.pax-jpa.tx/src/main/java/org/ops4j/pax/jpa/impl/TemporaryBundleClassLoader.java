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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ops4j.io.StreamUtils;
import org.ops4j.pax.swissbox.core.BundleClassLoader;
import org.osgi.framework.Bundle;

/**
 * Temporary class loader required by
 * {@code javax.persistence.spi.PersistenceUnitInfo.getNewTempClassLoader()}.
 * 
 * @author Harald Wellmann
 * 
 */
public class TemporaryBundleClassLoader extends BundleClassLoader {

    public TemporaryBundleClassLoader(Bundle bundle) {
        super(bundle);
    }

    public TemporaryBundleClassLoader(Bundle bundle, ClassLoader parent) {
        super(bundle, parent);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String resource = className.replace('.', '/').concat(".class");
        InputStream is = getResourceAsStream(resource);

        if (is == null) {
            throw new ClassNotFoundException(className);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            StreamUtils.copyStream(is, baos, true);
        }
        catch (IOException exc) {
            throw new ClassNotFoundException(className, exc);
        }

        byte[] bytes = baos.toByteArray();
        return defineClass(className, bytes, 0, bytes.length);
    }
}
