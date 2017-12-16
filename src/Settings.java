import java.io.*;
import java.util.*;

import javax.print.attribute.HashAttributeSet;


public class Settings {

	private String filePath;
	private Map<String,Float> settings;

	public Settings(String path) throws IOException{

		this.filePath = path;
		this.settings = new HashMap<>();


		// Get the object of DataInputStream
		FileInputStream fstream = new FileInputStream(this.getFilePath());
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;


		String[] array;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			
			array = strLine.split(";");
			System.out.println(array.length);
			if(array.length == 3 ||array.length == 2) {//The line is correct
				settings.put(array[0],Float.parseFloat(array[1]));
			}
		}
		br.close();
	}

	/**
	 * Use default settings
	 */
	public Settings() {
		this.filePath = "";
		this.defaultSettings();
	}

	/**
	 * Return the path of the file
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return the settings
	 */
	public Map<String,Float> getSettings() {
		return settings;
	}	
	
	/**
	 * Generate the default settings
	 * @return the settings
	 */
	public Map<String,Float> defaultSettings(){
		this.settings = new HashMap<>();
		this.settings.put("STUDENT_NUMBER", 60f);
		return this.getSettings();
	}

}
