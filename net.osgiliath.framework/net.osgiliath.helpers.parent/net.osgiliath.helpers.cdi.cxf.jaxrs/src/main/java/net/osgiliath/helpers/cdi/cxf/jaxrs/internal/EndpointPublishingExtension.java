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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import net.osgiliath.helpers.cdi.cxf.jaxrs.CXFEndpoint;
import net.osgiliath.helpers.cdi.cxf.jaxrs.internal.registry.ProvidersServiceRegistry;

import org.apache.commons.lang.ClassUtils;
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
 * @author charliemordant CXF endpoint CDI portable extension
 */
public class EndpointPublishingExtension implements Extension {
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(EndpointPublishingExtension.class);
	/**
	 * Registered servers
	 */
	private List<Server> servers = new ArrayList<Server>();
	/**
	 * Registered beans that waits for processing
	 */
	private Collection<ProcessBean> beansToProcess = Sets.newHashSet();
	/**
	 * RS server factory beans registry
	 */
	private Map<String, JAXRSServerFactoryBean> factoriesMap = Maps
			.newHashMap();

	/**
	 * CDI process bean registration phase
	 * 
	 * @param process
	 *            beans to process
	 * @param manager
	 *            manager
	 */
	public <X> void processPublishJaxRSServices(
			@Observes ProcessBean<X> process, BeanManager manager) {
		if (process.getBean() instanceof Interceptor
				|| process.getBean() instanceof Decorator
				|| !(process.getAnnotated() instanceof AnnotatedType)) {
			return;
		}

		final AnnotatedType annotatedType = (AnnotatedType) process
				.getAnnotated();
		if (annotatedType.getAnnotation(CXFEndpoint.class) == null) {
			return;
		}
		final List<Class> interfaces = new ArrayList<Class>(ClassUtils
				.getAllInterfaces(annotatedType.getJavaClass()));
		interfaces.addAll(ClassUtils.getAllSuperclasses(annotatedType.getJavaClass()));
		if (interfaces.isEmpty()) {
			process.addDefinitionError(new IllegalArgumentException(
					String.format(
							"JaxRS service %s must implement at least an interface/class",
							annotatedType.getJavaClass().getName())));

		}
		final boolean isService = checkIsService(interfaces);
		if (!isService) {
			process.addDefinitionError(new IllegalArgumentException(
					String.format(
							"JaxRS service %s must implement at least an interface that's has REST annotations",
							annotatedType.getJavaClass().getName())));
		}

		if (annotatedType.getJavaClass().isAnnotationPresent(CXFEndpoint.class)) {
			this.beansToProcess.add(process);
		}
	}

	/**
	 * Check if it implements an RS service
	 * 
	 * @param interfaces
	 *            all super interfaces
	 * @return if one of them is a JaxRS service
	 */
	private boolean checkIsService(List<Class> interfaces) {
		boolean isService = false;
		for (Class interfacee : interfaces) {
			if (interfacee.getAnnotation(Path.class) != null
					|| interfacee.getAnnotation(GET.class) != null
					|| interfacee.getAnnotation(POST.class) != null
					|| interfacee.getAnnotation(PUT.class) != null
					|| interfacee.getAnnotation(DELETE.class) != null) {
				return true;
			}
			for (Method method : interfacee.getMethods()) {
				if (method.getAnnotation(Path.class) != null
						|| method.getAnnotation(GET.class) != null
						|| method.getAnnotation(POST.class) != null
						|| method.getAnnotation(PUT.class) != null
						|| method.getAnnotation(DELETE.class) != null) {
					return true;
				}
			}
		}
		return isService;
	}

	/**
	 * Registers a service
	 * 
	 * @param event
	 *            deployment hook
	 * @param manager
	 *            beanmanager
	 * @throws ClassNotFoundException
	 *             didn't remember
	 */
	private <X> void registerRSService(
			@Observes AfterDeploymentValidation event, BeanManager manager)
			throws ClassNotFoundException {
		Map<String, JAXRSServerFactoryBean> factories = Maps.newHashMap();
		for (ProcessBean processBean : beansToProcess) {
			final CXFEndpoint endpointAnnotation = processBean.getAnnotated()
					.getAnnotation(CXFEndpoint.class);
			final JAXRSServerFactoryBean factory = getFactory(endpointAnnotation
					.factoryId());
			
			
			
			addProviders(factory, endpointAnnotation.providersClasses());
			addInterceptors(factory, endpointAnnotation);
			final String serviceURL = endpointAnnotation.url();
			
			factory.setAddress(serviceURL);
			factories.put(serviceURL, factory);
		}
		for (Entry<String, JAXRSServerFactoryBean> facts : factories.entrySet()) {
			List<Object> instancesToPut = Lists.newArrayList();
			for (ProcessBean processBean : beansToProcess) {
				final CXFEndpoint endpointAnnotation = processBean.getAnnotated()
						.getAnnotation(CXFEndpoint.class);
				final String serviceURL = endpointAnnotation.url();
				if (serviceURL.equals(facts.getKey())) {
					final Bean<X> bean = processBean.getBean();
					final Object instance = manager.getReference(bean,
							bean.getBeanClass(), manager.createCreationalContext(bean));
					instancesToPut.add(instance);
					LOG.info("instance: " + bean + ", managed: ");
					LOG.info("Creating CXF Endpoint for "
							+ bean.getBeanClass().getName() + " " + "on service address: " + serviceURL);
				}
			}
			JAXRSServerFactoryBean factory = facts.getValue();
			factory.setServiceBeans(instancesToPut);
			this.servers.add(factory.create());
		}
		
	}

