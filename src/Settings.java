import java.io.*;
import java.util.*;

import javax.print.attribute.HashAttributeSet;


public class Settings {

	private String filePath;
	private Map<String,Float> settings;

	public Settings(String path) throws Exception{

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
			if(array.length == 3 ||array.length == 2) {//The line is correct
				settings.put(array[0],Float.parseFloat(array[1]));
			}
		}
		br.close();
		//Checking that we have at least 128 cores
		if(settings.get("NODES_16_PROCESSOR_NUMBER")+
				settings.get("NODES_32_PROCESSOR_NUMBER")+
				settings.get("NODES_64_PROCESSOR_NUMBER")+
				settings.get("ACCELERATED_16_PROCESSOR_NUMBER")+
				settings.get("ACCELERATED_32_PROCESSOR_NUMBER")+
				settings.get("ACCELERATED_64_PROCESSOR_NUMBER")+
				settings.get("NODES_16_PROCESSOR_NUMBER")+
				settings.get("SPECIALIZED_NUMBER") < 128) {
			throw new Exception("There must be at least 128 cores in total");
		}
		
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
		this.settings.put("RESEARCHER_NUMBER", 20f);
		this.settings.put("COMPUTER_BUDGET", 20f);
		this.settings.put("WATER_BUDGET", 10f);
		this.settings.put("SOIL_BUDGET", 5f);
		this.settings.put("NODES_16_PROCESSOR_NUMBER", 80f);
		this.settings.put("NODES_32_PROCESSOR_NUMBER", 40f);
		this.settings.put("NODES_64_PROCESSOR_NUMBER", 30f);
		this.settings.put("ACCELERATED_16_PROCESSOR_NUMBER", 30f);
		this.settings.put("ACCELERATED_32_PROCESSOR_NUMBER", 20f);
		this.settings.put("ACCELERATED_64_PROCESSOR_NUMBER", 10f);
		this.settings.put("SPECIALIZED_NUMBER", 20f);
		this.settings.put("SMALL_PRICE", 0.05f);
		this.settings.put("MEDIUM_PRICE", 0.05f);
		this.settings.put("LARGE_PRICE", 0.05f);
		this.settings.put("HUGE_PRICE", 0.05f);
		this.settings.put("COMPUTER_COST", 0.01f);

		/**STUDENT_NUMBER;40;
RESEARCHER NUMBER;20;
COMPUTER_BUDGET;20;
WATER_BUDGET;10;
SOIL_BUDGET;5;
NODES_16_PROCESSOR_NUMBER;80;
NODES_32_PROCESSOR_NUMBER;40;
NODES_64_PROCESSOR_NUMBER;30;
ACCELERATED_16_PROCESSOR_NUMBER;30;
ACCELERATED_32_PROCESSOR_NUMBER;20;
ACCELERATED_64_PROCESSOR_NUMBER;10;
SPECIALIZED_NUMBER;20;
SMALL_PRICE;0.05;
MEDIUM_PRICE;0.05;
LARGE_PRICE;0.05;
HUGE_PRICE;0.05;
COMPUTER_COST;0.01;*/

		return this.getSettings();
	}

}
