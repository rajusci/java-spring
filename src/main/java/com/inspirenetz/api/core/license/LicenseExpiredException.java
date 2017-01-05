package com.inspirenetz.api.core.license;

/**
 * Thrown when the license validation determine the license to be expired. The
 * expiration date should then by retrieve using the
 * {@link AbstractLicense#getExpiration()}.
 * 
 * @author FFL
 * 
 */
public class LicenseExpiredException extends LicenseException {

	private static final long serialVersionUID = -9069804052012922999L;

	/**
	 * Constructs a new exception with null as its detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * Throwable.initCause(java.lang.Throwable).
	 */
	public LicenseExpiredException() {
		super("license expired");
	}

}
