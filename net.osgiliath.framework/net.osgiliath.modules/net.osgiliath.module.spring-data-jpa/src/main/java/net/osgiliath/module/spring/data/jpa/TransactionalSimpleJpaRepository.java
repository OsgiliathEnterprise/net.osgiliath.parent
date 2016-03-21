package net.osgiliath.module.spring.data.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
@Transactional
public class TransactionalSimpleJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, Serializable>{

	
	public TransactionalSimpleJpaRepository(JpaEntityInformationSupport jpaPersistableEntityInformation,
			EntityManager em) {
		super (jpaPersistableEntityInformation, em);
	}

}
