package app;

import javax.swing.*;

import model.Account;
import model.SHA3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFrame extends JFrame implements ActionListener {

	// Constructor for the login screen

	LoginFrame() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Initialization of the components on the login screen

	Container container = getContentPane();
	JLabel padlockLabel = new JLabel();
	JLabel welcomeLabel = new JLabel("Welcome to SimPass");
	JLabel descLabel = new JLabel("The secure, offline password storage solution");
	JLabel emailLabel = new JLabel("Email address");
	JTextField emailTextField = new JTextField();
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton loginButton = new JButton("Login");
	JButton signupButton = new JButton("Sign up");

	// Method to set the layout of the login screen to its default value

	public void setLayoutManager() {
		container.setLayout(null);
	}

	// Method to define certain properties of components such as icons, location and
	// size

	public void setLocationAndSize() {
		padlockLabel.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/padlock.png")));
		padlockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		padlockLabel.setBounds(200, 50, 100, 100);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		welcomeLabel.setBounds(150, 150, 200, 50);
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		descLabel.setBounds(100, 185, 300, 30);
		emailLabel.setBounds(150, 225, 200, 30);
		emailTextField.setBounds(150, 250, 200, 30);
		passwordLabel.setBounds(150, 275, 200, 30);
		passwordField.setBounds(150, 300, 200, 30);
		showPassword.setBounds(150, 325, 200, 30);
		loginButton.setBounds(150, 375, 100, 30);
		signupButton.setBounds(250, 375, 100, 30);
	}

	// Method that adds all of the defined components to the container of the login
	// screen

	public void addComponentsToContainer() {
		container.add(padlockLabel);
		container.add(welcomeLabel);
		container.add(descLabel);
		container.add(emailLabel);
		container.add(emailTextField);
		container.add(passwordLabel);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(signupButton);
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

	// Method to verify that the email and password entered are correct by accessing
	// the file containing the account details and looking for a match

//	private static boolean isCorrect(String email, String password) {
//		BufferedReader read;
//		try {
//			read = new BufferedReader(new FileReader("src/files/accounts.txt"));
//			String line;
//			while ((line = read.readLine()) != null) {
//				String[] var = line.split(" ");
//				if (var[1].equals(email) && var[2].equals(password)) {
//					Account acc = new Account(var[0], var[1], var[2]);
//					read.close();
//					return true;
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	private static boolean isCorrect(String email, String password) {
		
		BufferedReader read;
		try {
			read = new BufferedReader(new FileReader("src/files/accounts.txt"));
			String line;
			while ((line = read.readLine()) != null) {
				String[] var = line.split(" ");
				if (var[1].equals(email) && var[2].equals(SHA3.bytesToHex(SHA3.hash(var[3]+password)))) {
					Account acc = new Account(var[0], var[1], var[2], var[3]);
					read.close();
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Method to get the account holder's name to be displayed on the next screen if
	// login is successful

	private static String getAccountHolderName(String email, String password) {
		BufferedReader read;
		try {
			read = new BufferedReader(new FileReader("src/files/accounts.txt"));
			String line;
			while ((line = read.readLine()) != null) {
				String[] var = line.split(" ");
				if (var[1].equals(email) && var[2].equals(SHA3.bytesToHex(SHA3.hash(var[3]+password)))) {
					read.close();
					return var[0];
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		loginButton.addActionListener(this);
		signupButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the login screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		// When the user clicks on the login button, the application will perform
		// various
		// forms of validation / verification on the data and then perform
		// the necessary actions if any validation or verification fails. Otherwise, it
		// will take the user to the main application screen

		if (e.getSource() == loginButton) {
			String emailText;
			String passText;
			emailText = emailTextField.getText();
			passText = passwordField.getText();

			// Checking if a field has been left blank and acting accordingly

			if (emailText.isBlank() || passText.isBlank()) {
				emailTextField.setText("");
				passwordField.setText("");
				JOptionPane.showMessageDialog(this, "All fields are required", "Error!", JOptionPane.ERROR_MESSAGE);

				// Checking if the email address entered is valid and acting accordingly

			} else if (!isValid(emailText)) {
				emailTextField.setText("");
				emailTextField.setText("");
				passwordField.setText("");
				JOptionPane.showMessageDialog(this, "The email address you have entered is invalid", "Error!",
						JOptionPane.ERROR_MESSAGE);

				// Checking if the email address and password combination is correct and acting
				// accordingly

			} else if (isCorrect(emailText, passText)) {

				// If all of the above conditions check out, the user will be taken to the main
				// application screen

				JOptionPane.showMessageDialog(this, "Login Successful", "Success!", JOptionPane.INFORMATION_MESSAGE);
				Account acc = new Account(getAccountHolderName(emailText, passText), emailText, passText);
				MainFrame main = new MainFrame(acc);
				main.table = main.createTable();
				main.scrollPane = new JScrollPane(main.table);
				main.scrollPane.setBounds(25, 125, 500, 325);
				main.container.add(main.scrollPane);
				main.setTitle("SimPass");
				main.setBounds(10, 10, 700, 500);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				main.setLocation(dim.width / 2 - main.getSize().width / 2, dim.height / 2 - main.getSize().height / 2);
				main.setVisible(true);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setResizable(false);
				main.getIconImage();
				setVisible(false);
				dispose();
			} else {

				// Otherwise, an error message will be displayed

				passwordField.setText("");
				JOptionPane.showMessageDialog(this, "Invalid username or password", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// If the show password check box is checked, the program will reveal the
		// password that is being entered into the password field instead of "*"

		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		}

		// If the sign up button is clicked, the user will be redirected to the sign up
		// screen and the login screen will disappear

		if (e.getSource() == signupButton) {
			SignupFrame signup = new SignupFrame();
			signup.setTitle("SimPass");
			signup.setBounds(10, 10, 500, 500);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			signup.setLocation(dim.width / 2 - signup.getSize().width / 2,
					dim.height / 2 - signup.getSize().height / 2);
			signup.setVisible(true);
			signup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			signup.setResizable(false);
			signup.getIconImage();
			setVisible(false);
			dispose();
		}
	}
}