	/**
	 * Adds interceptors for a service
	 * 
	 * @param factory
	 *            the factory to add interceptor to
	 * @param endpointAnnotation
	 *            service annotation
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addInterceptors(final JAXRSServerFactoryBean factory,
			final CXFEndpoint endpointAnnotation) throws ClassNotFoundException {
		addInFaultInterceptors(factory,
				endpointAnnotation.inFaultInterceptors());
		addInInterceptors(factory, endpointAnnotation.inInterceptors());
		addOutFaultInterceptors(factory,
				endpointAnnotation.outFaultInterceptors());
		addOutInterceptors(factory, endpointAnnotation.outInterceptors());
	}

	/**
	 * Adds out interceptors for a service
	 * 
	 * @param factory
	 *            the factory to add interceptor to
	 * @param endpointAnnotation
	 *            service annotation
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addOutInterceptors(
			final JAXRSServerFactoryBean factory,
			final Class<? extends Interceptor<? extends Message>>[] outInterceptors)
			throws ClassNotFoundException {
		final Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets
				.newHashSet(factory.getOutInterceptors());
		fillInterceptorList(interceptors, outInterceptors);
		factory.setOutInterceptors(Lists.newArrayList(interceptors));

	}

	/**
	 * Adds out fault interceptors for a service
	 * 
	 * @param factory
	 *            the factory to add interceptor to
	 * @param endpointAnnotation
	 *            service annotation
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addOutFaultInterceptors(
			final JAXRSServerFactoryBean factory,
			final Class<? extends Interceptor<? extends Message>>[] outFaultInterceptors)
			throws ClassNotFoundException {
		final Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets
				.newHashSet(factory.getOutFaultInterceptors());
		fillInterceptorList(interceptors, outFaultInterceptors);
		factory.setOutFaultInterceptors(Lists.newArrayList(interceptors));

	}

	/**
	 * Adds in interceptors for a service
	 * 
	 * @param factory
	 *            the factory to add interceptor to
	 * @param endpointAnnotation
	 *            service annotation
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addInInterceptors(
			final JAXRSServerFactoryBean factory,
			final Class<? extends Interceptor<? extends Message>>[] inInterceptors)
			throws ClassNotFoundException {
		final Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets
				.newHashSet(factory.getInInterceptors());
		fillInterceptorList(interceptors, inInterceptors);
		factory.setInInterceptors(Lists.newArrayList(interceptors));

	}

	/**
	 * Adds in fault interceptors for a service
	 * 
	 * @param factory
	 *            the factory to add interceptor to
	 * @param endpointAnnotation
	 *            service annotation
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addInFaultInterceptors(
			final JAXRSServerFactoryBean factory,
			final Class<? extends Interceptor<? extends Message>>[] inFaultInterceptors)
			throws ClassNotFoundException {
		final Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> interceptors = Sets
				.newHashSet(factory.getInFaultInterceptors());
		fillInterceptorList(interceptors, inFaultInterceptors);
		factory.setInFaultInterceptors(Lists.newArrayList(interceptors));

	}

	/**
	 * Fills a kind of interceptors for a list
	 * 
	 * @param setToFill
	 *            list to fill
	 * @param toAddInterceptors
	 *            interceptor class
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void fillInterceptorList(
			final Set<org.apache.cxf.interceptor.Interceptor<? extends Message>> setToFill,
			final Class<? extends Interceptor<? extends Message>>[] toAddInterceptors)
			throws ClassNotFoundException {
		for (Class clazz : toAddInterceptors) {
			boolean found = false;
			for (Object provider : ProvidersServiceRegistry.getInstance()
					.getProviders()) {
				if (clazz.isInstance(provider)) {
					found = true;
					setToFill
							.add((org.apache.cxf.interceptor.Interceptor<? extends Message>) provider);
					break;
				}
			}
			if (!found) {
				throw new ClassNotFoundException("Interceptor "
						+ clazz.getName() + " could not be instantiated");
			}

		}
	}

	/**
	 * Get or creates a service factory
	 * 
	 * @param factoryId
	 *            id
	 * @return a factory
	 */
	private JAXRSServerFactoryBean getFactory(String factoryId) {
		JAXRSServerFactoryBean factory = factoriesMap.get(factoryId);
		if (factory == null) {
			factory = new JAXRSServerFactoryBean();
		}
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

	/**
	 * Add providers to factory
	 * 
	 * @param factory
	 *            the factory to add providers to
	 * @param clazzes
	 *            providers classes
	 * @throws ClassNotFoundException
	 *             provider error
	 */
	private void addProviders(JAXRSServerFactoryBean factory, Class[] clazzes)
			throws ClassNotFoundException {
		final Set<Object> providers = Sets.newHashSet(factory.getProviders());
		for (Class clazz : clazzes) {
			boolean found = false;
			for (Object provider : ProvidersServiceRegistry.getInstance()
					.getProviders()) {
				if (clazz.isInstance(provider)) {
					found = true;
					providers.add(provider);
					break;
				}
			}
			if (!found) {
				throw new ClassNotFoundException("Provider " + clazz.getName()
						+ " could not be instantiated");
			}

		}
		factory.setProviders(Lists.newArrayList(providers));

	}

	/**
	 * Destroys servers
	 */
	@PreDestroy
	public void destroy() {
		for (Server server : servers) {
			server.destroy();
		}
	}

}
