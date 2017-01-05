package com.inspirenetz.api.core.license;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract implementation of the license interface use by the license manager.
 * 
 * @author FFL
 * 
 */
public abstract class AbstractLicense implements Serializable, ILicense {

	private static final long serialVersionUID = -144058457108187374L;

	/**
	 * License type for lifetime version. Always valid.
	 */
	public static final String TYPE_LIFETIME = "lifetime";
	/**
	 * License type for single version. This type is valid for the given
	 * version.
	 */
	public static final String TYPE_SINGLE_VERSION = "single-version";
	/**
	 * License type for trial version. This type is valid until the expiration
	 * date.
	 */
	public static final String TYPE_TRIAL = "trial";

	private String email;

	private Date expiration;

	private String licenseNumber;

	private String licenseType;
	private String name;
	private String version;
    private Integer noOfConcurrentUsers;

	/**
	 * Create a new license with default property value.
	 */
	public AbstractLicense() {
		name = "";
		email = "";
		licenseNumber = "";
		expiration = new Date();
		version = "";
		licenseType = TYPE_TRIAL;

        noOfConcurrentUsers = 1;
	}

	/**
	 * Return the associated email value.
	 * 
	 * @return the email or null
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * @return the licenseNumber
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * Return the license type.
	 * 
	 * @return the licenseType
	 */
	public String getLicenseType() {
		return licenseType;
	}

	/**
	 * Return the name associated with the license.
	 * 
	 * @return the name or null
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the license version
	 * 
	 * @return the version or null
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the associated email
	 * 
	 * @param email
	 *            the email or null.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Set the license expiration date. Required with TYPE_TRIAL
	 * 
	 * @param expiration
	 *            the expiration date or null.
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	/**
	 * Sets the license number.
	 * 
	 * @param licenseNumber
	 *            the licenseNumber
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}



    // Get the No Of Concurrent Users
    public Integer getNoOfConcurrentUsers() {
        return noOfConcurrentUsers;
    }

    /**
     * Sets the No Of ConcurrentUsers
     *
     *    No Of ConcurrentUsers
     *            the noOfConcurrentUsers
     */
    public void setNoOfConcurrentUsers(Integer noOfConcurrentUsers) {
        this.noOfConcurrentUsers = noOfConcurrentUsers;
    }

    /**
	 * Sets the license type.
	 * 
	 * @param licenseType
	 *            the licenseType, one of the TYPE_* constants (can't be null).
	 */
	public void setLicenseType(String licenseType) {
		if (licenseType == null) {
			throw new NullPointerException();
		}
		this.licenseType = licenseType;
	}

	/**
	 * Sets the name associated with the license
	 * 
	 * @param name
	 *            the name or null
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the license version. Required with SINGLE_VERSION.
	 * 
	 * @param version
	 *            the version or null
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Check if the given license object is valid.
	 * 
	 //* @param license
	 *            the license object
	 */
	public void validate(Date currentDate, String currentVersion)
			throws LicenseException {

		validateExpiration(new Date());

		validateVersion(currentVersion);

	}

	/**
	 * Used to validate the expiration date according to the license type.
	 * 
	 * @param currentDate
	 *            the current date.
	 * @throws LicenseExpiredException
	 */
	protected void validateExpiration(Date currentDate)
			throws LicenseExpiredException {
		if (getLicenseType().equals(TYPE_TRIAL)) {
			if (getExpiration() == null || currentDate.after(getExpiration())) {
				throw new LicenseExpiredException();
			}
		}
		// The expiration date doesn't matter for a single version license or a
		// lifetime version.
	}

	/**
	 * Used to validate the version according to the license type.
	 * 
	 * @param currentVersion
	 * @throws LicenseVersionExpiredException
	 */
	protected void validateVersion(String currentVersion)
			throws LicenseVersionExpiredException {

		if (getLicenseType().equals(TYPE_SINGLE_VERSION)) {
			if (getVersion() == null) {
				throw new LicenseVersionExpiredException();
			}
			Pattern pattern = Pattern.compile(getVersion());
			Matcher matcher = pattern.matcher(currentVersion);
			if (!matcher.matches()) {
				throw new LicenseVersionExpiredException();
			}
		}

	}

    @Override
    public String toString() {
        return "AbstractLicense{" +
                "email='" + email + '\'' +
                ", expiration=" + expiration +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", noOfConcurrentUsers='" + noOfConcurrentUsers + '\'' +

                '}';
    }
}
