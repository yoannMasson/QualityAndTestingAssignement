import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Settings settings = new Settings();
		//Getting settings from the file
		try {

			settings = new Settings("./util/settings");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n Something went wrong, so we will be using default settings for the simulation");
			settings.defaultSettings();
		}
		System.out.println(settings.getSettings());

	}

}
