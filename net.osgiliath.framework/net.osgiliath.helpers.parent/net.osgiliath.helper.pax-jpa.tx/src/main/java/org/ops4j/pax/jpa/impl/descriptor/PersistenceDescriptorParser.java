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
package org.ops4j.pax.jpa.impl.descriptor;

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

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.ops4j.pax.jpa.jaxb.Persistence;
import org.ops4j.pax.jpa.jaxb.Persistence.PersistenceUnit;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Parser for persistence descriptors. Only supports the JPA 2.0 scheme.
 * 
 * Based on JAXB.
 * 
 * @author Harald Wellmann
 *
 */
public class PersistenceDescriptorParser {

    private static JAXBContext singletonJaxbContext;

    public Persistence parseDescriptor(URL url) throws JAXBException, IOException, SAXException {
        XMLReader reader = XMLReaderFactory.createXMLReader();

        // Use filter to override the namespace in the document.
        // On JDK 7, JAXB fails to parse the document if the namespace does not match
        // the one indicated by the generated JAXB model classes.
        // For some reason, the JAXB version in JDK 8 is more lenient and does
        // not require this filter.
        NamespaceFilter inFilter = new NamespaceFilter("http://xmlns.jcp.org/xml/ns/persistence");
        inFilter.setParent(reader);
        
        JAXBContext context = getJaxbContext();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        SAXSource source = new SAXSource(inFilter, new InputSource(url.openStream()));
        
        return unmarshaller.unmarshal(source, Persistence.class).getValue();
    }

    private synchronized JAXBContext getJaxbContext() throws JAXBException {
        if (singletonJaxbContext == null) {
            singletonJaxbContext = JAXBContext.newInstance(Persistence.class.getPackage().getName(), getClass()
                .getClassLoader());
        }
        return singletonJaxbContext;
    }

    public Properties parseProperties(PersistenceUnit persistenceUnit) {
        PersistenceUnit.Properties jaxbProps = persistenceUnit.getProperties();
        Properties props = new Properties();
        if (jaxbProps != null) {
            for (PersistenceUnit.Properties.Property p : jaxbProps.getProperty()) {
                props.setProperty(p.getName(), p.getValue());
            }
        }
        return props;
    }
}
