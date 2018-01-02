package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import simulation.*;


class jobTesting {

	SimulationSettings settings = new SimulationSettings();
	
	//Queue initialization

	
	SmallQueue smallQ = new SmallQueue(10,10,10,settings);
	MediumQueue mediumQ = new MediumQueue(10,10,10,settings);
	LargeQueue largeQ = new LargeQueue(10,10,10,settings);
	HugeQueue hugeQ = new HugeQueue(10,10,10,settings);

	@Test
	void testJobConstructor() {
		
		int nbCores = 16;
		long duration = 1500;
		Date d = new Date("01/01/2000");
		Job j1 = new Job(nbCores,d,duration,smallQ);
		assertEquals(j1.getJobStatus(),JobStatus.inQueue);
		assertEquals(j1.getRequestDate(),d);
		assertEquals(j1.getNodes(), nbCores);
		assertEquals(j1.getDuration(),duration);
	}
	
	@Test
	void testStartJob() {
		int nbCores = 16;
		long duration = 1500;
		Date requestDate = new Date("01/01/2000");
		Date startDate = new Date("01/01/2010");
		long waitingTime = (startDate.getTime()-requestDate.getTime())/1000;
		Job j1 = new Job(nbCores,requestDate,duration,smallQ);
		
		try {
			j1.startJob(startDate);
		} catch (notEnoughBudgetException e) {
			System.out.println("not enough budget");
		}
		assertEquals(j1.getRequestDate(),requestDate);
		assertEquals(j1.getJobStatus(),JobStatus.processing);
		assertEquals(j1.getNodes(), nbCores);
		assertEquals(j1.getFinishedDate(),new Date(startDate.getTime()+j1.getDuration()*1000));
		assertEquals(j1.getWaitingTime(), waitingTime);
		
	}

}
