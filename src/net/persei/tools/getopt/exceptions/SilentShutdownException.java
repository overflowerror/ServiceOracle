package net.persei.tools.getopt.exceptions;

public class SilentShutdownException extends OptionException {

	private static final long serialVersionUID = -8807171464602371333L;
	
	private int errorCode;
	
	public SilentShutdownException(int errorCode) {
		super(String.valueOf(errorCode));

		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

}
