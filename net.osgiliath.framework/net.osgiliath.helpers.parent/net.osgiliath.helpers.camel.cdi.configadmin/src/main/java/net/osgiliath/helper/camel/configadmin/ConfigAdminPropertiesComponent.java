package net.osgiliath.helper.camel.configadmin;

/*
 * #%L
 * net.osgiliath.helper.camel.cdi.configadmin
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath
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
import javax.enterprise.inject.Specializes;

import net.osgiliath.helper.camel.configadmin.internal.ConfigAdminPropertiesParser;

import org.apache.camel.cdi.component.properties.CdiPropertiesComponent;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.ops4j.pax.cdi.api.Properties;
import org.ops4j.pax.cdi.api.Property;
/**
 * 
 * @author charliemordant
 * specialization for configadmin
 */
@OsgiServiceProvider
@Properties(value = { @Property(name = "component-type", value = "configAdminProperties") })
@ApplicationScoped
@Specializes
public class ConfigAdminPropertiesComponent extends CdiPropertiesComponent {
    /**
     * returns property component
     */
    public ConfigAdminPropertiesComponent() {
	setPropertiesParser(new ConfigAdminPropertiesParser());
    }

}
