package model;

import java.io.*;

public class Credential {

	// Initialization of variables

	private String email;
	private String credname;
	private String username;
	private String password;

	// Constructor for the Credential class

	public Credential(String email, String credname, String username, String password) {
		this.email = email;
		this.credname = credname;
		this.username = username;
		this.password = password;
	}

	// Getters and setters for all of the variables of the Credential class

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCredname() {
		return credname;
	}

	public void setCredname(String credname) {
		this.credname = credname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// toString method for Credential class

	@Override
	public String toString() {
		return getEmail() + ":" + getCredname() + ":" + getUsername() + ":" + getPassword() + "\n";
	}

	// Method to write credentials to the credentials.txt file

	public void writeToFile() {
		try {
			Writer output = null;
			String text = this.toString();
			File file = new File("src/files/credentials.txt");
			output = new PrintWriter(new FileWriter(file, true));
			output.append(text);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
