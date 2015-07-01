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

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.ops4j.lang.Ops4jException;
import org.ops4j.pax.jpa.JpaConstants;
import org.ops4j.pax.jpa.impl.JpaWeavingHook;
import org.ops4j.pax.jpa.impl.PersistenceUnitState;
import org.ops4j.pax.jpa.impl.TemporaryBundleClassLoader;
import org.ops4j.pax.jpa.jaxb.Persistence.PersistenceUnit;
import org.ops4j.pax.jpa.jaxb.PersistenceUnitCachingType;
import org.ops4j.pax.jpa.jaxb.PersistenceUnitValidationModeType;
import org.ops4j.pax.swissbox.core.BundleClassLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

/**
 * Link between OSGi JPA container and persistence provider, collecting information about a given
 * persistence unit.
 *
 * @author Harald Wellmann
 *
 */
public class PersistenceUnitInfoImpl implements PersistenceUnitInfo {

    private Bundle bundle;

    private String version;

    private PersistenceUnit persistenceUnit;

    private DataSourceFactory dataSourceFactory;

    private Properties props;

    private BundleClassLoader cl;

    private PersistenceProvider provider;

    private ServiceRegistration<EntityManagerFactoryBuilder> emfBuilderRegistration;
    private ServiceRegistration<EntityManagerFactory> emfRegistration;
    private ServiceRegistration<WeavingHook> hookRegistration;

    private PersistenceUnitState state;

    public PersistenceUnitInfoImpl(Bundle bundle, String version, PersistenceUnit persistenceUnit,
        Properties props) {
        this.bundle = bundle;
        this.version = version;
        this.persistenceUnit = persistenceUnit;
        this.props = props;
        this.state = PersistenceUnitState.UNASSIGNED;
        this.cl = new BundleClassLoader(bundle);
    }

    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public String getPersistenceUnitName() {
        return persistenceUnit.getName();
    }

    @Override
    public String getPersistenceProviderClassName() {
        return persistenceUnit.getProvider();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        org.ops4j.pax.jpa.jaxb.PersistenceUnitTransactionType transactionType = persistenceUnit
            .getTransactionType();
        return transactionType == null ? null : PersistenceUnitTransactionType
            .valueOf(transactionType.toString());
    }

    @Override
    public DataSource getJtaDataSource() {
        String dataSourceName = persistenceUnit.getJtaDataSource();
        if (dataSourceName != null) {
            return commonParseJNDIDatasource(dataSourceName);
        }
        return null;
    }

    private DataSource commonParseJNDIDatasource(String dataSourceName) {
        if (dataSourceName != null) {
            try {
                InitialContext context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup(dataSourceName);
                return dataSource;
            }
            catch (NamingException exc) {
                throw new Ops4jException(exc);
            }
        }
        String url = props.getProperty(JpaConstants.JPA_URL);
        String user = props.getProperty(JpaConstants.JPA_USER);
        String password = props.getProperty(JpaConstants.JPA_PASSWORD);
        Properties dsfProps = new Properties();

        if (url != null) {
            dsfProps.setProperty(DataSourceFactory.JDBC_URL, url);
        }
        if (user != null) {
            dsfProps.setProperty(DataSourceFactory.JDBC_USER, user);
        }
        if (password != null) {
            dsfProps.setProperty(DataSourceFactory.JDBC_PASSWORD, password);
        }
        try {
            return dataSourceFactory.createDataSource(dsfProps);
        }
        catch (SQLException exc) {
            throw new Ops4jException(exc);
        }
    }

    @Override
    public DataSource getNonJtaDataSource() {
        String dataSourceName = persistenceUnit.getNonJtaDataSource();
        return commonParseJNDIDatasource(dataSourceName);
    }

    @Override
    public List<String> getMappingFileNames() {
        return persistenceUnit.getMappingFile();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return bundle.getEntry("/");
    }

    @Override
    public List<String> getManagedClassNames() {
        return persistenceUnit.getClazz();
    }

    @Override
    public boolean excludeUnlistedClasses() {
        Boolean exclude = persistenceUnit.isExcludeUnlistedClasses();
        return (exclude == null) ? false : exclude;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        PersistenceUnitCachingType sharedCacheMode = persistenceUnit.getSharedCacheMode();
        return (sharedCacheMode == null) ? null : SharedCacheMode.valueOf(sharedCacheMode
            .toString());
    }

    @Override
    public ValidationMode getValidationMode() {
        PersistenceUnitValidationModeType validationMode = persistenceUnit.getValidationMode();
        return (validationMode == null) ? null : ValidationMode.valueOf(validationMode.toString());
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return version;
    }

    @Override
    public ClassLoader getClassLoader() {
        return cl;
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
        JpaWeavingHook hook = new JpaWeavingHook(this, transformer);
        hookRegistration = bundle.getBundleContext().registerService(WeavingHook.class, hook, null);
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return new TemporaryBundleClassLoader(bundle, provider.getClass().getClassLoader());
    }

    public PersistenceProvider getProvider() {
        return provider;
    }

    public void setProvider(PersistenceProvider provider) {
        this.provider = provider;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public ServiceRegistration<EntityManagerFactory> getEmfRegistration() {
        return emfRegistration;
    }

    public void setEmfRegistration(ServiceRegistration<EntityManagerFactory> emfRegistration) {
        this.emfRegistration = emfRegistration;
    }

    public ServiceRegistration<EntityManagerFactoryBuilder> getEmfBuilderRegistration() {
        return emfBuilderRegistration;
    }

    public void setEmfBuilderRegistration(
        ServiceRegistration<EntityManagerFactoryBuilder> emfBuilderRegistration) {
        this.emfBuilderRegistration = emfBuilderRegistration;
    }

    public void unregister() {
        this.state = PersistenceUnitState.UNASSIGNED;

        unregister(emfBuilderRegistration);
        unregister(emfRegistration);
        unregister(hookRegistration);

        this.emfBuilderRegistration = null;
        this.emfRegistration = null;
        this.hookRegistration = null;
    }

    private <T> void unregister(ServiceRegistration<T> reg) {
        try {
            if (reg != null) {
                reg.unregister();
            }
        }
        catch (IllegalStateException exc) {
            // ignore
        }
    }

    public PersistenceUnitState getState() {
        return state;
    }

    public void setState(PersistenceUnitState state) {
        this.state = state;
    }

    public boolean hasJndiDataSource() {
        return persistenceUnit.getNonJtaDataSource() != null
            || persistenceUnit.getJtaDataSource() != null;
    }

}
