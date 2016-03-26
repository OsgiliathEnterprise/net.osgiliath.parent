package net.osgiliath.module.deltaspike.configadmin.internal;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.deltaspike.configadmin.karaf.KarafConfigAdminListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Activator for config admin listener.
 * 
 * @author charliemordant
 */
@Slf4j
public class CdiConfigAdminServiceStarter {
	/**
	 * Configadmin service tracker.
	 */
	private BundleContext context;
	/**
	 * Service tracker.
	 */
	private ServiceTracker configAdminServiceTracker;
	/**
	 * Service listener.
	 */
	private KarafConfigAdminListener listener;
	
	
	/**
	 * Default constructor.
	 * @param bundleContext OSGI bundle context.
	 */
	public CdiConfigAdminServiceStarter(BundleContext bundleContext) {
		this.context = bundleContext;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 *      )
	 */
	public void start() {
		log.info("Osgiliath: starting deltaspike config admin tracker");
		try {
			 this.listener = new KarafConfigAdminListener(context);
			 
		} catch (InvalidSyntaxException e) {
			log.error("error gettint service", e);
		}
		
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 *      )
	 */
	public void stop() {
		log.info("Osgiliath: destroying deltaspike config admin tracker");
		
		this.listener.stop();
		this.listener = null;
		this.context = null;
	}

}
