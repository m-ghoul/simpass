package app;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Account;
import model.Credential;

public class ViewCredentialFrame extends JFrame implements ActionListener {

	// Constructor for the view credential screen

	ViewCredentialFrame(Account a) {
		retrieveAccountInfo(a);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Method that retrieves the user's account information (needed to display the
	// account details on the view credentials screen)

	private String accEmail;
	private String accName;
	private String accPass;

	void retrieveAccountInfo(Account a) {
		this.accEmail = a.getEmail();
		this.accName = a.getName();
		this.accPass = a.getPassword();
	}

	// Initialization of the components of the view credential screen

	Container container = getContentPane();
	JLabel viewLabel = new JLabel("View Credential");
	JLabel descLabel = new JLabel("<html><p> The following are the details of the selected credential</p></html>");
	JLabel credNameLabel = new JLabel("Credential Name");
	JTextField credNameField = new JTextField("Credential Name goes here");
	JLabel usernameLabel = new JLabel("Username");
	JTextField usernameField = new JTextField("Credential username goes here");
	JLabel passwordLabel = new JLabel("Password");
	JPasswordField passwordField = new JPasswordField("Credential password goes here");
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton backButton = new JButton("Back");

	// Method to set the layout of the add credential screen to its default value

	public void setLayoutManager() {
		container.setLayout(null);
	}

	// Method to define certain properties of components such as icons, location and
	// size

	public void setLocationAndSize() {
		viewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		viewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		viewLabel.setBounds(100, 110, 300, 30);
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		descLabel.setBounds(50, 135, 400, 50);
		credNameLabel.setBounds(150, 175, 200, 30);
		credNameField.setBounds(150, 200, 200, 30);
		credNameField.setEditable(false);
		usernameLabel.setBounds(150, 225, 200, 30);
		usernameField.setBounds(150, 250, 200, 30);
		usernameField.setEditable(false);
		passwordLabel.setBounds(150, 275, 200, 30);
		passwordField.setBounds(150, 300, 200, 30);
		passwordField.setEditable(false);
		showPassword.setBounds(150, 325, 150, 30);
		backButton.setBounds(200, 375, 100, 30);
	}

	// Method that adds all of the defined components to the container of the add
	// credential screen

	public void addComponentsToContainer() {
		container.add(viewLabel);
		container.add(descLabel);
		container.add(credNameLabel);
		container.add(credNameField);
		container.add(usernameLabel);
		container.add(usernameField);
		container.add(passwordLabel);
		container.add(passwordField);
		container.add(showPassword);
		container.add(backButton);
	}

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		showPassword.addActionListener(this);
		backButton.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the add credential screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}
		}

		// If the back button is pressed, the user will be taken back to the main
		// application screen

		if (e.getSource() == backButton) {
			setVisible(false);
			dispose();
		}
	}
}
