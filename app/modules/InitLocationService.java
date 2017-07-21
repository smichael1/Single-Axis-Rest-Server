package modules;

import csw.services.ccs.BlockingAssemblyClient;
import csw.services.loc.LocationService;
import csw.services.sequencer.SequencerEnv;
import org.tmt.aps.ics.assembly.SingleAxisComponentHelper;
import csw.util.config.Configurations;
import static csw.util.config.Configurations.ConfigKey;
import csw.util.config.Configurations.SetupConfig;


public class InitLocationService implements Init {

	public InitLocationService() {
		
		System.out.println("Initializing location service...");
		LocationService.initInterface();
		System.out.println("Done");
		
	}
		
}
