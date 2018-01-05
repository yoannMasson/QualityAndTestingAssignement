package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import simulation.*;


class jobTesting {

	SimulationSettings settings = new SimulationSettings();
	
	//Queue initialization

	
	SmallQueue smallQ = new SmallQueue(50,50,50,settings);
	MediumQueue mediumQ = new MediumQueue(50,50,50,settings);
	LargeQueue largeQ = new LargeQueue(50,50,50,settings);
	HugeQueue hugeQ = new HugeQueue(50,50,50,settings);

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
	
	/**
	 * The cost of a job is determined by the time (every 1 hour) and the number of cores ( every 32 cores)
	 */
	@Test
	void testCost() {
		int nbCores = 16;
		long duration = 2000;
		Date requestDate = new Date("01/01/2000");
		Job j1 = new Job(nbCores,requestDate,duration,mediumQ);//16 cores less than 1h
		Job j2 = new Job(nbCores*2,requestDate,duration,mediumQ);//32 cores less than 1h
		Job j3 = new Job(nbCores*2+1,requestDate,duration,mediumQ);//33 cores less than 1h
		Job j4 = new Job(nbCores,requestDate,duration*2,mediumQ);//16 cores more than 1h
		Job j5 = new Job(nbCores*2+1,requestDate,duration*2,mediumQ);//33 cores more than 1h
		assertEquals(j1.getCost(),0.05f);
		assertEquals(j2.getCost(),0.05f);
		assertEquals(j3.getCost(),0.1f);
		assertEquals(j4.getCost(),0.1f);
		assertEquals(j5.getCost(),0.2f);
	}

}
