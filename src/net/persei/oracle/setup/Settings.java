package net.persei.oracle.setup;

import java.io.File;

import net.persei.oracle.exceptions.SettingsNotValidException;

public class Settings {
	private ExecutionType type = DefaultSettings.DEFAULT_EXECUTION_TYPE;
	
	private File configFile = null;
	
	public void checkValidity() throws SettingsNotValidException {
		if (type == null)
			throw new SettingsNotValidException("No execution type set.");
		if (configFile == null)
			throw new SettingsNotValidException("No config file set.");
	}

	public ExecutionType getType() {
		return type;
	}
	
	public void setType(ExecutionType type) {
		this.type = type;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	
	

}
