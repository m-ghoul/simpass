package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Account;

public class MainFrame extends JFrame implements ActionListener {

	// Constructor for the main application screen

	MainFrame(Account a) {
		retrieveAccountInfo(a);
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
	}

	// Method that retrieves the user's account information (needed in later
	// methods)

	String accEmail;
	String accName;
	String accPass;

	void retrieveAccountInfo(Account a) {
		this.accEmail = a.getEmail();
		this.accName = a.getName();
		this.accPass = a.getPassword();
	}

	// Initialization of the components of the main application screen (except for
	// the JTable and JScrollPane, which were initialized in the LoginFrame class)

	Container container = getContentPane();
	JLabel greetingLabel = new JLabel("Welcome, ");
	JLabel nameLabel = new JLabel(accName);
	JLabel passwordListLabel = new JLabel("My Vault");
	JTextField searchField = new JTextField();
	JButton searchButton = new JButton("Search");
	JButton clearButton = new JButton("Clear");
	JTable table;
	JScrollPane scrollPane;
	JButton refreshButton = new JButton("Refresh");
	JButton viewButton = new JButton("View");
	JButton addButton = new JButton("Add");
	JButton editButton = new JButton("Edit");
	JButton deleteButton = new JButton("Delete");
	JButton logoutButton = new JButton("Logout");

	// Method to set the layout of the main application screen to its default value

	public void setLayoutManager() {
		container.setLayout(null);
	}

	// Method to define certain properties of components such as icons, location and
	// size

	public void setLocationAndSize() {
		greetingLabel.setHorizontalAlignment(SwingConstants.LEFT);
		greetingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		greetingLabel.setBounds(25, 25, 200, 50);
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		nameLabel.setBounds(125, 25, 200, 50);
		nameLabel.setText(accName);
		passwordListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordListLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		passwordListLabel.setBounds(25, 90, 200, 30);
		searchField.setBounds(115, 90, 200, 30);
		searchButton.setBounds(320, 90, 100, 30);
		clearButton.setBounds(425, 90, 100, 30);
		refreshButton.setBounds(550, 125, 100, 30);
		viewButton.setBounds(550, 175, 100, 30);
		addButton.setBounds(550, 225, 100, 30);
		editButton.setBounds(550, 275, 100, 30);
		deleteButton.setBounds(550, 325, 100, 30);
		logoutButton.setBounds(550, 425, 100, 30);
	}

	// Method that adds all of the defined components to the container of the main
	// application screen

	public void addComponentsToContainer() {
		container.add(greetingLabel);
		container.add(nameLabel);
		container.add(passwordListLabel);
		container.add(searchField);
		container.add(searchButton);
		container.add(clearButton);
		container.add(refreshButton);
		container.add(viewButton);
		container.add(addButton);
		container.add(editButton);
		container.add(deleteButton);
		container.add(logoutButton);
	}

	// Method to initialize a reader to use to read from the credentials file

	BufferedReader getFileReader() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File("src/files/credentials.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return reader;
	}

	// Method to create a table model for the JTable containing the credentials

	TableModel getTableModel() {
		String[] columns = { "Credential Name", "Username" };
		String delimiter = ":";
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			BufferedReader reader = getFileReader();
			model.setColumnIdentifiers(columns);
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.split(delimiter)[0].equals(accEmail)) {
					String[] newArr = { line.split(delimiter)[1], line.split(delimiter)[2] };
					model.addRow(newArr);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return model;
	}

	// Method to create a table from the TableModel and put it in a scroll pane

	JTable createTable() {
		JTable table = new JTable(getTableModel());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return table;
	}

	// Methods to obtain the values of the currently selected credential details in
	// the JTable (used in the action listeners)

	public String getSelectedCredName() {
		if (table.getSelectedRow() != -1) {
			final int row = table.getSelectedRow();
			final int column = 0;
			final String valueInCell = (String) table.getValueAt(row, column);
			return valueInCell;
		} else {
			return null;
		}
	}

	public String getSelectedUsername() {
		if (table.getSelectedRow() != -1) {
			final int row = table.getSelectedRow();
			final int column = 1;
			final String valueInCell = (String) table.getValueAt(row, column);
			return valueInCell;
		} else {
			return null;
		}
	}

