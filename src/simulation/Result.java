package simulation;

import java.util.*;

public class Result {

	private int[] numberJobProcessByWeek;
	private float timeConsumedByMachine;
	private Queue queue;
	
	/**
	 * Create an object result associated with the queue given in parameter
	 * @param q the q to get the result from
	 */
	public Result(Queue q,SimulationSettings settings) {
		this.queue =q;
		this.numberJobProcessByWeek = new int[settings.getSimulationTime()];
		this.timeConsumedByMachine = 0f;
	}
	
	/**
	 * Return an array with the number of each job processed by week.
	 * @return the number of job processed by week in an array
	 */
	public int[] getNumberProcessedJobPerWeek() {
		return this.numberJobProcessByWeek;
	}
	
	/**
	 * Return the overall time consumed by users.
	 * @return
	 */
	public float getMachineHoursConsummedByUserJob() {
		return this.timeConsumedByMachine;
	}
	
	/**
	 * Return the total price paid by users
	 * @return the sum paid by users
	 */
	public float getResultingPricePaidByUser() {
		return 0;
	}
	
	/**
	 * Return the average wait time for jobs already processed
	 * @return the average time
	 */
	public float getAverageWaitTime() {
		return 0;
	}
	
	/**
	 * Return the average turn around ratio. WaitingTime/jobDuration
	 * @return the average time
	 */
	public float getAverageTurnAroundRatio() {
		return 0;
	}
	
	/**
	 * Return the cost of running the queue and the CPU associated for the given time in the settings
	 */
	public float getCenterCost() {
		return 0;
	}
	
	
	
	
	
	
}
