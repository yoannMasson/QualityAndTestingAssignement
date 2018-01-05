package test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import simulation.MediumQueue;
import simulation.SimulationSettings;

class queueTesting {

	@Test
	void testWeekEnd() {
		
		SimulationSettings s =new SimulationSettings();
		MediumQueue q = new MediumQueue(10, 10, 10, s);
		
		Date notWeekEnd = new Date("01/01/2018 09:00:00");//Monday
		Date friday8pm = new Date("01/05/2018 20:00:02");//Friday
		Date friday8am = new Date("01/05/2018 08:00:00");//Monday
		Date WeekEnd = new Date ("12/31/2017");//Sunday
		
		assertTrue(q.isWeekEnd(WeekEnd));
		assertTrue(q.isWeekEnd(friday8pm));
		
		assertFalse(q.isWeekEnd(notWeekEnd));
		assertFalse(q.isWeekEnd(friday8am));
	}

}
