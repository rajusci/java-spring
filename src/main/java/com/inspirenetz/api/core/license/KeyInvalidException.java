package com.inspirenetz.api.core.license;

/**
 * This exception is throw when the key manager determine the key as invalid
 * because of the checksum or because it's been wrongly generated.
 * 
 * @author FFL
 * 
 */
public class KeyInvalidException extends LicenseException {

	private static final long serialVersionUID = 3455646784833396158L;

	public KeyInvalidException() {
		super("invalid key");
	}
}
