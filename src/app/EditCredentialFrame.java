package app;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sun.tools.javac.Main;

import model.Account;

public class EditCredentialFrame extends JFrame implements ActionListener {

	// Constructor for the edit credential screen

	EditCredentialFrame(Account a) {
		retrieveAccountInfo(a);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Method that retrieves the user's account information (needed in later
	// methods)

	private String accEmail;
	private String accName;
	private String accPass;

	void retrieveAccountInfo(Account a) {
		this.accEmail = a.getEmail();
		this.accName = a.getName();
		this.accPass = a.getPassword();
	}

	// Initialization of the components of the edit credential screen

	Container container = getContentPane();
	JLabel addLabel = new JLabel("Edit Credential");
	JLabel descLabel = new JLabel(
			"<html><p>Edit the details of your credential and press 'Save' to save your changes to your vault or 'Cancel' to return to your vault </p></html>");
	JLabel credNameLabel = new JLabel("Credential Name");
	JTextField credNameField = new JTextField();
	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField();
	JLabel currentPasswordLabel = new JLabel("Current Password");
	JPasswordField currentPasswordField = new JPasswordField();
	JLabel newPasswordLabel = new JLabel("New Password");
	JPasswordField newPasswordField = new JPasswordField();
	JLabel confirmPasswordLabel = new JLabel("Confirm Password");
	JPasswordField confirmPasswordField = new JPasswordField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton saveButton = new JButton("Save");
	JButton cancelButton = new JButton("Cancel");

	// Method to set the layout of the edit credential screen to its default value

	public void setLayoutManager() {
		container.setLayout(null);
	}

	// Method to define certain properties of components such as icons, location and
	// size

	public void setLocationAndSize() {
		addLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		addLabel.setBounds(100, 35, 300, 30);
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		descLabel.setBounds(50, 60, 400, 50);
		credNameLabel.setBounds(150, 105, 200, 30);
		credNameField.setBounds(150, 130, 200, 30);
		usernameLabel.setBounds(150, 155, 200, 30);
		usernameField.setBounds(150, 180, 200, 30);
		usernameField.setEditable(false);
		currentPasswordLabel.setBounds(150, 205, 200, 30);
		currentPasswordField.setBounds(150, 230, 200, 30);
		newPasswordLabel.setBounds(150, 255, 200, 30);
		newPasswordField.setBounds(150, 280, 200, 30);
		confirmPasswordLabel.setBounds(150, 305, 200, 30);
		confirmPasswordField.setBounds(150, 330, 200, 30);
		showPassword.setBounds(150, 355, 200, 30);
		saveButton.setBounds(125, 400, 100, 30);
		cancelButton.setBounds(275, 400, 100, 30);
	}

	// Method that adds all of the defined components to the container of the edit
	// credential screen

	public void addComponentsToContainer() {
		container.add(addLabel);
		container.add(descLabel);
		container.add(credNameLabel);
		container.add(credNameField);
		container.add(usernameLabel);
		container.add(usernameField);
		container.add(currentPasswordLabel);
		container.add(currentPasswordField);
		container.add(newPasswordLabel);
		container.add(newPasswordField);
		container.add(confirmPasswordLabel);
		container.add(confirmPasswordField);
		container.add(showPassword);
		container.add(saveButton);
		container.add(cancelButton);
	}

	// Method to check if the password entered is strong

	public static boolean isStrong(String input) {
		int n = input.length();
		boolean hasLower = false, hasUpper = false, hasDigit = false, specialChar = false;
		Set<Character> set = new HashSet<Character>(
				Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));
		for (char i : input.toCharArray()) {
			if (Character.isLowerCase(i))
				hasLower = true;
			if (Character.isUpperCase(i))
				hasUpper = true;
			if (Character.isDigit(i))
				hasDigit = true;
			if (set.contains(i))
				specialChar = true;
		}

		if (hasDigit && hasLower && hasUpper && specialChar && (n >= 8)) {
			return true;
		}
		return false;
	}

