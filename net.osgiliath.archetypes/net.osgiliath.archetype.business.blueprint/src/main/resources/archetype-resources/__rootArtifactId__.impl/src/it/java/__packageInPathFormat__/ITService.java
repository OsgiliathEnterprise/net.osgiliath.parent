#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import helper.exam.AbstractKarafPaxExamConfiguration;
import org.apache.karaf.features.BootFinished;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITService extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITService.class);
	@Inject
	@Filter(timeout = 400000)
	private BootFinished bootFinished;
	protected static final String BUNDLE_GROUP_ID = "bundle.groupId";
	protected static final String BUNDLE_ARTIFACT_ID = "bundle.parent.artifactId";
	@Test
	public void testMyService()  {
	//TODO make the test!
	}
	
	@Override
	protected Option featureToTest() {
		
		return features(
				maven().groupId(System.getProperty(BUNDLE_GROUP_ID))
				.artifactId(
						System.getProperty(BUNDLE_GROUP_ID)
								+ ".features").type("xml")
				.classifier("features").versionAsInProject(),
		System.getProperty(BUNDLE_ARTIFACT_ID) + ".itests");
	}
}