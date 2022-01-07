package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHA3 {

	// Method that hashes the plaintext in the parameter using the SHA3-256 hashing
	// algorithm

	public static byte[] hash(String plaintext) {

		// Setting the hashing algorithm to SHA3-256

		String algorithm = "SHA3-256";

		// Initializing a MessageDigest object to be used for the hashing process

		MessageDigest digest = null;

		// Creating a variable that contains a MessageDigest object that performs the
		// digest in SHA3-256 and surrounding it with a try-catch block to prevent
		// getting an exception at runtime

		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// Finally, the hashing operation is done using the digest variable and saved
		// into an array of bytes with the UTF 8 character set

		final byte[] hashBytes = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));

		// The array containing the hashed plaintext is then returned by the method

		return hashBytes;
	}

	// Method to generate a random nonce to be used as a salt to make sure each
	// password has a different hash

	public static String getSalt(int nonceSize) {

		// The nonce array of size nonceSize is initialized

		byte[] nonce = new byte[nonceSize];

		// Random numbers are generated by a SecureRandom object and put into the nonce
		// array and then converted to a String

		new SecureRandom().nextBytes(nonce);
		String nonceString = bytesToHex(nonce);

		// The generated nonce is then returned by the method

		return nonceString.toString();
	}

	// Method that converts any byte[] array to a string

	public static String bytesToHex(byte[] bytes) {

		// Initializing a new StringBuilder object to put the hexadecimal value of the
		// bytes into a string

		StringBuilder hexString = new StringBuilder(2 * bytes.length);

		// Looping through all the bytes in the bytes array and converting each byte
		// into a hexadecimal and then adding them to the newly created StringBuilder
		// object

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xff & bytes[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		// The string containing the full hexadecimal value is then returned by the
		// method

		return hexString.toString();
	}
}
