package net.osgiliath.helpers.deltaspike.tx;

/*
 * #%L
 * net.osgiliath.helpers.deltaspike.tx
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

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.FrameworkUtil;
import net.osgiliath.helpers.deltaspike.configadmin.ConfigAdminAccessor;
import javax.transaction.TransactionManager;
import java.lang.annotation.Annotation;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.core.util.ExceptionUtils;
import org.apache.deltaspike.jpa.api.transaction.TransactionConfig;
import org.apache.deltaspike.jpa.impl.transaction.BeanManagedUserTransactionStrategy;
import org.apache.deltaspike.jpa.impl.transaction.ResourceLocalTransactionStrategy;
import org.apache.deltaspike.jpa.impl.transaction.context.EntityManagerEntry;
import org.ops4j.pax.cdi.api.OsgiService;

@Dependent
@Alternative
@SuppressWarnings("UnusedDeclaration")

public class OsgiUserTransactionStrategy extends
    ResourceLocalTransactionStrategy

{

  private static final long serialVersionUID = -2432802805095533499L;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(OsgiUserTransactionStrategy.class.getName());

  private transient TransactionConfig transactionConfig;

  @Inject
  @OsgiService
  private UserTransaction userTransaction;
  @Inject
  @OsgiService
  private TransactionManager transactionManager;
  @Inject
  @OsgiService
  private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

  @Override
  protected EntityManagerEntry createEntityManagerEntry(

  EntityManager entityManager, Class<? extends Annotation> qualifier)

  {

    applyTransactionTimeout(); // needs to be done before UserTransaction#begin
                               // - TODO move this call

    return super.createEntityManagerEntry(entityManager, qualifier);

  }

  protected void applyTransactionTimeout()

  {

    Integer transactionTimeout = getDefaultTransactionTimeoutInSeconds();

    if (transactionTimeout == null)

    {

      // the default configured for the container will be used

      return;

    }

    try

    {

      UserTransaction userTransaction = resolveUserTransaction();

      if (userTransaction != null
          && userTransaction.getStatus() != Status.STATUS_ACTIVE)

      {

        userTransaction.setTransactionTimeout(transactionTimeout);

      }

    }

    catch (SystemException e)

    {

      LOGGER.warn("UserTransaction#setTransactionTimeout failed",
          e);

    }

  }

  protected Integer getDefaultTransactionTimeoutInSeconds()

  {

    if (this.transactionConfig == null)

    {

      lazyInit();

    }

    return transactionConfig.getUserTransactionTimeoutInSeconds();

  }

  protected synchronized void lazyInit()

  {

    if (this.transactionConfig != null)

    {

      return;

    }

   // this.transactionConfig = BeanProvider.getContextualReference(
    //    TransactionConfig.class, true);

    //if (this.transactionConfig == null)

    //{

      this.transactionConfig = createDefaultTransactionConfig();

    //}

  }

  protected TransactionConfig createDefaultTransactionConfig()

  {

    return new TransactionConfig()

    {

      private static final long serialVersionUID = -3915439087580270117L;

      @Override
      public Integer getUserTransactionTimeoutInSeconds()

      {
        int ret = 10;
        try {
          String prop = ConfigAdminAccessor.getProperty(FrameworkUtil.getBundle(getClass()).getBundleContext(), " aries.transaction.timeout");
              if (prop != null && !prop.isEmpty()) {
                ret = Integer.valueOf(prop);
              }
          
        }
        catch (NumberFormatException | IOException | InvalidSyntaxException e) {
          LOGGER.error("unable to find default transaction timeout", e);
          
        }
       return ret;
        

      }

    };

  }

  @Override
  protected EntityTransaction getTransaction(
      EntityManagerEntry entityManagerEntry)

  {

    return new UserTransactionAdapter();

  }

  /**
   * 
   * Needed because the {@link EntityManager} might get created outside of the
   * {@link UserTransaction}
   * 
   * (e.g. depending on the implementation of the producer).
   * 
   * Can't be in
   * {@link BeanManagedUserTransactionStrategy.UserTransactionAdapter#begin()}
   * 
   * because {@link ResourceLocalTransactionStrategy} needs to do
   * 
   * <pre>
   * 
   * if (!transaction.isActive())
   * 
   * {
   * 
   *   transaction.begin();
   * 
   * }
   * 
   * </pre>
   * 
   * for the {@link EntityTransaction} of every {@link EntityManager}
   * 
   * and
   * {@link BeanManagedUserTransactionStrategy.UserTransactionAdapter#isActive()}
   * 
   * can only use the status information of the {@link UserTransaction} and
   * therefore
   * 
   * {@link BeanManagedUserTransactionStrategy.UserTransactionAdapter#begin()}
   * 
   * will only executed once, but
   * {@link javax.persistence.EntityManager#joinTransaction()}
   * 
   * needs to be called for every {@link EntityManager}.
   * 
   * @param invocationContext
   *          current invocation-context
   * 
   * @param entityManagerEntry
   *          entry of the current entity-manager
   * 
   * @param transaction
   *          current JTA transaction wrapped in an EntityTransaction adapter
   */

  @Override
  protected void beforeProceed(InvocationContext invocationContext,

  EntityManagerEntry entityManagerEntry,

  EntityTransaction transaction)

  {

    entityManagerEntry.getEntityManager().joinTransaction();

  }

  protected UserTransaction resolveUserTransaction()

  {

    return userTransaction;
  }

  protected TransactionSynchronizationRegistry resolveTransactionRegistry()

  {

    return transactionSynchronizationRegistry;

  }

  private class UserTransactionAdapter implements EntityTransaction

  {

    private final UserTransaction userTransaction;

    // needed for calls through an EJB with CMT

    private final TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    public UserTransactionAdapter()

    {

      this.userTransaction = resolveUserTransaction();

      if (this.userTransaction == null)

      {

        this.transactionSynchronizationRegistry = resolveTransactionRegistry();

        if (this.transactionSynchronizationRegistry.getTransactionStatus() != Status.STATUS_ACTIVE)

        {

          throw new IllegalStateException(

          "The CMT is not active. Please check the config of the Data-Source.");

        }

      }

      else

      {

        this.transactionSynchronizationRegistry = null;

      }

    }

    /**
     * 
     * Only delegate to the {@link UserTransaction} if the state of the
     * 
     * {@link UserTransaction} is {@link Status#STATUS_NO_TRANSACTION}
     * 
     * (= the status before and after a started transaction).
     */

    @Override
    public void begin()

    {

      if (this.userTransaction == null)

      {

        throw new UnsupportedOperationException(
            "A CMT is active. This operation is only supported with BMT.");

      }

      try

      {

        // 2nd check (already done by #isActive triggered by
        // ResourceLocalTransactionStrategy directly before)

        // currently to filter STATUS_UNKNOWN - see to-do -> TODO re-visit it

        if (this.userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION)

        {

          this.userTransaction.begin();

        }

      }

      catch (Exception e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    /**
     * 
     * Only delegate to the {@link UserTransaction} if the state of the
     * 
     * {@link UserTransaction} is one of
     * 
     * <ul>
     * 
     * <li>{@link Status#STATUS_ACTIVE}</li>
     * 
     * <li>{@link Status#STATUS_PREPARING}</li>
     * 
     * <li>{@link Status#STATUS_PREPARED}</li>
     * 
     * </ul>
     */

    @Override
    public void commit()

    {

      if (this.userTransaction == null)

      {

        throw new UnsupportedOperationException(
            "A CMT is active. This operation is only supported with BMT.");

      }

      try

      {

        if (isTransactionReadyToCommit())

        {

          this.userTransaction.commit();

        }

      }

      catch (Exception e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    /**
     * 
     * Only delegate to the {@link UserTransaction} if the state of the
     * 
     * {@link UserTransaction} is one of
     * 
     * <ul>
     * 
     * <li>{@link Status#STATUS_ACTIVE}</li>
     * 
     * <li>{@link Status#STATUS_PREPARING}</li>
     * 
     * <li>{@link Status#STATUS_PREPARED}</li>
     * 
     * <li>{@link Status#STATUS_MARKED_ROLLBACK}</li>
     * 
     * <li>{@link Status#STATUS_COMMITTING}</li>
     * 
     * </ul>
     */

    @Override
    public void rollback()

    {

      if (this.userTransaction == null)

      {

        throw new UnsupportedOperationException(
            "A CMT is active. This operation is only supported with BMT.");

      }

      try

      {

        if (isTransactionAllowedToRollback())

        {

          this.userTransaction.rollback();

        }

      }

      catch (SystemException e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    @Override
    public void setRollbackOnly()

    {

      try

      {

        if (this.userTransaction != null)

        {

          this.userTransaction.setRollbackOnly();

        }

        else

        {

          this.transactionSynchronizationRegistry.setRollbackOnly();

        }

      }

      catch (SystemException e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    @Override
    public boolean getRollbackOnly()

    {

      try

      {

        return getTransactionStatus() == Status.STATUS_MARKED_ROLLBACK;

      }

      catch (SystemException e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    /**
     * 
     * @return true if the transaction has been started and not ended
     */

    @Override
    public boolean isActive()

    {

      // we can't use the status of the overall

      try

      {

        return this.getTransactionStatus() != Status.STATUS_NO_TRANSACTION &&

        this.getTransactionStatus() != Status.STATUS_UNKNOWN; // TODO re-visit
                                                              // it

      }

      catch (SystemException e)

      {

        throw ExceptionUtils.throwAsRuntimeException(e);

      }

    }

    protected boolean isTransactionAllowedToRollback() throws SystemException

    {

      // if the following gets changed, it needs to be tested with different
      // constellations

      // (normal exception, timeout,...) as well as servers

      return this.getTransactionStatus() != Status.STATUS_COMMITTED &&

      this.getTransactionStatus() != Status.STATUS_NO_TRANSACTION &&

      this.getTransactionStatus() != Status.STATUS_UNKNOWN;

    }

    protected boolean isTransactionReadyToCommit() throws SystemException

    {

      return getTransactionStatus() == Status.STATUS_ACTIVE ||

      getTransactionStatus() == Status.STATUS_PREPARING ||

      getTransactionStatus() == Status.STATUS_PREPARED;

    }

    protected int getTransactionStatus() throws SystemException

    {

      if (this.userTransaction != null)

      {

        return this.userTransaction.getStatus();

      }

      else

      {

        return this.transactionSynchronizationRegistry.getTransactionStatus();

      }

    }

  }

}