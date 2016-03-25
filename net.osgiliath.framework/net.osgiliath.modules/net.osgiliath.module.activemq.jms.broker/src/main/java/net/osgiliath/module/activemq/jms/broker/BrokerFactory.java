package net.osgiliath.module.activemq.jms.broker;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.broker.region.policy.IndividualDeadLetterStrategy;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.broker.util.LoggingBrokerPlugin;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.StoreUsage;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.TempUsage;
import org.apache.activemq.util.MemoryPropertyEditor;
/**
 * ActiveMq broker configuration.
 * @author charliemordant
 *
 */
public class BrokerFactory {
    /**
     * Broker's name.
     */
	private String brokerName;
	/**
	 * JMS uri.
	 */
	private String jmsURI;
	/**
	 * JMS transactional URI.
	 */
	private String jmsXAURI;
	/**
	 * Stomp URI.
	 */
	private String stompURI;
	/**
	 * Stomp transactional URI.
	 */
	private String stompXAURI;
	/**
	 * WebSocket URI.
	 */
	private String websocketURI;
	/**
	 * Transactional URI.
	 */
	private String websocketXAURI;
	/**
	 * Persistence system.
	 */
	private KahaDBPersistenceAdapter persistenceAdapter;
	/**
	 * Broker.
	 */
	private BrokerService broker;

	
	/**
	 * Sets the broker name.
	 *
	 * @param brokerName the new broker name
	 */
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	
	/**
	 * Sets the jms uri.
	 *
	 * @param jmsURI the new jms uri
	 */
	public void setJmsURI(String jmsURI) {
		this.jmsURI = jmsURI;
	}
	/**
	 * Sets the XA URI.
	 * @param jmsXAURI jms URI
	 */
	public void setJmsXAURI(String jmsXAURI) {
		this.jmsXAURI = jmsXAURI;
	}
	/**
	 * Sets the stomp URI.
	 * @param stompURI stomp URI
	 */
	public void setStompURI(String stompURI) {
		this.stompURI = stompURI;
	}
	
	/**
	 * Sets the stomp xauri.
	 *
	 * @param stompXAURI the new stomp xauri
	 */
	public void setStompXAURI(String stompXAURI) {
		this.stompXAURI = stompXAURI;
	}
	
	/**
	 * Sets the websocket uri.
	 *
	 * @param websocketURI the new websocket uri
	 */
	public void setWebsocketURI(String websocketURI) {
		this.websocketURI = websocketURI;
	}
	
	/**
	 * Sets the websocket xauri.
	 *
	 * @param websocketXAURI the new websocket xauri
	 */
	public void setWebsocketXAURI(String websocketXAURI) {
		this.websocketXAURI = websocketXAURI;
	}
	
	/**
	 * Instantiates a new broker factory.
	 */
	public BrokerFactory() {
	}
	
	/**
	 * Inits the factory.
	 *
	 * @throws Exception the exception when something is wrong.
	 */
	public void init() throws Exception {
		BrokerService broker = new BrokerService();
		broker.setBrokerName(brokerName);
		broker.setShutdownOnMasterFailure(true);
		broker.setUseJmx(true);
		broker.setDestinationPolicy(createPolicyMap());
		broker.setManagementContext(createManagementContext());
		broker.setPersistenceAdapter(createPersistenceAdapter());
		broker.setPlugins(createPlugins());
		broker.setSystemUsage(createSystemUsage());
		broker.setTransportConnectors(createTransportConnectors());
		this.broker = broker;
		broker.start();
	}
	
	/**
	 * Shutdown.
	 *
	 * @throws Exception the exception.
	 */
	public void shutdown() throws Exception {
		this.broker.stop();
		this.broker = null;
		this.persistenceAdapter = null;
	}
	
