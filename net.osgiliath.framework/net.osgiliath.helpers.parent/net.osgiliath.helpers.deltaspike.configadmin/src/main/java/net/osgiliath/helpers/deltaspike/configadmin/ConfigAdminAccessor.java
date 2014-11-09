package net.osgiliath.helpers.deltaspike.configadmin;

/*
 * #%L
 * Helper configAdmin cdi support
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

import java.io.IOException;
import java.util.Map;

import net.osgiliath.helpers.deltaspike.configadmin.internal.ConfigAdminTracker;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

public class ConfigAdminAccessor {

	public static String getProperty(BundleContext context, String key) throws IOException,
	InvalidSyntaxException{
		return ConfigAdminTracker.getInstance(context).getProperty(key);
		
	}
	public static Map<String, String> getProperties(BundleContext context) {
		return ConfigAdminTracker.getInstance(context).getProperties();
	}
		
}
