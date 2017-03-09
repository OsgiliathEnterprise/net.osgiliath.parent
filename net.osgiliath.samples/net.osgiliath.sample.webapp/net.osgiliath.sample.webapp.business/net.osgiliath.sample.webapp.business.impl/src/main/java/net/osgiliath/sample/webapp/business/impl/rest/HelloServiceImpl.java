package net.osgiliath.sample.webapp.business.impl.rest;

import java.util.stream.Collectors;
import javax.inject.Inject;
import net.osgiliath.sample.webapp.business.spi.annotations.REST;
import net.osgiliath.sample.webapp.business.spi.model.Hellos;
import net.osgiliath.sample.webapp.model.daos.HelloRepository;
import net.osgiliath.sample.webapp.model.entities.HelloEntity;
import org.ops4j.pax.cdi.api.Service;


/**
 * REST Service implementation for testing purpose.
 * 
 * @author charliemordant
 */
@REST
public class HelloServiceImpl implements HelloServiceJaxRS {
	
	/**
	 * JPA repository.
	 */
	@Inject
	@Service
	private HelloRepository repository;
	
  

  /**
   * Registering instance.
   * 
   * @param helloObject
   *          the element to save in the database
   */
  @Override
  public void persistHello(final HelloEntity helloObject) {
	  repository.save(helloObject);
  }

  /**
   * Returns registered instances.
   * 
   * @return all instances
   */
  @Override
  public Hellos getHellos() {
    
    return new Hellos(repository.findAll().stream().map(input -> input.getHelloMessage()).collect(Collectors.toList()));
  }

  /**
   * Deletes all elements.
   */
  @Override
  public void deleteHellos() {
	  repository.deleteAll();
    
  }


}