	public String getSelectedPassword() {
		if (table.getSelectedRow() != -1) {
			String selectedPass = null;
			try {
				BufferedReader reader = getFileReader();
				String delimiter = ":";
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.split(delimiter)[1].equals(getSelectedCredName())
							&& line.split(delimiter)[2].equals(getSelectedUsername())) {
						selectedPass = line.split(delimiter)[3];
						return selectedPass;
					}
				}
				reader.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			return selectedPass;
		} else {
			return null;
		}
	}

	// Method to find the line in the credentials text file that is to be replaced

	public String getSelectedCredentialLine() {
		if (table.getSelectedRow() != -1) {
			try {
				BufferedReader reader = getFileReader();
				String delimiter = ":";
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.split(delimiter)[1].equals(getSelectedCredName())
							&& line.split(delimiter)[2].equals(getSelectedUsername())) {
						return line;
					}
				}
				reader.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}

	// Method to delete a line from the credentials file

	public void deleteCredLine(String line) {
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
		String newLine = "deleted:deleted:deleted:deleted";
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

	// Method that searches the credentials text file for a particular string or
	// substring and creates a new TableModel based on the results (a variation of
	// the getTableModel() method

	TableModel getSearchResults(String searchText) {
		String[] columns = { "Credential Name", "Username" };
		String delimiter = ":";
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try {
			BufferedReader reader = getFileReader();
			model.setColumnIdentifiers(columns);
			String line;
			String text = searchText;
			String upperText = text.toUpperCase();
			String lowerText = text.toLowerCase();
			while ((line = reader.readLine()) != null) {
				if (line.split(delimiter)[0].equals(accEmail)
						&& (line.toLowerCase().split(delimiter)[1].contains(lowerText)
								|| line.toLowerCase().split(delimiter)[2].contains(lowerText))) {
					String[] newArr = { line.split(delimiter)[1], line.split(delimiter)[2] };
					model.addRow(newArr);
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return model;
	}

	// Method that adds action listeners to the relevant components (they detect
	// interactions with the defined components)

	public void addActionEvent() {
		searchButton.addActionListener(this);
		clearButton.addActionListener(this);
		refreshButton.addActionListener(this);
		viewButton.addActionListener(this);
		addButton.addActionListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
		logoutButton.addActionListener(this);
	}

	// Method that defines what happens when the user interacts with some of the
	// components on the main application screen such as buttons and check boxes.

	@Override
	public void actionPerformed(ActionEvent e) {

		// When the search button is clicked, the program will search for a credential
		// that contains or exactly matches the string entered in the search box and
		// filter the table to display the results

		if (e.getSource() == searchButton) {
			if (searchField.getText().isBlank()) {
				System.out.println("null search bar contents");
			} else {
				TableModel newModel = getSearchResults(searchField.getText());
				table.setModel(newModel);
				revalidate();
				repaint();
			}
		}

		// When the clear button is clicked, the contents of the search box will be
		// cleared and the table will be refreshed to remove the results of the previous
		// search

		if (e.getSource() == clearButton) {
			searchField.setText("");
			refreshButton.doClick();
		}

		// When the user clicks on the refresh button, the JTable will be redrawn and
		// any modifications made will be displayed in the refreshed JTable

		if (e.getSource() == refreshButton) {
			TableModel newModel = getTableModel();
			table.setModel(newModel);
			revalidate();
			repaint();
		}

		// When the user clicks on the add button, they will be redirected to the add
		// credential screen

		if (e.getSource() == addButton) {
			AddCredentialFrame add = new AddCredentialFrame(new Account(accEmail, accEmail, accEmail));
			add.setTitle("SimPass");
			add.setBounds(10, 10, 500, 500);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			add.setLocation(dim.width / 2 - add.getSize().width / 2, dim.height / 2 - add.getSize().height / 2);
			add.setVisible(true);
			add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			add.setResizable(false);
		}

		// When the user clicks on the view button, the program will check if a
		// credential is selected and if there is a selection, they will be redirected
		// to the view credential screen for that credential

		if (e.getSource() == viewButton) {
			if (getSelectedCredName() == null && getSelectedUsername() == null && getSelectedPassword() == null) {
				JOptionPane.showMessageDialog(this,
						"You must select a credential row before choosing to view, modify or delete", "Error!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				ViewCredentialFrame view = new ViewCredentialFrame(new Account(accEmail, accEmail, accEmail));
				view.credNameField.setText(getSelectedCredName());
				view.usernameField.setText(getSelectedUsername());
				view.passwordField.setText(getSelectedPassword());
				view.setTitle("SimPass");
				view.setBounds(10, 10, 500, 500);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				view.setLocation(dim.width / 2 - view.getSize().width / 2, dim.height / 2 - view.getSize().height / 2);
				view.setVisible(true);
				view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				view.setResizable(false);
			}
		}

		// When the user clicks on the edit button, the program will check if a
		// credential is selected and if there is a selection, they will be redirected
		// to the edit credentials screen

		if (e.getSource() == editButton) {
			if (getSelectedCredName() == null && getSelectedUsername() == null) {
				JOptionPane.showMessageDialog(this,
						"You must select a credential row before choosing to view, modify or delete", "Error!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				Account acc = new Account(accName, accEmail, accPass);
				EditCredentialFrame edit = new EditCredentialFrame(acc);
				edit.credNameField.setText(getSelectedCredName());
				edit.usernameField.setText(getSelectedUsername());
				edit.setTitle("SimPass");
				edit.setBounds(10, 10, 500, 500);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				edit.setLocation(dim.width / 2 - edit.getSize().width / 2, dim.height / 2 - edit.getSize().height / 2);
				edit.setVisible(true);
				edit.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				edit.setResizable(false);
			}
		}

		// When the user clicks on the delete button, the program will check if a
		// credential is selected and if there is a selection, the program will prompt
		// the user to confirm the deletion of the selected credential

		if (e.getSource() == deleteButton) {
			if (getSelectedCredName() == null && getSelectedUsername() == null && getSelectedPassword() == null) {
				JOptionPane.showMessageDialog(this,
						"You must select a credential row before choosing to view, edit or delete", "Error!",
						JOptionPane.ERROR_MESSAGE);
			} else {

				int option = JOptionPane.OK_CANCEL_OPTION;
				int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this credential?",
						"Delete Credential", option);
				if (result == JOptionPane.OK_OPTION) {
					deleteCredLine(getSelectedCredentialLine());
					JOptionPane.showMessageDialog(this, "Your credential was deleted successfully", "Delete Credential",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		// When the user clicks on the logout button, their main application screen will
		// be closed and they will be redirected back to the login screen

		if (e.getSource() == logoutButton) {
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