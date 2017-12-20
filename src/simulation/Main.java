package simulation;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		//Getting settings from the file
		SimulationSettings settings = new SimulationSettings();
		try {
			settings = new SimulationSettings("./util/settings");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n Something went wrong, so we will be using default settings for the simulation");
			settings.defaultSettings();
		}
		System.out.println(settings.getSettings());

	}

}
