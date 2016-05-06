package net.osgiliath.module.spring.data.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Transaction (JTA term) spring data repository.
 * 
 * @author charliemordant
 *
 * @param <T>
 *          Entity type.
 * @param <ID>
 *          Entity ID type.
 */
@Transactional
public class TransactionalSimpleJpaRepository<T, ID extends Serializable>
    extends SimpleJpaRepository<T, Serializable> {

  /**
   * Constructor.
   * 
   * @param jpaPersistableEntityInformation
   *          the entity {@link JpaEntityInformationSupport}.
   * @param em
   *          the {@link EntityManager}
   */
  public TransactionalSimpleJpaRepository(
      JpaEntityInformationSupport jpaPersistableEntityInformation,
      EntityManager em) {
    super(jpaPersistableEntityInformation, em);
  }

}
