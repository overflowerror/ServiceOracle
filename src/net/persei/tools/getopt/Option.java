package net.persei.tools.getopt;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.message.StringHeader;

import net.persei.tools.StringHelper;
import net.persei.tools.getopt.exceptions.NecessaryOptionMissingException;
import net.persei.tools.getopt.exceptions.OptionAlreadyGivenException;
import net.persei.tools.getopt.exceptions.OptionDoesNotHaveArgumentException;
import net.persei.tools.getopt.exceptions.OptionException;
import net.persei.tools.getopt.exceptions.OptionNeedsArgumentException;

public class Option {
	
	private static final int LONG_OPTION_MAX_LENGTH = 10;
	private static final int MAX_LINE_LENGTH = 80;
	
	private Character shortOption;
	private String longOption;
	private ArgumentType argumentType = ArgumentType.NONE;
	private OptionType optionType = OptionType.OPTIONAL;
	private String synopsis = null;
	private boolean isSingleton = false;
	private boolean wasHandled = false;
	private OptionHandler handler;
	
	public Option() {
	}
	
	public Option(char option, OptionHandler handler) {
		this.shortOption = Character.valueOf(option);
		this.handler = handler;
	}
	
	public Option(String option, OptionHandler handler) {
		this.longOption = option;
		this.handler = handler;
	}
	
	public Option setSynopsis(String synopsis) {
		this.synopsis = synopsis;
		return this;
	}
	
	public Option setShortOption(char option) {
		this.shortOption = Character.valueOf(option);
		return this;
	}
	
	public Option setLongOption(String option) {
		this.longOption = option;
		return this;
	}
	
	public Option setArgumentType(ArgumentType type) {
		this.argumentType = type;
		return this;
	}
	
	public Option setOptionType(OptionType type) {
		this.optionType = type;
		return this;
	}
	
	public Option setHandler(OptionHandler handler) {
		this.handler = handler;
		return this;
	}
	
	public Option setSingleton(boolean singleton) {
		this.isSingleton = singleton;
		return this;
	}
	
	public boolean isValid() {
		return ((shortOption != null || longOption != null) 
				&& argumentType != null 
				&& optionType != null 
				&& handler != null);
	}
	
	public OptionType getOptionType() {
		return optionType;
	}
	
	public void handlingWasOkay() throws OptionException {
		if (!wasHandled && optionType == OptionType.NECESSARY)
			throw new NecessaryOptionMissingException(toString());
	}
	
	public boolean check(boolean isShort, String option, String argument) throws OptionException {
		if (isShort) {
			if (shortOption == null)
				return false;
			if (!option.equals(String.valueOf(shortOption)))
				return false;
			if (argument == null && argumentType == ArgumentType.NECESSARY)
				throw new OptionNeedsArgumentException("-" + option);
			if (argument != null && argumentType == ArgumentType.NONE)
				throw new OptionDoesNotHaveArgumentException("-" + option);
		} else {
			if (longOption == null)
				return false;
			if (!option.equals(longOption))
				return false;
			if (argument == null && argumentType == ArgumentType.NECESSARY)
				throw new OptionNeedsArgumentException("--" + option);
			if (argument != null && argumentType == ArgumentType.NONE)
				throw new OptionDoesNotHaveArgumentException("--" + option);
		}
		
		if (isSingleton && wasHandled) {
			throw new OptionAlreadyGivenException((isShort ? "-" : "--") + option);
		}
		wasHandled = true;
		handler.handle(argument);
		return true;
	}
	
	public String toString() {
		if (shortOption != null)
			return "-" + shortOption;
		if (longOption != null)
			return "--" + longOption;
		return "[no option set]";
	}

	public boolean hasSynopsis() {
		return synopsis != null;
	}
	
	public String getSynopsis() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(" ");
		
		if (shortOption == null)
			builder.append("  ");
		else
			builder.append("-").append(shortOption);
		
		builder.append("  ");
		
		int tmp = 0;
		
		if (longOption == null)
			tmp = LONG_OPTION_MAX_LENGTH + 2;
		else {
			builder.append("--").append(longOption);
			tmp = LONG_OPTION_MAX_LENGTH - longOption.length();
		}
		
		builder.append(StringHelper.repeat(tmp, " "));
		
		builder.append("  ");
		
		int offset = 1 + 2 + 2 + 2 + LONG_OPTION_MAX_LENGTH + 2;
			
		String syn = synopsis;
		
		boolean first = true;
		while(syn != null && syn.length() > 0) {
			if (!first)
				builder.append("\n").append(StringHelper.repeat(offset, " "));
			first = false;
			if (syn.length() > MAX_LINE_LENGTH - offset) {
				builder.append(syn.substring(0, MAX_LINE_LENGTH - offset));
				syn = syn.substring(MAX_LINE_LENGTH - offset);
			} else {
				builder.append(syn);
				syn = null;
			}
		}
		
		return builder.toString();
	}
}
