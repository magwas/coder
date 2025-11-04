package io.github.magwas.coder.dependencies.tests;

public class SystemExitSimulationException extends SecurityException {
	private final int exitCode;

	public SystemExitSimulationException(int exitCode) {
		super("System.exit(" + exitCode + ")");
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}
}
