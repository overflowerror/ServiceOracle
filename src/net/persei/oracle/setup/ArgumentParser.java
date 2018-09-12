package net.persei.oracle.setup;

import java.io.File;
import java.io.FileNotFoundException;

import net.persei.oracle.Oracle;
import net.persei.oracle.exceptions.SetupException;
import net.persei.oracle.exceptions.ShutdownException;
import net.persei.tools.getopt.ArgumentType;
import net.persei.tools.getopt.Option;
import net.persei.tools.getopt.OptionParser;
import net.persei.tools.getopt.OptionType;
import net.persei.tools.getopt.exceptions.OptionAlreadyGivenException;
import net.persei.tools.getopt.exceptions.OptionDoesNotHaveArgumentException;
import net.persei.tools.getopt.exceptions.OptionException;
import net.persei.tools.getopt.exceptions.OptionNeedsArgumentException;
import net.persei.tools.getopt.exceptions.SilentShutdownException;
import net.persei.tools.getopt.exceptions.UnknownOptionException;

public class ArgumentParser {
	private static final String HELP_TEXT = "[help text]";

	private OptionParser optionParser;

	public ArgumentParser(Settings settings) {

		optionParser = new OptionParser();

		try {
			optionParser.addOption(new Option().setShortOption('c').setLongOption("config")
					.setOptionType(OptionType.NECESSARY).setArgumentType(ArgumentType.NECESSARY).setSingleton(true)
					.setSynopsis("set config file").setHandler((argument) -> {
						File file = new File(argument);
						if (!file.exists())
							throw new OptionException(new FileNotFoundException(argument));
						settings.setConfigFile(file);
					}));

			optionParser.addOption(new Option().setLongOption("client").setArgumentType(ArgumentType.NONE)
					.setOptionType(OptionType.OPTIONAL).setSingleton(true).setSynopsis("client mode")
					.setHandler((argument) -> {
						settings.setType(ExecutionType.CLIENT);
					}));

			optionParser.addOption(new Option().setLongOption("server").setArgumentType(ArgumentType.NONE)
					.setOptionType(OptionType.OPTIONAL).setSingleton(true).setSynopsis("client mode")
					.setHandler((argument) -> {
						settings.setType(ExecutionType.SERVER);
					}));

			optionParser.setHelp(
					new Option().setShortOption('h').setLongOption("help").setSynopsis("display this help"),
					Oracle.APPLICATION_NAME, Oracle.CURRENT_VERSION.toString(), Oracle.COPYRIGHT, Oracle.LICENCE,
					HELP_TEXT, "");

		} catch (OptionException e) {
			e.printStackTrace();
		}
	}

	public void parse(String[] args) throws SetupException {
		try {
			args = optionParser.parse(args);
		} catch (SilentShutdownException e) {
			throw new ShutdownException(e.getErrorCode());
		} catch (OptionNeedsArgumentException e) {
			System.err.println("The option " + e.getMessage() + " needs an argument.");
			throw new ShutdownException(ShutdownException.ERROR);
		} catch (OptionAlreadyGivenException e) {
			System.err.println("The option " + e.getMessage() + " was already given.");
			throw new ShutdownException(ShutdownException.ERROR);
		} catch (UnknownOptionException e) {
			System.err.println("The option " + e.getMessage() + " is unknown.");
			throw new ShutdownException(ShutdownException.ERROR);
		} catch (OptionDoesNotHaveArgumentException e) {
			System.err.println("The option " + e.getMessage() + " does not have an argument.");
			throw new ShutdownException(ShutdownException.ERROR);
		} catch (OptionException e) {
			throw new SetupException(e);
		}
	}

}
