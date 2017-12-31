package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

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
		assertTrue(set.get("valueThatDoesNotExist") == -1);
	}
	
	@Test
	void atLeast128Cores() {
		assertTrue(set.getTotalNumberNodes() >= 128);
	}
	
	@Test
	void testGet() {
		System.out.println(set.getSettings());
		assertTrue(set.get("STUDENT_NUMBER") == 60f);
		assertTrue(set.get("RESEARCHER_NUMBER") == 20f);
		assertTrue(set.get("COMPUTER_BUDGET") == 20f);
		assertTrue(set.get("WATER_BUDGET") == 10f);
		assertTrue(set.get("SOIL_BUDGET") == 5f);
		assertTrue(set.get("NODES_PROCESSOR_NUMBER") == 80f);
		assertTrue(set.get("ACCELERATED_PROCESSOR_NUMBER") == 40f);
		assertTrue(set.get("SPECIALIZED_NUMBER") == 20f);
		assertTrue(set.get("SMALL_PRICE") == 0.05f);
		assertTrue(set.get("MEDIUM_PRICE") == 0.05f);
		assertTrue(set.get("LARGE_PRICE") == 0.05f);
		assertTrue(set.get("HUGE_PRICE") == 0.05f);
		assertTrue(set.get("COMPUTER_COST") == 0.01f);
		assertTrue(set.get("ENTIRE_TIME") == 8f);
		assertTrue(set.get("DELTA_SIZE") == 1f);
		assertTrue(set.get("DELTA_JOB_TIME") == 1f);
		assertTrue(set.get("DELTA_REQUEST_TIME") == 1f);
		assertTrue(set.get("RESEARCHER_BUDGET") == 50f);
	}

}
