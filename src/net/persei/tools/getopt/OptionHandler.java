package net.persei.tools.getopt;

import net.persei.tools.getopt.exceptions.OptionException;

@FunctionalInterface
public interface OptionHandler {

	void handle(String argument) throws OptionException;
	
}
