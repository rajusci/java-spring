package com.inspirenetz.api.core.license;

/**
 * This exception is throw by the key manager when the key is determined to be
 * black listed.
 * 
 * @author FFL
 * 
 */
public class KeyBlackListedException extends LicenseException {

	private static final long serialVersionUID = 4833729281645719038L;

	public KeyBlackListedException() {
		super("black listed key");
	}
}
