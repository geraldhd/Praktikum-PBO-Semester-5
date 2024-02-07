package HomePage;

import Controller.Handler;
import User.Profile;
import User.Leaderboard;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TypingGame extends JFrame implements KeyListener {
	private JTextPane textGame;
	private JPanel mainPanel;
	private JTextField inputTxt;
	private JButton restartBtn;
	private JLabel leaderboard;
	private JLabel profile;
	private JLabel timer;
	private JLabel score;
	
	public TypingGame() {
		setTitle("Typing Test");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		textGame.setContentType("text/html");
		leaderboard.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Handler.showLeaderboard();
				new Leaderboard();
			}
		});
		profile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Handler.addProfile(Handler.getIdUser());
				new Profile();
			}
		});
		inputTxt.addKeyListener(this);
		
		restartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
				Handler.getScore();
			}
		});
		setVisible(true);
		getDataset();
	}
	
	public void startGame() {
		stopCountdownThread(); // Hentikan thread countdown jika sedang berjalan
		countdown = 60; // Waktu permainan dalam detik
		timer.setText(countdown + " second");
		
		correctWordCount = 0;
		currentWordIndex = 0;
		groupIndex = 0;
		
		wordsToType = new ArrayList<>();
		for (String word : getWordsType()) {
			for (int i = 0; i < WORDS_PER_GROUP; i++) {
				String randomWord = getRandomWord();
				if (randomWord != null) {
					wordsToType.add(randomWord);
				}
			}
			wordsToType.add(word);
		}
		updateTextPane();
		
		inputTxt.setText("");
		inputTxt.requestFocus();
		startTime = System.currentTimeMillis();
		
		countdownThread = new Thread(() -> {
			while (countdown > 0 && isRunning) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				countdown--;
				SwingUtilities.invokeLater(() -> timer.setText(countdown + " second"));
			}
			if (isRunning) {
				endGame();
			}
		});
		countdownThread.start();
	}
	
	private void stopCountdownThread() {
		isRunning = false; // Set flag isRunning menjadi false
		if (countdownThread != null && countdownThread.isAlive()) {
			try {
				countdownThread.join(); // Tunggu hingga thread countdown selesai
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		countdownThread = null; // Reset thread countdown
		isRunning = true;
	}
	private String getRandomWord() {
		if (!record.isEmpty()) {
			int randomIndex = rand.nextInt(record.size());
			return record.get(randomIndex);
		}
		return null;
	}
	private String[] getWordsType() {
		return record.toArray(new String[0]);
	}
	private String[] getWordsToTypeGroup() {
		int fromIndex = groupIndex * WORDS_PER_GROUP;
		int toIndex = Math.min(fromIndex + WORDS_PER_GROUP, wordsToType.size());
		return wordsToType.subList(fromIndex, toIndex).toArray(new String[0]);
	}
	
	private void updateTextPane() {
		String[] groupWords = getWordsToTypeGroup();
		StringBuilder html = new StringBuilder("<html>");
		
		for (int i = 0; i < groupWords.length; i++) {
			if (i < currentWordIndex % WORDS_PER_GROUP) {
				html.append("<font color='green'>").append(groupWords[i]).append("</font>").append(" ");
			} else if (i == currentWordIndex % WORDS_PER_GROUP) {
				html.append("<font color='red'>").append(groupWords[i]).append("</font>").append(" ");
			} else {
				html.append("<font color='black'>").append(groupWords[i]).append("</font>").append(" ");
			}
		}
		html.append("</html>");
		textGame.setText(html.toString());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (currentWordIndex < wordsToType.size()) {
				String typedText = inputTxt.getText().trim();
				String currentWord = getWordsToTypeGroup()[currentWordIndex % WORDS_PER_GROUP];
				
				if (typedText.equals(currentWord)) {
					currentWordIndex++;
					updateTextPane();
					inputTxt.setText("");
					
					if (currentWordIndex % WORDS_PER_GROUP == 0) {
						groupIndex++;
						updateTextPane();
					}
					
					correctWordCount++;
					inputTxt.setText("");
				}
			}
		}
	}
	
	private void endGame() {
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		double seconds = elapsedTime / 1000.0;
		
		// int numberOfWords = getWordsToType().length;
		double minutes = seconds / 60.0;
		int wpm = (int) ((double) correctWordCount / minutes);
		wpmCurrent = wpm;
		// JOptionPane.showMessageDialog(this, "Selesai!\nWaktu yang dibutuhkan: " + seconds + " detik\nWPM: " + wpm);
		score.setText("Score : " + wpm + " WPM");
		textGame.setText("");
		count++;
		Handler.updateData();
		restartBtn.setEnabled(true);
	}
	
	
	public static void getDataset() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				for (String value : values) {
					record.add(value.trim());
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// variable needed
	private static final int WORDS_PER_GROUP = 10;
	private List<String> wordsToType;
	private int currentWordIndex;
	public static int wpmCurrent;
	private int groupIndex;
	public static int count;
	private volatile boolean isRunning = true;
	private Thread countdownThread;
	private int countdown;
	private long startTime;
	private int correctWordCount;
	private Random rand = new Random();
	private static ArrayList<String> record = new ArrayList<>();
	private static String path = "D:\\Kuliah\\Semester 5\\PBO\\Modul-4\\src\\assets\\dataset.csv";
}
