#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.itests;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import net.osgiliath.helpers.exam.AbstractPaxExamKarafConfigurationFactory;
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
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITService extends AbstractPaxExamKarafConfigurationFactory {
	private static Logger LOG = LoggerFactory.getLogger(ITService.class);
	@Inject
	@Filter(timeout = 400000)
	private BootFinished bootFinished;
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
	builder.addTest(AbstractPaxExamKarafConfigurationFactory.class);
        builder.setHeader(Constants.EXPORT_PACKAGE, "${package}.itests");
        builder.setHeader(Constants.BUNDLE_MANIFESTVERSION, "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        return builder;
    }
	@Test
	public void testMyService()  {
	//TODO make the test!
	}
	
	@Override
	protected Option featureToTest() {
		
		return features(
				maven().groupId(System.getProperty(MODULE_GROUP_ID))
				.artifactId(
						System.getProperty(MODULE_GROUP_ID)
								+ ".features").type("xml")
				.classifier("features").versionAsInProject(),
		System.getProperty(MODULE_PARENT_ARTIFACT_ID) + ".itests");
	}
	static {
		// uncomment to enable debugging of this test class
		// paxRunnerVmOption = DEBUG_VM_OPTION;

	}

	@Configuration
	public Option[] config() {
		return createConfig();
	}
}