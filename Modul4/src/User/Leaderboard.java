package User;

import Controller.Handler;
import javax.swing.*;

public class Leaderboard extends JFrame {
	private JList leaderboardList;
	private JPanel mainPanel;
	private JScrollPane scrollPane;
	public Leaderboard(){
		setTitle("Leaderboard");
		setSize(200, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		leaderboardList.setModel(new AbstractListModel<String>(){
			public static String[] user = Handler.data.toArray(new String[0]);
			public int getSize() { return user.length; }
			public String getElementAt(int i) { return user[i]; }
		});
		setVisible(true);
	}
}
