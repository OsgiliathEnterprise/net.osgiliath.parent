package net.osgiliath.features.karaf.jaxrs.web.impl;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jaxrs.web
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
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import net.osgiliath.features.karaf.jaxrs.web.HelloServiceJaxRS;
import net.osgiliath.features.karaf.jaxrs.web.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.web.model.Hellos;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@ApplicationScoped
@Default
public class HelloServiceImpl implements HelloServiceJaxRS {
    private Collection<HelloObject> objects = new ArrayList<HelloObject>();

    @Override
    public void persistHello(HelloObject helloObject) {
	objects.add(helloObject);

    }

    @Override
    public Hellos getHellos() {

	return new Hellos(Lists.newArrayList(Iterables.transform(objects,
		new Function<HelloObject, String>() {

		    @Override
		    public String apply(HelloObject input) {

			return input.getHelloMessage();
		    };
		})));
    }

}
