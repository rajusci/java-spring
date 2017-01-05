package com.inspirenetz.api.core.license.util;

/**
 * A mechanism to log errors throughout the license framework.
 * <p>
 * Clients may provide their own implementation to change how errors are logged
 * from within the license framework.
 * </p>
 * 
 */
public interface ILogger {
	/**
	 * Trace level (value: trace).
	 */
	public static final String TRACE = "trace";
	/**
	 * Debug level (value: debug).
	 */
	public static final String DEBUG = "debug";
	/**
	 * Info level (value: info).
	 */
	public static final String INFO = "info";
	/**
	 * Warn level (value: warn).
	 */
	public static final String WARN = "warn";
	/**
	 * Error level (value: error).
	 */
	public static final String ERROR = "error";

	/**
	 * Logs the given status.
	 * 
	 * @param level
	 *            The level
	 * @param message
	 *            The message to be logged.
	 */
	public void log(String level, String message);

	/**
	 * Logs the given exception.
	 * 
	 * @param level
	 * @param exception
	 */
	public void log(String level, Throwable exception);

}
