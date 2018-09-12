package net.persei.oracle.exceptions;

public class ShutdownException extends SetupException {

	private static final long serialVersionUID = -3829264217427478192L;
	
	public static final int ERROR = 1;
	public static final int SUCCESS = 0;
	
	private int exitCode;
	
	public ShutdownException(int exitCode) {
		super("Exitcode: " + exitCode);
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}
	
}
