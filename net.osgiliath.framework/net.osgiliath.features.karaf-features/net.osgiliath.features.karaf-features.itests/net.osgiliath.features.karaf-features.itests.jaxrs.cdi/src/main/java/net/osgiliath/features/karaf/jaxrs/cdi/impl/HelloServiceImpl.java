package net.osgiliath.features.karaf.jaxrs.cdi.impl;

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

import net.osgiliath.features.karaf.jaxrs.cdi.HelloServiceJaxRS;
import net.osgiliath.features.karaf.jaxrs.cdi.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.cdi.model.Hellos;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * REST Service implementation for testing purpose.
 * 
 * @author charliemordant
 */
public class HelloServiceImpl implements HelloServiceJaxRS {
  /**
   * Instances elements
   */
  private final Collection<HelloObject> objects = new ArrayList<HelloObject>();

  /**
   * Registering instance
   * 
   * @param helloObject
   *          the element to save in the database
   */
  @Override
  public void persistHello(final HelloObject helloObject) {
    this.objects.add(helloObject);

  }

  /**
   * Returns registered instances
   * 
   * @return all instances
   */
  @Override
  public Hellos getHellos() {
    return new Hellos(Lists.newArrayList(Iterables.transform(this.objects,
        new Function<HelloObject, String>() {
          @Override
          public String apply(final HelloObject input) {
            return input.getHelloMessage();
          }
        })));
  }
  /**
   * Deletes all elements
   */
  @Override
  public void deleteHellos() {
    this.objects.clear();
  }

}