	// Method to initialize a FileReader object to read the credentials file

	BufferedReader getFileReader() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File("src/files/credentials.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return reader;
	}

	// Method to retrieve the current password for the

	public String getCurrentPassword() {
		String currentPass = null;
		try {
			BufferedReader reader = getFileReader();
			String delimiter = ":";
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.split(delimiter)[1].equals(credNameField.getText())
						&& line.split(delimiter)[2].equals(usernameField.getText())) {
					currentPass = line.split(delimiter)[3];
					return currentPass;
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return currentPass;
	}

	// Method to find the line in the credentials text file that is to be replaced

	public String getCredentialLine() {
		try {
			BufferedReader reader = getFileReader();
			String delimiter = ":";
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.split(delimiter)[1].equals(credNameField.getText())
						&& line.split(delimiter)[2].equals(usernameField.getText())) {
					return line;
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// Method to overwrite a line in the credentials file

	public void overwriteCredLine(String line) {
		File file = new File("src/files/credentials.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		while (scanner.hasNextLine()) {
			buffer.append(scanner.nextLine() + System.lineSeparator());
		}
		String fileContents = buffer.toString();
		scanner.close();
		String oldLine = line;
		String newLine = accEmail + ":" + credNameField.getText() + ":" + usernameField.getText() + ":"
				+ newPasswordField.getText();
		fileContents = fileContents.replaceAll(oldLine, newLine);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer.append(fileContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the edit credential screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		// When the user clicks on the save button, the application will perform
		// various forms of validation / verification on the data and then perform
		// the necessary actions if any validation or verification fails. Otherwise, it
		// will replace the old credential details with the new credential details and
		// notify the user

		if (e.getSource() == saveButton) {

			// Checking if a field has been left blank and acting accordingly

			if (credNameField.getText().isBlank() || usernameField.getText().isBlank()
					|| currentPasswordField.getText().isBlank() || newPasswordField.getText().isBlank()
					|| confirmPasswordField.getText().isBlank()) {
				JOptionPane.showMessageDialog(this, "All fields are required", "Error!", JOptionPane.ERROR_MESSAGE);

				// Checking if the current credential password matches the password in the
				// database and acting accordingly

			} else if (!currentPasswordField.getText().equals(getCurrentPassword())) {
				currentPasswordField.setText("");
				JOptionPane.showMessageDialog(this, "The current password is incorrect", "Error!",
						JOptionPane.ERROR_MESSAGE);

				// Checking if the password entered in the password field is the same as the
				// password entered in the confirm password field and acting accordingly

			} else if (!confirmPasswordField.getText().equals(newPasswordField.getText())) {
				newPasswordField.setText("");
				confirmPasswordField.setText("");
				JOptionPane.showMessageDialog(this, "The passwords don't match", "Error!", JOptionPane.ERROR_MESSAGE);

				// Replacing the old credential information with the new information, checking
				// if the new password is strong and notifying the user, notifying the user that
				// the credential was edited successfully and exiting the edit credential screen

			} else {
				overwriteCredLine(getCredentialLine());
				if (!isStrong(newPasswordField.getText())) {
					JOptionPane.showMessageDialog(this, "Weak Password!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				JOptionPane.showMessageDialog(this, "Your credential was edited successfully", "Edit Credential",
						JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			}
		}

		// If the show password check box is checked, the program will reveal the
		// password that is being entered into the current/new/confirm password fields
		// instead of "*"

		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				currentPasswordField.setEchoChar((char) 0);
				newPasswordField.setEchoChar((char) 0);
				confirmPasswordField.setEchoChar((char) 0);
			} else {
				currentPasswordField.setEchoChar('*');
				newPasswordField.setEchoChar('*');
				confirmPasswordField.setEchoChar('*');
			}
		}

		// If the cancel button is pressed, the user will be taken back to the main
		// application screen

		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		}
	}
}
