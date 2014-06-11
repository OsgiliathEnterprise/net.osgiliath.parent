package net.osgiliath.helpers.cdi.eager.extension;

/*
 * #%L
 * net.osgiliath.helpers.cdi.eager
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
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import net.osgiliath.helpers.cdi.eager.Eager;
/**
 * 
 * @author charliemordant
 * Eager bean startup CDI extension
 */
public class EagerExtension implements Extension {
    /**
     * List of eager startup beans
     */
    private Collection<Bean<?>> eagerBeansList = new ArrayList<Bean<?>>();
    /**
     * Add beans to eager startup beans registry 
     * @param event
     */
    public <T> void collect(@Observes ProcessBean<T> event) {
	if (event.getAnnotated().isAnnotationPresent(Eager.class)
		&& event.getAnnotated().isAnnotationPresent(
			ApplicationScoped.class)) {
	    this.eagerBeansList.add(event.getBean());
	}
    }
    /**
     * Loads all eagered annotated
     * @param event deployement hook
     * @param beanManager bean manager
     */
    public void load(@Observes AfterDeploymentValidation event,
	    BeanManager beanManager) {
	for (Bean<?> bean : this.eagerBeansList) {
	    // note: toString() is important to instantiate the bean
	    beanManager.getReference(bean, bean.getBeanClass(),
		    beanManager.createCreationalContext(bean)).toString();
	}
    }

}
