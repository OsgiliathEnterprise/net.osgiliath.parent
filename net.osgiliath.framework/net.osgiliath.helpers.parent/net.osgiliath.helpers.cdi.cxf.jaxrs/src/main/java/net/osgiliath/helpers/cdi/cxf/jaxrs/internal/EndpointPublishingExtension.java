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
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ProcessBean;

import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;
import net.osgiliath.helpers.cdi.cxf.jaxrs.JaxRSService;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author charliemordant
 * 
 */
public class EndpointPublishingExtension implements Extension {
	private static final Logger LOG = LoggerFactory
			.getLogger(EndpointPublishingExtension.class);
	
	private List<Server> servers = new ArrayList<Server>();
	
	private Collection<ProcessBean> beansToProcess = Sets.newHashSet();
	
	private Map<String, JAXRSServerFactoryBean> factoriesMap = Maps.newHashMap();
	

	
	public <X> void processPublishJaxRSServices(
			@Observes ProcessBean<X> process, BeanManager manager) {
		if (process.getBean() instanceof Interceptor
				|| process.getBean() instanceof Decorator
				|| !(process.getAnnotated() instanceof AnnotatedType)) {
			return;
		}

		AnnotatedType annotatedType = (AnnotatedType) process.getAnnotated();
		if (annotatedType.getAnnotation(CXFEndpoint.class) == null) {
			return;
		}
		Class[] interfaces = annotatedType.getJavaClass().getInterfaces();
		if (interfaces == null) {
			process.addDefinitionError(new IllegalArgumentException(
					String.format(
							"JaxRS service %s must implement at least an interface",
							annotatedType.getJavaClass().getName())));
			boolean isService = checkImplementsService(interfaces);
			if (!isService) {
				process.addDefinitionError(new IllegalArgumentException(
						String.format(
								"JaxRS service %s must implement at least an interface that's a subinterface os JaxRSService",
								annotatedType.getJavaClass().getName())));
			}
		}
		
		
		
		if (annotatedType.getJavaClass().isAnnotationPresent(CXFEndpoint.class)) {
			beansToProcess.add(process);
		}
	}

	private boolean checkImplementsService(Class[] interfaces) {
		boolean isService = false;
		for (Class interfacee : interfaces) {
			if (interfacee.isAssignableFrom(JaxRSService.class)) {
				isService = true;
			}
		}
		return isService;
	}

	private <X> void registerRSService(
			@Observes AfterDeploymentValidation event, BeanManager manager) throws ClassNotFoundException {
		
		for (ProcessBean processBean : beansToProcess) {
			CXFEndpoint endpointAnnotation = processBean.getAnnotated()
			.getAnnotation(CXFEndpoint.class);
			JAXRSServerFactoryBean factory = getFactory(endpointAnnotation.factoryId());
			Bean<X> bean = processBean.getBean();
			LOG.info("Creating CXF Endpoint for "
					+ bean.getBeanClass().getName());
			Object instance = manager.getReference(bean, bean.getBeanClass(),
					manager.createCreationalContext(bean));

			LOG.info("instance: " + bean + ", managed: ");
			String serviceURL = endpointAnnotation.url();
			LOG.info("publishing instance: " + instance + ", url: "
					+ serviceURL);
			addProviders(factory, endpointAnnotation.providersClasses());
			addInterceptors(factory, endpointAnnotation);

			factory.setAddress(serviceURL);
			factory.setServiceBean(instance);
			servers.add(factory.create());
		}
	}

	private void addInterceptors(JAXRSServerFactoryBean factory,
			CXFEndpoint endpointAnnotation) throws ClassNotFoundException {
		addInFaultInterceptors(factory, endpointAnnotation.inFaultInterceptors());
		addInInterceptors(factory, endpointAnnotation.inInterceptors());
		addOutFaultInterceptors(factory, endpointAnnotation.outFaultInterceptors());
		addOutInterceptors(factory, endpointAnnotation.outInterceptors());
	}

	private void addOutInterceptors(
			JAXRSServerFactoryBean factory,
			Class<? extends Interceptor<? extends Message>>[] outInterceptors) throws ClassNotFoundException {
		Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets.newHashSet(factory.getOutInterceptors());
		fillInterceptorList(interceptors, outInterceptors);
		factory.setOutInterceptors(Lists.newArrayList(interceptors));
		
		
	}
	
	private void addOutFaultInterceptors(
			JAXRSServerFactoryBean factory,
			Class<? extends Interceptor<? extends Message>>[] outFaultInterceptors) throws ClassNotFoundException {
		Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets.newHashSet(factory.getOutFaultInterceptors());
		fillInterceptorList(interceptors, outFaultInterceptors);
		factory.setOutFaultInterceptors(Lists.newArrayList(interceptors));
		
		
	}

	private void addInInterceptors(JAXRSServerFactoryBean factory,
			Class<? extends Interceptor<? extends Message>>[] inInterceptors) throws ClassNotFoundException {
		Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets.newHashSet(factory.getInInterceptors());
		fillInterceptorList(interceptors, inInterceptors);
		factory.setInInterceptors(Lists.newArrayList(interceptors));
		
	}

	private void addInFaultInterceptors(
			JAXRSServerFactoryBean factory,
			Class<? extends Interceptor<? extends Message>>[] inFaultInterceptors) throws ClassNotFoundException {
		Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets.newHashSet(factory.getInFaultInterceptors());
		fillInterceptorList(interceptors, inFaultInterceptors);
		factory.setInFaultInterceptors(Lists.newArrayList(interceptors));
		
		
	}

	private void fillInterceptorList(
			Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> setToFill,
			Class<? extends Interceptor<? extends Message>>[] toAddInterceptors)
			throws ClassNotFoundException {
		for(Class clazz : toAddInterceptors) {
			boolean found= false;
			for (Object provider : ProvidersServiceRegistry.getInstance().getProviders()) {
				if (clazz.isInstance(provider)) {
					found = true;
					setToFill.add((org.apache.cxf.interceptor.Interceptor<? extends Message>) provider);
					break;
				}
			}
			if (!found) {
				throw new ClassNotFoundException("Interceptor " + clazz.getName() + " could not be instantiated");
			}
			
		}
	}

	private JAXRSServerFactoryBean getFactory(String factoryId) {
		JAXRSServerFactoryBean factory = factoriesMap.get(factoryId);
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
		return factory;
	}

	private void addProviders(JAXRSServerFactoryBean factory, Class[] clazzes) throws ClassNotFoundException {
		Set<Object> providers = Sets.newHashSet(factory.getProviders());
		for(Class clazz : clazzes) {
			boolean found= false;
			for (Object provider : ProvidersServiceRegistry.getInstance().getProviders()) {
				if (clazz.isInstance(provider)) {
					found = true;
					providers.add(provider);
					break;
				}
			}
			if (!found) {
				throw new ClassNotFoundException("Provider " + clazz.getName() + " could not be instantiated");
			}
			
		}
		factory.setProviders(Lists.newArrayList(providers));
		
	}

	@PreDestroy
	public void destroy() {
		for (Server server : servers) {
			server.destroy();
		}
	}

}
