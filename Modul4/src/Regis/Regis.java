package Regis;

import Controller.Handler;
import Login.Login;

import javax.swing.*;

public class Regis extends JFrame {
	private JTextField userTxt;
	private JPasswordField passTxt;
	private JButton regis;
	private JLabel username;
	private JLabel password;
	private JLabel login;
	private JPanel mainPanel;
	private JPasswordField conPassTxt;
	private JLabel conPassword;
	private JButton cancelBtn;
	
	public Regis() {
		setTitle("Register");
		setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
		login.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				new Login();
				dispose();
			}
		});
		regis.addActionListener(e -> {
			String username = userTxt.getText();
			String password = String.valueOf(passTxt.getPassword());
			String confirmPass = String.valueOf(conPassTxt.getPassword());
			if (username.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
				JOptionPane.showMessageDialog(Regis.this, "Masukkan Data dengan Benar", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!password.equals(confirmPass)) {
				JOptionPane.showMessageDialog(Regis.this, "Password tidak sama", "Warning", JOptionPane.WARNING_MESSAGE);
				userTxt.setText("");
				passTxt.setText("");
				conPassTxt.setText("");
				return;
			}
			registerUser();
		});
		
		cancelBtn.addActionListener(e -> {
			userTxt.setText("");
			passTxt.setText("");
			conPassTxt.setText("");
		});
	}
	
	private void registerUser() {
		String username = userTxt.getText();
		String password = String.valueOf(passTxt.getPassword());
		if (Handler.isValidRegis(username)) {
			Handler.insertUser(username, password);
			JOptionPane.showMessageDialog(Regis.this, "Register Berhasil", "Success", JOptionPane.INFORMATION_MESSAGE);
			new Login();
			dispose();
		}else{
			JOptionPane.showMessageDialog(Regis.this, "Username Sudah Terdaftar", "Warning", JOptionPane.WARNING_MESSAGE);
			userTxt.setText("");
			passTxt.setText("");
			conPassTxt.setText("");
		}
	}
}
