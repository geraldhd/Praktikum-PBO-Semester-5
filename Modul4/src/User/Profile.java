package User;

import javax.swing.*;

public class Profile extends JFrame {
	private JLabel profile;
	private JPanel mainPanel;
	private JLabel nameLabel;
	private JLabel scoreLabel;
	private JLabel totalLabel;
	
	public Profile() {
		setTitle("Profile");
		setSize(200, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		nameLabel.setText("Name : " + username);
		scoreLabel.setText("Highscore : " + score);
		totalLabel.setText("Total Test : " + totalGame);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void setDataProfile(String username, String totalGame, String score){
		Profile.username = username;
		Profile.totalGame = totalGame;
		Profile.score = score;
	}
	// variable needed
	private static String username;
	private static String totalGame;
	private static String score;
}
