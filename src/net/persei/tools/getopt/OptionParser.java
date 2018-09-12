package net.persei.tools.getopt;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.persei.tools.exceptions.WhatTheFuckException;
import net.persei.tools.getopt.exceptions.OptionDoesNotHaveArgumentException;
import net.persei.tools.getopt.exceptions.OptionException;
import net.persei.tools.getopt.exceptions.OptionNotValidException;
import net.persei.tools.getopt.exceptions.UnknownOptionException;

public class OptionParser {
	
	private UnknownOptionBehaviour unknownOptionBehaviour = UnknownOptionBehaviour.ERROR;
	
	private List<Option> options;

	private HelpHandler helpHandler;
	
	public OptionParser() {
		this.options = new LinkedList<>();
	}
	
	public void addOption(Option option) throws OptionException {
		if (!option.isValid())
			throw new OptionNotValidException(option.toString());
		options.add(option);
	}
	
	public void setUnknownOptionBehaviour(UnknownOptionBehaviour b) {
		this.unknownOptionBehaviour = b;
	}
	
	public String[] parse(String[] args) throws OptionException {
		if (helpHandler != null && args.length > 0)
			helpHandler.setName(args[0]);
		
		int i;
		for (i = 0; i < args.length; i++) {
			String arg = args[i];
			
			if (arg.startsWith("--")) {
				arg = arg.substring(2);
				if (arg.length() == 0)
					break;
				String argument = null;
				String optionString = null;
				int equals = arg.indexOf("=");
				if (equals >= 0) {
					optionString = arg.substring(0, equals);
					if (arg.length() > equals + 1)
						argument = arg.substring(equals + 1);
				} else {
					optionString = arg;
				}
				boolean okay = false;
				for (Option option : options) {
					if (option.check(false, optionString, argument)) {
						okay = true;
						continue;
					}
				}
				
				if (!okay) {
					boolean end = false;
					switch (unknownOptionBehaviour) {
					case END:
						end = true;
						break;
					case ERROR:
						throw new UnknownOptionException("--" + optionString);
					case IGNORE:
						break;
					default:
						throw new WhatTheFuckException();
					}
					
					if (end)
						break;
				}
			} else if (arg.startsWith("-")) {
				arg = arg.substring(1);
				if (arg.length() == 0)
					break;
				String argument = null;
				if (args.length > i + 1)
					argument = args[i + 1];
				for(int j = 0; j < arg.length(); j++) {
					String optionString = arg.substring(j, j + 1);
					boolean okay = false;
					for (Option option : options) {
						try {
							if (arg.length() == j + 1 && argument != null) {
								okay = option.check(true, optionString, argument);
								i++;
							} else {
								okay = option.check(true, optionString, null);
							}
						} catch (OptionDoesNotHaveArgumentException e) {
							okay = option.check(true, optionString, null);
						} catch (OptionException e) {
							throw e;
						}
						if (okay)
							continue;
					}
					
					if (!okay) {
						boolean end = false;
						switch (unknownOptionBehaviour) {
						case END:
							end = true;
							break;
						case ERROR:
							throw new UnknownOptionException("-" + optionString);
						case IGNORE:
							break;
						default:
							throw new WhatTheFuckException();
						}
						
						if (end)
							break;
					}
				}
			} else {
				break;
			}
		}
		
		if (i == args.length + 1)
			return new String[0];
		
		return Arrays.asList(args).subList(i, args.length).toArray(new String[args.length - i]);
	}

	public void setHelp(Option option, String applicationName, String version, String copyright, String licence, String helpText, String appendex) throws OptionException {
		helpHandler = new HelpHandler(options, applicationName, version, copyright, licence, helpText, appendex);
		option.setHandler(helpHandler);
		
		addOption(option);
	}
	
}
