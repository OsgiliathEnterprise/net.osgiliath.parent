package conf;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.messaging
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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import net.osgiliath.messaging.HelloEntity;
import net.osgiliath.messaging.repository.HelloCDIRepository;
import net.osgiliath.messaging.repository.impl.RouteConsumer;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Default
@ApplicationScoped
@OsgiServiceProvider
public class CdiBootstrap{
	private Logger LOG = LoggerFactory.getLogger(CdiBootstrap.class);
	@Inject
	private HelloCDIRepository repository;
	@Inject
	private RouteConsumer consumer;
	@Inject
	private Components components;
	
	@PostConstruct
	public void boot() {
		repository.toString();
		consumer.toString();
		components.toString();
	}
	
	public void directSave(HelloEntity entity) {
		repository.directSave(entity);
	}


	public boolean ensureDelivery() {
		boolean processedByRoute =  consumer.isDelivered();
		boolean processedByConsumer = repository.isProcessed();
		return processedByRoute;//TODO while testing consumes add && processedByConsumer
		
	}

}
