package net.osgiliath.module.spring.data.jpa;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Metamodel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
/**
 * Delegates the construction of spring-data-jpa repository (blueprint integration needs this.
 * @author charliemordant
 *
 * @param <T> repository Entity type.
 * @param <ID> entity ID.
 */
@javax.transaction.Transactional
public abstract class DelegatingSimpleJpaRepository<T, ID extends Serializable>
		implements JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * Delegate spring-data-jpa repository.
     */
	protected SimpleJpaRepository<T, ID> delegate;

	@Override
	public Page<T> findAll(Pageable pageable) {

		return delegate.findAll(pageable);
	}

	@Override
	public <S extends T> S save(S entity) {

		return delegate.save(entity);
	}

	@Override
	public T findOne(ID id) {

		return delegate.findOne(id);
	}

	@Override
	public boolean exists(ID id) {

		return delegate.exists(id);
	}

	@Override
	public long count() {

		return delegate.count();
	}

	@Override
	public void delete(ID id) {
		delegate.delete(id);

	}

	@Override
	public void delete(T entity) {
		delegate.delete(entity);

	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		delegate.delete(entities);

	}

	@Override
	public void deleteAll() {
		delegate.deleteAll();

	}

	@Override
	public T findOne(Specification<T> spec) {
		return delegate.findOne(spec);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return delegate.findAll(spec);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {

		return delegate.findAll(spec, pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {

		return delegate.findAll(spec, sort);
	}

	@Override
	public long count(Specification<T> spec) {
		return delegate.count(spec);
	}

	@Override
	public List<T> findAll() {
		return delegate.findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {

		return delegate.findAll(sort);
	}

	@Override
	public List<T> findAll(Iterable<ID> ids) {
		return delegate.findAll(ids);
	}

	@Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		return delegate.save(entities);
	}

	@Override
	public void flush() {
		delegate.flush();

	}

	@Override
	public <S extends T> S saveAndFlush(S entity) {
		return delegate.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		delegate.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		delegate.deleteAllInBatch();

	}

	@Override
	public T getOne(ID id) {
		return delegate.getOne(id);
	}
	/**
	 * Instancites a spring data repository: must be called within postconstruct.
	 * @param emf the {@link EntityManagerFactory}.
	 * @param em the {@link EntityManager}.
	 * @param domainClass the repository class.
	 */
	protected void instanciateDelegateRepository(EntityManagerFactory emf, EntityManager em, Class<T> domainClass) {
		Metamodel mm = emf.getMetamodel();
		if (Persistable.class.isAssignableFrom(domainClass)) {
			delegate = new TransactionalSimpleJpaRepository(new JpaPersistableEntityInformation(domainClass, mm), em);

		} else {
			delegate = new TransactionalSimpleJpaRepository(new JpaMetamodelEntityInformation(domainClass, mm), em);

		}
	}
	/**
	 * Client must call this annotated with {@link PostConstruct} annotation, should call instanciateDelegateRepository.
	 */
	public abstract void postConstruct();
}
