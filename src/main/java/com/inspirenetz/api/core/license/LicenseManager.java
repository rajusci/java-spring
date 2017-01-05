package com.inspirenetz.api.core.license;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * This the main entry point of the licensing module. This class should be used
 * to create and check license files.
 * <p>
 * Generally, an application will not required more then one instance of license
 * manager.
 * 
 * @author FFL
 * 
 */
public class LicenseManager {
	private static final int SIZE = 2048;
	/**
	 * The encryption manager used by this class.F
	 */
	private EncryptionManager encryptionManager;

	/**
	 * Create a new license manager.
	 * 
	 * @param publicKey
	 *            the public key filename.
	 * @param privateKey
	 *            the private key filename (null if not available).
	 * @throws java.security.GeneralSecurityException
	 *             if the provided key are invalid.
	 * @throws java.io.IOException
	 *             if the file doesn't exists
	 */
	public LicenseManager(String publicKey, String privateKey)
			throws GeneralSecurityException, IOException {
		byte[] pubdata = EncryptionManager.readAll(new File(publicKey));
		byte[] privdata = null;
		if (privateKey != null) {
			privdata = EncryptionManager.readAll(new File(privateKey));
		}
		this.encryptionManager = new EncryptionManager(pubdata, privdata);
	}

	/**
	 * Create a new license manager.
	 * 
	 * @param publicKey
	 *            the public key file.
	 * @param privateKey
	 *            the private key file (null if not available).
	 * @throws java.security.GeneralSecurityException
	 *             if the provided key are invalid.
	 * @throws java.io.IOException
	 *             if the file doesn't exists
	 */
	public LicenseManager(File publicKey, File privateKey)
			throws GeneralSecurityException, IOException {
		byte[] pubdata = EncryptionManager.readAll(publicKey);
		byte[] privdata = null;
		if (privateKey != null) {
			privdata = EncryptionManager.readAll(privateKey);
		}
		this.encryptionManager = new EncryptionManager(pubdata, privdata);
	}

	/**
	 * Create a new license manager.
	 * 
	 * @param publicKey
	 *            an input stream containing the public key
	 * @param privateKey
	 *            an input stream containing the private key
	 */
	public LicenseManager(InputStream publicKey, InputStream privateKey)
			throws GeneralSecurityException, IOException {
		byte[] pubdata = EncryptionManager.readAll(publicKey);
		byte[] privdata = null;
		if (privateKey != null) {
			privdata = EncryptionManager.readAll(privateKey);
		}
		this.encryptionManager = new EncryptionManager(pubdata, privdata);
	}

	/**
	 * Create a new license manager. Generally, an application will not required
	 * more then one instance of license manager.
	 * 
	 * @param publicKey
	 *            the public key (can't be null).
	 * 
	 * @param privateKey
	 *            the private key (null if not available).
	 * @throws java.security.GeneralSecurityException
	 *             if the provided key are invalid.
	 */
	public LicenseManager(byte[] publicKey, byte[] privateKey)
			throws GeneralSecurityException {
		this.encryptionManager = new EncryptionManager(publicKey, privateKey);
	}

	/**
	 * Read the content of an encrypted license file.
	 * 
	 * @param file
	 *            the location to the license file.
	 * @return the license object if the license file is valid, null otherwise.
	 * @throws java.io.IOException
	 *             if file not found or read error.
	 * @throws java.security.SignatureException
	 *             if this signature algorithm is unable to process the content
	 *             of the file
	 * @throws java.security.NoSuchAlgorithmException
	 *             if the SHA algorithm doesn't exists
	 * @throws java.security.InvalidKeyException
	 *             if the public key is invalid
	 * @throws ClassNotFoundException
	 *             if the implementation of {@link ILicense} stored in the file
	 *             can't be found
	 */
	public ILicense readLicenseFile(File file) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException, SignatureException,
			ClassNotFoundException {

		// Read the content of the file
		byte[] sig;
		byte[] data;
		ObjectInputStream fileIn = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(file)));
		try {
			int sigLength = fileIn.readInt();
			sig = new byte[sigLength];
			fileIn.read(sig);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			byte[] buf = new byte[SIZE];
			int len;
			while ((len = fileIn.read(buf)) != -1) {
				dataStream.write(buf, 0, len);
			}
			dataStream.flush();
			data = dataStream.toByteArray();
			dataStream.close();
		} finally {
			fileIn.close();
		}

		// Validate the signature
		if (!encryptionManager.verify(data, sig)) {
			return null;
		}

		// Read the license object from the data.
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
				data));
		try {
			ILicense license = (ILicense) in.readObject();
			return license;
		} finally {
			in.close();
		}

	}

	/**
	 * Used to serialize a license object.
	 * 
	 * @param license
	 *            the license object.
	 * @param file
	 *            the location where to save the new license file. If file
	 *            exists, it's overwrite.
	 * @throws java.io.IOException
	 *             if the file doesn't exists or can't be written to
	 * @throws java.security.SignatureException
	 *             if this signature algorithm is unable to process the license
	 *             data
	 * @throws java.security.NoSuchAlgorithmException
	 *             if the algorithm SHA is not supported
	 * @throws java.security.InvalidKeyException
	 *             if the private key is invalid.
	 */
	public void writeLicense(ILicense license, File file) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		// Write the license information into a byte array.
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(dataStream);
		out.writeObject(license);
		byte[] data = dataStream.toByteArray();
		out.close();

		// Then sign the byte array
		byte[] signature = this.encryptionManager.sign(data);

		// Write all the data into one single file.
		ObjectOutputStream fileOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(file)));
		try {
			fileOut.writeInt(signature.length);
			fileOut.write(signature);
			fileOut.write(data);
			fileOut.flush();
		} finally {
			fileOut.close();
		}

	}

}
