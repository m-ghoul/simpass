package app;

import javax.swing.*;

import model.Account;
import model.SHA3;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;

public class SignupFrame extends JFrame implements ActionListener {

	// Constructor for the sign up screen

	SignupFrame() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Initialization of the components on the sign up screen

	Container container = getContentPane();
	JLabel signupLabel = new JLabel("Sign Up");
	JLabel descLabel = new JLabel("Fill in the following form to register for an account");
	JLabel nameLabel = new JLabel("Name");
	JTextField nameTextField = new JTextField();
	JLabel emailLabel = new JLabel("Email address");
	JTextField emailTextField = new JTextField();
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField();
	JLabel confirmPasswordLabel = new JLabel("Confirm password");
	JPasswordField confirmPasswordField = new JPasswordField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton submitButton = new JButton("Submit");
	JButton cancelButton = new JButton("Cancel");

	// Method to set the layout of the login screen to its default value

	public void setLayoutManager() {
		container.setLayout(null);
	}

	// Method to define certain properties of components such as icons, location and
	// size

	public void setLocationAndSize() {
		signupLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signupLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		signupLabel.setBounds(100, 60, 300, 30);
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		descLabel.setBounds(100, 85, 300, 30);
		nameLabel.setBounds(150, 125, 200, 30);
		nameTextField.setBounds(150, 150, 200, 30);
		emailLabel.setBounds(150, 175, 200, 30);
		emailTextField.setBounds(150, 200, 200, 30);
		passwordLabel.setBounds(150, 225, 200, 30);
		passwordField.setBounds(150, 250, 200, 30);
		confirmPasswordLabel.setBounds(150, 275, 200, 30);
		confirmPasswordField.setBounds(150, 300, 200, 30);
		showPassword.setBounds(150, 325, 200, 30);
		submitButton.setBounds(125, 375, 100, 30);
		cancelButton.setBounds(275, 375, 100, 30);
	}

	// Method that adds all of the defined components to the container of the sign
	// up screen

	public void addComponentsToContainer() {
		container.add(signupLabel);
		container.add(descLabel);
		container.add(nameLabel);
		container.add(nameTextField);
		container.add(emailLabel);
		container.add(emailTextField);
		container.add(passwordLabel);
		container.add(passwordField);
		container.add(confirmPasswordLabel);
		container.add(confirmPasswordField);
		container.add(showPassword);
		container.add(submitButton);
		container.add(cancelButton);

	}

	// Method to verify that the email entered is valid

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
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

	// Method that saves the account information into the accounts.txt text file

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		submitButton.addActionListener(this);
		cancelButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the sign up screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		// When the user clicks on the submit button, the application will perform
		// various forms of validation / verification on the data and then perform
		// the necessary actions if any validation or verification fails. Otherwise, it
		// will create the account and save its details

		if (e.getSource() == submitButton) {

			// Checking if a field has been left blank and acting accordingly

			if (nameTextField.getText().isBlank() || emailTextField.getText().isBlank()
					|| passwordField.getText().isBlank() || confirmPasswordField.getText().isBlank()) {
				JOptionPane.showMessageDialog(this, "All fields are required", "Error!", JOptionPane.ERROR_MESSAGE);
			}

			// Checking if the user's name contains any numbers and acting accordingly

			else if (nameTextField.getText().matches(".*\\d.*")) {
				JOptionPane.showMessageDialog(this, "The name field must not contain any numbers", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}

			// Checking if the email address entered is valid and acting accordingly

			else if (!isValid(emailTextField.getText())) {
				JOptionPane.showMessageDialog(this, "The email address you have entered is invalid", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}

			// Checking if the password entered is strong enough and acting accordingly

			else if (!isStrong(passwordField.getText())) {

				JOptionPane.showMessageDialog(this,
						"Your password must contain at least 8 characters, an uppercase letter, a lowercase letter, a number and a special character",
						"Error!", JOptionPane.ERROR_MESSAGE);
			}

			// Checking if the password entered in the password field is the same as the
			// password entered in the confirm password field and acting accordingly.

			else if (!confirmPasswordField.getText().equals(passwordField.getText())) {
				passwordField.setText("");
				confirmPasswordField.setText("");
				JOptionPane.showMessageDialog(this, "The passwords don't match", "Error!", JOptionPane.ERROR_MESSAGE);
			} else {

				// If all of the above validations check out, a new account is created and saved
				// in a text file

				String passSalt = SHA3.getSalt(16);
				String hashedPass = SHA3.bytesToHex(SHA3.hash(passSalt + passwordField.getText()));
				Account acc = new Account(nameTextField.getText(), emailTextField.getText(), hashedPass, passSalt);
				acc.writeToFileSecure();
				JOptionPane.showMessageDialog(this, "Your account has been created successfully", "Account Creation",
						JOptionPane.INFORMATION_MESSAGE);
				LoginFrame login = new LoginFrame();
				login.setTitle("SimPass");
				login.setBounds(10, 10, 500, 500);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				login.setLocation(dim.width / 2 - login.getSize().width / 2,
						dim.height / 2 - login.getSize().height / 2);
				login.setVisible(true);
				login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				login.setResizable(false);
				login.getIconImage();
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

		// If the cancel button is pressed, the user will be taken back to the login
		// screen

		if (e.getSource() == cancelButton) {
			LoginFrame login = new LoginFrame();
			login.setTitle("SimPass");
			login.setBounds(10, 10, 500, 500);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			login.setLocation(dim.width / 2 - login.getSize().width / 2, dim.height / 2 - login.getSize().height / 2);
			login.setVisible(true);
			login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			login.setResizable(false);
			login.getIconImage();
			setVisible(false);
			dispose();
		}
	}
}