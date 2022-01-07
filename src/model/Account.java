package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;

public class Account implements Serializable {

	// Initialization of variables

	private String name;
	private String email;
	private String password;
	private String hashedPassword;
	private String salt;

	// 2 constructors, one containing the hashed account details and the other
	// containing the unhashed account details for internal use

	public Account(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Account(String name, String email, String hashedPassword, String salt) {
		this.name = name;
		this.email = email;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
	}

	// Getters and setters for all the variables of the Account class

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	// 2 toString methods. One for the constructor with hashing and the other for
	// the constructor without hashing

	@Override
	public String toString() {
		return getName() + " " + getEmail() + " " + getPassword() + "\n";
	}

	public String toStringSecure() {
		return getName() + " " + getEmail() + " " + getHashedPassword() + " " + getSalt() + "\n";
	}

	// 2 methods that write to the accounts.txt file depending on the kind of
	// constructor (with or without hashing) using the above toString methods

	public void writeToFile() {
		try {
			Writer output = null;
			String text = this.toString();
			File file = new File("src/files/accounts.txt");
			output = new PrintWriter(new FileWriter(file, true));
			output.append(text);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFileSecure() {
		try {
			Writer output = null;
			String text = this.toStringSecure();
			File file = new File("src/files/accounts.txt");
			output = new PrintWriter(new FileWriter(file, true));
			output.append(text);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
