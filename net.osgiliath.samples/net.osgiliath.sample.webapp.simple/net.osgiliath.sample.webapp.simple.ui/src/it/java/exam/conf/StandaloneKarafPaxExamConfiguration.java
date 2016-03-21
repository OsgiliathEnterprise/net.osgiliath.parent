package exam.conf;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;

import net.osgiliath.module.exam.AbstractPaxExamKarafConfiguration;

public class StandaloneKarafPaxExamConfiguration  extends
AbstractPaxExamKarafConfiguration {

@Override
protected Option featureToTest() {

return features(
    maven().artifactId("net.osgiliath.sample.webapp.simple.features")
        .groupId("net.osgiliath.samples").type("xml").classifier("features")
        .versionAsInProject(), "net.osgiliath.sample.webapp.simple.ui.itests");
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
