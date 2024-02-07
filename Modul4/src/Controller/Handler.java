package Controller;

import HomePage.TypingGame;
import User.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Handler {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db_typing_test";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static int idUser;
	private static int scorePast;
	private static int attemptPast;
	public static ArrayList<String> data = new ArrayList<>();
	
	private static Connection connect() throws Exception {
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}
	
	public static boolean isValidUser(String username, String password) {
		boolean found = false;
		try (Connection conn = connect()) {
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, username);
				statement.setString(2, password);
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					setIdUser(resultSet.getInt("id"));
					attemptPast = resultSet.getInt("totalGame");
					found = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}
	
	public static void getScore() {
		try (Connection conn = connect()) {
			String query = "SELECT * FROM users WHERE id = ?";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, getIdUser());
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					scorePast = resultSet.getInt("score");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addProfile(int id) {
		try (Connection conn = connect()) {
			String query = "SELECT * FROM users WHERE id = ?";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					Profile.setDataProfile(resultSet.getString("username"), resultSet.getString("totalGame"), resultSet.getString("score"));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setIdUser(int id) {
		idUser = id;
	}
	
	public static int getIdUser() {
		return idUser;
	}
	
	public static boolean isValidRegis(String username) {
		try (Connection conn = connect()) {
			String query = "SELECT * FROM users WHERE username = ?";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, username);
				try (ResultSet resultSet = statement.executeQuery()) {
					return !resultSet.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void insertUser(String username, String password) {
		try (Connection conn = connect()) {
			String query = "INSERT INTO users (username, password) VALUES (?, ?)";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, username);
				statement.setString(2, password);
				statement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateData() {
		try (Connection conn = connect()) {
			if (TypingGame.wpmCurrent > scorePast) {
				String query = "UPDATE users SET score = ?, totalGame = ? WHERE id = ?";
				try (PreparedStatement statement = conn.prepareStatement(query)) {
					statement.setInt(1, TypingGame.wpmCurrent);
					statement.setInt(2, attemptPast + TypingGame.count);
					statement.setInt(3, getIdUser());
					statement.executeUpdate();
				}
			}else{
				String query = "UPDATE users SET totalGame = ? WHERE id = ?";
				try (PreparedStatement statement = conn.prepareStatement(query)) {
					statement.setInt(1, attemptPast + TypingGame.count);
					statement.setInt(2, getIdUser());
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showLeaderboard(){
		try (Connection conn = connect()) {
			String query = "SELECT * FROM users ORDER BY score DESC";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				ResultSet resultSet = statement.executeQuery();
				int number = 1;
				while (resultSet.next()) {
					data.add((number++) + ". " + resultSet.getString("username") + " " + resultSet.getInt("score") + " WPM\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
