package net.persei.tools.getopt;

import java.util.List;
import java.util.stream.Collectors;

import net.persei.tools.getopt.exceptions.OptionException;
import net.persei.tools.getopt.exceptions.SilentShutdownException;

public class HelpHandler implements OptionHandler {

	private List<Option> options;
	private String applicationName;
	private String version;
	private String copyright;
	private String licence;
	private String helpText;
	private String appendex;
	private String executeableName;
	
	public HelpHandler(List<Option> options, String applicationName, String version, String copyright, String licence, String helpText, String appendex) {
		this.options = options.stream().filter(o -> o.hasSynopsis()).collect(Collectors.toList());
		this.applicationName = applicationName;
		this.version = version;
		this.copyright = copyright;
		this.licence = licence;
		this.helpText = helpText;
		this.appendex = appendex;
	}

	@Override
	public void handle(String argument) throws OptionException {
		if (executeableName == null)
			executeableName = "";
		
		if (applicationName != null) {
			System.out.print(applicationName);
			if (version != null)
				System.out.print(" " + version);
			
			if (copyright != null)
				System.out.print(" - (C) by " + copyright);
			
			System.out.println();
			
			if (licence != null) {
				System.out.println(licence);
			}
			
			System.out.println();
		}
		
		System.out.print("SYNOPSIS: " + executeableName + " [OPTIONS] ");
		
		if (appendex != null)
			System.out.print(appendex);
		
		System.out.println();
		System.out.println();
		
		if (helpText != null) {
			System.out.println(helpText);
			System.out.println();
		}
		
		if (options != null && options.size() > 0) {
			System.out.println("OPTIONS:");
			
			for (Option option : options) {
				System.out.println(option.getSynopsis());
			}
		}
		
		throw new SilentShutdownException(0);
	}

	public void setName(String name) {
		this.executeableName = name;
	}

}
