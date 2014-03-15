/**
 * 
 */
package net.osgiliath.helpers.cdi.cxf.jaxrs.internal;

/*
 * #%L
 * net.osgiliath.helpers.cdi.cxf.jaxrs
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
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.interceptor.Interceptor;

import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author charliemordant
 * 
 */
public class EndpointPublishingExtension implements Extension {
	private List<Server> servers = new ArrayList<Server>();
	private static final Logger LOG = LoggerFactory
			.getLogger(EndpointPublishingExtension.class);
	private Collection<ProcessBean> beans = Sets.newHashSet();
	private JAXRSServerFactoryBean factory = null;

	public <X> void processPublishJaxRSServices(
			@Observes ProcessBean<X> process, BeanManager manager) {
		if (process.getBean() instanceof Interceptor
				|| process.getBean() instanceof Decorator
				|| !(process.getAnnotated() instanceof AnnotatedType)) {
			return;
		}

		AnnotatedType annotatedType = (AnnotatedType) process.getAnnotated();

		if (annotatedType.getJavaClass().getInterfaces() == null
				&& annotatedType.getAnnotation(CXFEndpoint.class) != null) {
			process.addDefinitionError(new IllegalArgumentException(
					String.format(
							"JaxRS service %s must implement at least an interface",
							annotatedType.getJavaClass().getName())));
		}
		if (annotatedType.getJavaClass().isAnnotationPresent(CXFEndpoint.class)) {
			beans.add(process);
		}
	}

	private <X> void registerRSService(
			@Observes AfterDeploymentValidation event, BeanManager manager) {
		if (factory == null)
			factory = new JAXRSServerFactoryBean();
		Bus bus = CXFBusFactory.getDefaultBus();

		if (bus == null) {
			LOG.info("Creating a new Bus");
			bus = CXFBusFactory.newInstance().createBus();
		} else {
			LOG.info("registering on existing bus");
		}
		factory.setBus(bus);
		for (ProcessBean processBean : beans) {
			Bean<X> bean = processBean.getBean();
			LOG.info("Creating CXF Endpoint for "
					+ bean.getBeanClass().getName());
			Object instance = manager.getReference(bean, bean.getBeanClass(),
					manager.createCreationalContext(bean));

			LOG.info("instance: " + bean + ", managed: ");
			String serviceURL = processBean.getAnnotated()
					.getAnnotation(CXFEndpoint.class).url();
			LOG.info("publishing instance: " + instance + ", url: "
					+ serviceURL);
			factory.setProviders(Lists.newArrayList(new JAXBElementProvider(),
					new JSONProvider()));
			factory.setAddress(serviceURL);
			factory.setServiceBean(instance);
			servers.add(factory.create());
		}
	}

	@PreDestroy
	public void destroy() {
		for (Server server : servers) {
			server.destroy();
		}
	}

}
