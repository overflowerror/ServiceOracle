package net.persei.oracle;

import net.persei.oracle.client.OracleClient;
import net.persei.oracle.exceptions.SetupException;
import net.persei.oracle.exceptions.ShutdownException;
import net.persei.oracle.network.objects.Version;
import net.persei.oracle.network.objects.VersionMode;
import net.persei.oracle.server.OracleServer;
import net.persei.oracle.setup.ArgumentParser;
import net.persei.oracle.setup.ConfigParser;
import net.persei.oracle.setup.ExecutionType;
import net.persei.oracle.setup.Settings;

public class Oracle {
	
	public static final String APPLICATION_NAME = "ServiceOracle";
	public static final Version CURRENT_VERSION = new Version(0, 1, VersionMode.PRE_ALPHA);
	public static final String COPYRIGHT = "overflow";
	public static final String LICENCE = "Released under the MIT licence.";
	
	public static void main(String[] args) throws SetupException {
		
		try {
			Settings settings = getSettings(args);
			
			ApplicationContext context = ApplicationContext.getInstance();
			context.setSettings(settings);
			
			Application application;
			
			if (settings.getType() == ExecutionType.SERVER) {
				application = new OracleServer(); 
			} else {
				application = new OracleClient();
			}
			
			application.execute();
		
		} catch (ShutdownException e) {
			System.exit(e.getExitCode());
		}
	}

	private static Settings getSettings(String[] args) throws SetupException {
		Settings settings = new Settings();
		
		ArgumentParser argumentParser = new ArgumentParser(settings);
		argumentParser.parse(args);
		
		ConfigParser configParser = new ConfigParser(settings);
		configParser.parse();
		
		settings.checkValidity();
		
		return settings;
	}
}
