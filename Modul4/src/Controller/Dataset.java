package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Dataset {
	private static ArrayList<String> record = new ArrayList<>();
	private static String path = "D:\\Kuliah\\Semester 5\\PBO\\Modul-4\\src\\assets\\dataset.csv";
	
	//	private ArrayList<String> data = new ArrayList<>();
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
	
	public static String[] getRecord() {
		getDataset();
		return record.toArray(new String[0]);
	}
}