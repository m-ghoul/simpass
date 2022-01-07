package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
	public static void main(String[] a) {

		// Intializing the login screen that is displayed every time the program runs

		LoginFrame login = new LoginFrame();
		login.setTitle("SimPass");
		login.setBounds(10, 10, 500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		login.setLocation(dim.width / 2 - login.getSize().width / 2, dim.height / 2 - login.getSize().height / 2);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setResizable(false);
		login.getIconImage();

	}
}