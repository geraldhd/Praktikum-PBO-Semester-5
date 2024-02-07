package Login;

import Controller.Handler;
import HomePage.TypingGame;
import Regis.Regis;
import javax.swing.*;

public class Login extends JFrame {
	private JTextField userTxt;
	private JPasswordField passTxt;
	private JButton login;
	private JLabel username;
	private JLabel password;
	private JLabel regis;
	private JPanel mainPanel;
	public Login() {
		setTitle("Login");
		setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		login.addActionListener(e -> {
			authUser();
		});
		regis.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				new Regis();
				dispose();
			}
		});
		setVisible(true);
	}
	
	private void authUser() {
		String username = userTxt.getText();
		String password = String.valueOf(passTxt.getPassword());
		if (Handler.isValidUser(username, password)) {
			JOptionPane.showMessageDialog(Login.this, "Login Berhasil", "Success", JOptionPane.INFORMATION_MESSAGE);
			new TypingGame();
			dispose();
		} else {
			userTxt.setText("");
			passTxt.setText("");
			JOptionPane.showMessageDialog(Login.this, "Login Gagal", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
}
