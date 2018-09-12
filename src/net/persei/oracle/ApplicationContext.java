package net.persei.oracle;

import net.persei.oracle.exceptions.PropertyAlreadySetException;
import net.persei.oracle.exceptions.SetupException;
import net.persei.oracle.setup.Settings;

public class ApplicationContext {
	private ApplicationContext() {
	}
	
	private static ApplicationContext instance;
	
	public static ApplicationContext getInstance() {
		if (instance == null)
			instance = new ApplicationContext();
		return instance;
	}

	private Settings settings;
	
	public void setSettings(Settings settings) throws SetupException {
		if (this.settings != null)
			throw new PropertyAlreadySetException("settings");
		
		this.settings = settings;
	}
}
