package com.inspirenetz.api.core.license;

/**
 * This exception is throw when the license version doesn't match the current
 * version.
 * 
 * @author FFL
 * 
 */
public class LicenseVersionExpiredException extends LicenseException {

	private static final long serialVersionUID = 8947235554238066208L;

	public LicenseVersionExpiredException() {
		super("version expired");
	}

}
