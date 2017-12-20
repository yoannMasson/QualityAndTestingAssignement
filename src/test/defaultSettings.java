package test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import simulation.SimulationSettings;


/**
 * This class is aiming to test that default value are the one expected
 * @author Yoann Masson
 *
 */
class defaultSettings {

	SimulationSettings set = new SimulationSettings();
	
	@Test
	void nullParameter() {
		assertNull(set.get("valueThatDoesNotExist"));
	}
	
	@Test
	void nodesParameter() {
		assertNull(set.get("valueThatDoesNotExist"));
	}

}
