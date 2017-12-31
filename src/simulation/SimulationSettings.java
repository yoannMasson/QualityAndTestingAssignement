package simulation;
import java.io.*;
import java.util.*;

import javax.print.attribute.HashAttributeSet;


public class SimulationSettings {

	private String filePath;
	private Map<String,Float> settings;

	public SimulationSettings(String path) throws Exception{

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

			array = strLine.split("=");
			if(array.length == 2) {//The line is correct
				settings.put(array[0],Float.parseFloat(array[1]));
			}
		}
		br.close();
		//Checking that we have at least 128 cores
		if(settings.get("NODES_PROCESSOR_NUMBER")+
				settings.get("ACCELERATED_PROCESSOR_NUMBER")+
				settings.get("SPECIALIZED_NUMBER") < 128) {
			throw new Exception("There must be at least 128 cores in total");
		}

	}

	/**
	 * Use default settings
	 */
	public SimulationSettings() {
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
	 * Return the value of the asked settings
	 * @param param, the key we want the value from
	 * @return the wanted value in the settings, -1 if it does not exist.
	 */
	public Float get(String param) {
		float result = -1;
		if(settings.containsKey(param)) {
			result = settings.get(param);
		}
		return result;
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
		this.settings.put("NODES_PROCESSOR_NUMBER", 80f);
		this.settings.put("ACCELERATED_PROCESSOR_NUMBER", 40f);
		this.settings.put("SPECIALIZED_NUMBER", 20f);
		this.settings.put("SMALL_PRICE", 0.05f);
		this.settings.put("MEDIUM_PRICE", 0.05f);
		this.settings.put("LARGE_PRICE", 0.05f);
		this.settings.put("HUGE_PRICE", 0.05f);
		this.settings.put("COMPUTER_COST", 0.01f);
		this.settings.put("ENTIRE_TIME", 8f);
		this.settings.put("DELTA_SIZE", 1f);
		this.settings.put("DELTA_JOB_TIME", 1f);
		this.settings.put("DELTA_REQUEST_TIME", 1f);
		this.settings.put("RESEARCHER_BUDGET", 50f);


		return this.getSettings();
	}

	public int getTotalNumberNodes() {
		return (int) (settings.get("NODES_PROCESSOR_NUMBER")+
				settings.get("ACCELERATED_PROCESSOR_NUMBER")+
				settings.get("SPECIALIZED_NUMBER"));
	}

	/**
	 * Return the duration of the simulation
	 * @return the simulation time
	 */
	public int getSimulationTime() {

		return this.settings.get("ENTIRE_TIME").intValue();
	}

}
