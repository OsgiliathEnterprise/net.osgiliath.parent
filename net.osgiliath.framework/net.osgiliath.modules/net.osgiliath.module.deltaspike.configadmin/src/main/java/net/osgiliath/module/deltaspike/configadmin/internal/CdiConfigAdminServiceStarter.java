package net.osgiliath.module.deltaspike.configadmin.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import lombok.extern.slf4j.Slf4j;
import net.osgiliath.module.deltaspike.configadmin.karaf.KarafConfigAdminListener;

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
	private transient BundleContext context;
	private ServiceTracker configAdminServiceTracker;
	private KarafConfigAdminListener listener;
	
	

	public CdiConfigAdminServiceStarter(BundleContext bundleContext) {
		this.context = bundleContext;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 *      )
	 * @param bundleContext
	 *            the osgi bundle context
	 */
	public void start() {
		log.info("Osgiliath: starting deltaspike config admin tracker");
		try {
			 listener = new KarafConfigAdminListener(context);
			 
		} catch (InvalidSyntaxException e) {
			
		}
		
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 *      )
	 * @param bundleContext
	 *            the osgi bundle context
	 */
	public void stop() {
		log.info("Osgiliath: destroying deltaspike config admin tracker");
		
		listener.stop();
		listener = null;
		this.context = null;
	}

}
