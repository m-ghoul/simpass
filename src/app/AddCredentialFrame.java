package app;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import model.Account;
import model.Credential;

public class AddCredentialFrame extends JFrame implements ActionListener {

	// Constructor for the add credential screen

	AddCredentialFrame(Account a) {
		retrieveAccountInfo(a);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Method that retrieves the user's account information (needed to save the
	// credentials under the user's account)

	private String accEmail;
	private String accName;
	private String accPass;

	void retrieveAccountInfo(Account a) {
		this.accEmail = a.getEmail();
		this.accName = a.getName();
		this.accPass = a.getPassword();
	}

	// Initialization of the components of the add credential screen

	Container container = getContentPane();
	JLabel addLabel = new JLabel("Add Credential");
	JLabel descLabel = new JLabel(
			"<html><p>Fill in the following form with the details of your credential and press 'Save' to add it to your vault or 'Cancel' to return to your vault </p></html>");
	JLabel credNameLabel = new JLabel("Credential Name");
	JTextField credNameField = new JTextField();
	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField();
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField();
	JLabel confirmPasswordLabel = new JLabel("Confirm Password");
	JPasswordField confirmPasswordField = new JPasswordField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton saveButton = new JButton("Save");
	JButton cancelButton = new JButton("Cancel");

	// Method to set the layout of the add credential screen to its default value

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
		passwordLabel.setBounds(150, 205, 200, 30);
		passwordField.setBounds(150, 230, 200, 30);
		confirmPasswordLabel.setBounds(150, 255, 200, 30);
		confirmPasswordField.setBounds(150, 280, 200, 30);
		showPassword.setBounds(150, 305, 200, 30);
		saveButton.setBounds(125, 375, 100, 30);
		cancelButton.setBounds(275, 375, 100, 30);
	}

	// Method that adds all of the defined components to the container of the add
	// credential screen

	public void addComponentsToContainer() {
		container.add(addLabel);
		container.add(descLabel);
		container.add(credNameLabel);
		container.add(credNameField);
		container.add(usernameLabel);
		container.add(usernameField);
		container.add(passwordLabel);
		container.add(passwordField);
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

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the add credential screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		// When the user clicks on the save button, the application will perform
		// various forms of validation / verification on the data and then perform
		// the necessary actions if any validation or verification fails. Otherwise, it
		// will add the credential to that user's list of credentials

		if (e.getSource() == saveButton) {

			// Checking if a field has been left blank and acting accordingly

			if (credNameField.getText().isBlank() || usernameField.getText().isBlank()
					|| passwordField.getText().isBlank() || confirmPasswordField.getText().isBlank()) {
				JOptionPane.showMessageDialog(this, "All fields are required", "Error!", JOptionPane.ERROR_MESSAGE);

				// Checking if the password entered in the password field is the same as the
				// password entered in the confirm password field and acting accordingly

			} else if (!confirmPasswordField.getText().equals(passwordField.getText())) {
				passwordField.setText("");
				confirmPasswordField.setText("");
				JOptionPane.showMessageDialog(this, "The passwords don't match", "Error!", JOptionPane.ERROR_MESSAGE);
			} else {

				// Checking if the password entered is strong enough and acting accordingly

				if (!isStrong(passwordField.getText())) {
					JOptionPane.showMessageDialog(this, "Weak Password!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}

				// Creating a new credential, writing it to the file containing all
				// credentials, notifying the user that the credential was added
				// successfully and exiting the add credential screen

				Credential cred = new Credential(accEmail, credNameField.getText(), usernameField.getText(),
						passwordField.getText());
				cred.writeToFile();
				JOptionPane.showMessageDialog(this, "Your credential was added successfully", "Add Credential",
						JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			}
		}

		// If the show password check box is checked, the program will reveal the
		// password that is being entered into the password field instead of "*"

		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
				confirmPasswordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
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
