package SeparateData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CarData {
	private Object[] years;
	private String make;
	private String model;
	//private String[] parsedYears;
	private ArrayList<CarData> carDataAll = new ArrayList<CarData>();
	
	public CarData() { /* Constructor Method */ }
	
	public void setYears(String readYears) { years = parseYears(readYears); }
	public void setMake(String readMake) { make = readMake; }
	public void setModel(String readModel) { model = readModel; }
	
	public Object[] getYears() { return years; }
	public String getMake() { return make; }
	public String getModel() { return model; }
	
	
	private Object[] parseYears(String readYears) {
		int srt = 0, end = 0;
		ArrayList<String> tempYears = new ArrayList<String>();
		
		while (srt < readYears.length()) {
			end = srt + 2;
			tempYears.add(readYears.substring(srt, end));
			srt = srt + 2;
		}
		Object[] returnArray = tempYears.toArray();
		return returnArray;
	}
	public void writeFile() throws IOException {
		FileOutputStream fos = new FileOutputStream("CarDatabase.csv");
		PrintWriter printy = new PrintWriter(fos);
		String correctedYear = "";
		for (int mk = 0; mk < carDataAll.size(); mk++) {
			for (int yr = 0; yr < carDataAll.get(mk).getYears().length; yr++) {
				
				if (Integer.parseInt(carDataAll.get(mk).getYears()[yr].toString()) < 89) {
					correctedYear = "20" + carDataAll.get(mk).getYears()[yr].toString();	
				} else {
					correctedYear =  "19" + carDataAll.get(mk).getYears()[yr].toString();	
				}
				
				printy.printf("%s,%s,%s%n", correctedYear, carDataAll.get(mk).getMake(), carDataAll.get(mk).getModel());
			}
		}
		System.out.println("All done writing!");
		fos.close();
	}	
	
	public void readFile(String fileToRead) throws IOException {
		FileInputStream fis = new FileInputStream(fileToRead);
		Scanner ready = new Scanner(fis);
		
		String[] tempArr = null;
		
		while (ready.hasNextLine()) {
			CarData tempCarData = new CarData();
			tempArr = ready.nextLine().split("	");
			tempCarData.setYears(tempArr[2]);
			tempCarData.setMake(tempArr[1]);
			tempCarData.setModel(tempArr[0]);
			carDataAll.add(tempCarData);
		}
		System.out.println("All done reading!");
		fis.close();
		writeFile();
		
	}
	
	public static void main(String[] args) throws IOException {
		CarData carData = new CarData();
		carData.readFile("YMMdata.txt");
	}
}
