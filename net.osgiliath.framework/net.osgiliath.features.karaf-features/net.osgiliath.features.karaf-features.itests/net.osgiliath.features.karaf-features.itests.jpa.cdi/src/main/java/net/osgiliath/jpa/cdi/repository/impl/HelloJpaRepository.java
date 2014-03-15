package net.osgiliath.jpa.cdi.repository.impl;

import net.osgiliath.jpa.cdi.repository.HelloRepository;

@Repository
public class HelloJpaRepository extends EntityRepository<HelloEntity, Long> implements HelloRepository{
	

}