	/**
	 * Creates a new transport object.
	 *
	 * @return the list< transport connector>
	 */
	private List<TransportConnector> createTransportConnectors() {
		List<TransportConnector> ret = new ArrayList<>();
		TransportConnector stompConnector = new TransportConnector();
		stompConnector.setName("stomp");
		stompConnector.setUri(URI.create(stompURI));
		ret.add(stompConnector);
		
		TransportConnector stompXAConnector = new TransportConnector();
		stompXAConnector.setName("stompXA");
		stompXAConnector.setUri(URI.create(stompXAURI));
		ret.add(stompXAConnector);
		
		TransportConnector websocketConnector = new TransportConnector();
		websocketConnector.setName("websocket");
		websocketConnector.setUri(URI.create(websocketURI));
		ret.add(websocketConnector);

		TransportConnector websocketXAConnector = new TransportConnector();
		websocketXAConnector.setName("websocketXA");
		websocketXAConnector.setUri(URI.create(websocketXAURI));
		ret.add(websocketXAConnector);

		TransportConnector jmsConnector = new TransportConnector();
		jmsConnector.setName("jms");
		jmsConnector.setUri(URI.create(jmsURI));
		ret.add(jmsConnector);

		TransportConnector jmsXAConnector = new TransportConnector();
		jmsXAConnector.setName("jmsXA");
		jmsXAConnector.setUri(URI.create(jmsXAURI));
		ret.add(jmsXAConnector);

		return ret;
	}
	
	/**
	 * Creates a new system usage.
	 *
	 * @return the system usage
	 */
	private SystemUsage createSystemUsage() {
		SystemUsage ret = new SystemUsage();
		MemoryUsage memoryUsage = new MemoryUsage();
		MemoryPropertyEditor memoryEditor = new MemoryPropertyEditor();
		memoryEditor.setAsText("420 mb");
		memoryUsage.setLimit((long) memoryEditor.getValue());
		ret.setMemoryUsage(memoryUsage);
		StoreUsage storeUsage = new StoreUsage();
		storeUsage.setStore(this.persistenceAdapter);
		MemoryPropertyEditor storeEditor = new MemoryPropertyEditor();
		storeEditor.setAsText("1 gb");
		storeUsage.setLimit((long) storeEditor.getValue());
		ret.setStoreUsage(storeUsage);
		TempUsage tempUsage = new TempUsage();
		
		MemoryPropertyEditor tempEditor = new MemoryPropertyEditor();
		tempEditor.setAsText("200 mb");
		tempUsage.setLimit((long) tempEditor.getValue());
		ret.setTempUsage(tempUsage);
		return ret;
	}
	/**
	 * Creates broker plugins.
	 * @return the plugins.
	 */
	private BrokerPlugin[] createPlugins() {
		BrokerPlugin[] ret = new BrokerPlugin[1];
		ret[0] = new LoggingBrokerPlugin();
		return ret;
	}
	/**
	 * Creates the persistence adapter.
	 * @return the persistence adapter.
	 */
	private PersistenceAdapter createPersistenceAdapter() {
		KahaDBPersistenceAdapter adapter = new KahaDBPersistenceAdapter();
		adapter.setDirectory(new File("activeMQ/kahadb-jms"));
		adapter.setEnableJournalDiskSyncs(false);
		adapter.setIndexWriteBatchSize(10000);
		adapter.setIndexCacheSize(1000);
		this.persistenceAdapter = adapter; 
		return adapter;
	}
	/**
	 * Creates the management context.
	 * @return the context.
	 */
	private ManagementContext createManagementContext() {
		ManagementContext ret = new ManagementContext();
		ret.setCreateConnector(true);
		return ret;
	}
	/**
	 * Creates the policy map.
	 * @return the policy map.
	 */
	private PolicyMap createPolicyMap() {
		PolicyMap ret = new PolicyMap();
		ret.setPolicyEntries(createPolicyEntries());
		return ret;
	}
	/**
	 * Policy entries.
	 * @return the policy entries.
	 */
	private List createPolicyEntries() {
		List<PolicyEntry> entries = new ArrayList<>();
		entries.add(createPolicyEntry());
		return entries;
	}
	/**
	 * The default policy entry.
	 * @return the entry.
	 */
	private PolicyEntry createPolicyEntry() {
		PolicyEntry ret = new PolicyEntry();
		ret.setQueue(">");
		ret.setProducerFlowControl(true);
		MemoryPropertyEditor memoryEditor = new MemoryPropertyEditor();
		memoryEditor.setAsText("20mb");
		ret.setMemoryLimit((long) memoryEditor.getValue());
		ret.setDeadLetterStrategy(createDeadLetterStrategy());
		return ret;
	}
	/**
	 * Creates the DLQ strategy.
	 * @return the strategy.
	 */
	private DeadLetterStrategy createDeadLetterStrategy() {
		IndividualDeadLetterStrategy ret = new IndividualDeadLetterStrategy();
		ret.setQueuePrefix("DLQ.");
		ret.setUseQueueForQueueMessages(true);
		return ret;
	}
}
