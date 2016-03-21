package net.osgiliath.module.activemq.jms.broker;

import java.io.File;
import java.io.IOException;
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

public class BrokerFactory {

	private String brokerName;
	private String jmsURI;
	private String jmsXAURI;
	private String stompURI;
	private String stompXAURI;
	private String websocketURI;
	private String websocketXAURI;
	private KahaDBPersistenceAdapter persistenceAdapter;
	private BrokerService broker;

	
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public void setJmsURI(String jmsURI) {
		this.jmsURI = jmsURI;
	}
	public void setJmsXAURI(String jmsXAURI) {
		this.jmsXAURI = jmsXAURI;
	}
	public void setStompURI(String stompURI) {
		this.stompURI = stompURI;
	}
	public void setStompXAURI(String stompXAURI) {
		this.stompXAURI = stompXAURI;
	}
	public void setWebsocketURI(String websocketURI) {
		this.websocketURI = websocketURI;
	}
	public void setWebsocketXAURI(String websocketXAURI) {
		this.websocketXAURI = websocketXAURI;
	}
	public BrokerFactory() {
	}
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
	public void shutdown() throws Exception {
		this.broker.stop();
		this.broker = null;
		this.persistenceAdapter = null;
	}
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
	private BrokerPlugin[] createPlugins() {
		BrokerPlugin[] ret = new BrokerPlugin[1];
		ret[0] = new LoggingBrokerPlugin();
		return ret;
	}
	private PersistenceAdapter createPersistenceAdapter() {
		KahaDBPersistenceAdapter adapter = new KahaDBPersistenceAdapter();
		adapter.setDirectory(new File("activeMQ/kahadb-jms"));
		adapter.setEnableJournalDiskSyncs(false);
		adapter.setIndexWriteBatchSize(10000);
		adapter.setIndexCacheSize(1000);
		this.persistenceAdapter = adapter; 
		return adapter;
	}
	private ManagementContext createManagementContext() {
		ManagementContext ret = new ManagementContext();
		ret.setCreateConnector(true);
		return ret;
	}
	private PolicyMap createPolicyMap() {
		PolicyMap ret = new PolicyMap();
		ret.setPolicyEntries(createPolicyEntries());
		return ret;
	}
	private List createPolicyEntries() {
		List<PolicyEntry> entries = new ArrayList<>();
		entries.add(createPolicyEntry());
		return entries;
	}
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
	private DeadLetterStrategy createDeadLetterStrategy() {
		IndividualDeadLetterStrategy ret = new IndividualDeadLetterStrategy();
		ret.setQueuePrefix("DLQ.");
		ret.setUseQueueForQueueMessages(true);
		return ret;
	}
}
