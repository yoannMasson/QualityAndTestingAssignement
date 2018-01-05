package simulation;

import java.util.*;

public class Result {

	private int[] numberJobProcessByWeek;
	private float timeConsumedByMachine;
	private List<Queue> queue;

	/**
	 * Create an object result associated with the queue given in parameter
	 * @param q the q to get the result from
	 */
	public Result(SimulationSettings settings) {

		this.numberJobProcessByWeek = new int[settings.getSimulationTime()+1];
		for(int i = 0; i < numberJobProcessByWeek.length;i++) {
			numberJobProcessByWeek[i] = 0;
		}
		this.queue = new LinkedList<>();
		this.timeConsumedByMachine = 0f;

	}

	/**
	 * Add a queue to the result object
	 * @param the queue we want the result from
	 */
	public void addQueue(Queue q) {
		this.queue.add(q);
	}

	/**
	 * Return an array with the number of each job processed by week.
	 * @return the number of job processed by week in an array
	 */
	public int[] getNumberProcessedJobPerWeek() {
		Date d1;
		Calendar cl  = Calendar. getInstance();
		for(Queue q: this.queue) {
			for(Job j : q.jobsQueue) {
				if(j.getJobStatus() == JobStatus.done) {
					cl.setTime(j.getFinishedDate());
					this.numberJobProcessByWeek[cl.get(Calendar.WEEK_OF_YEAR)-1]++;
				}	
			}	
		}
		return this.numberJobProcessByWeek;
	}


	/**
	 * Return the total price paid by users
	 * @return the sum paid by users
	 */
	public float getResultingPricePaidByUser() {
		float sum = 0;
		for(Queue q : queue) {
			for(Job j : q.jobsQueue) {
				if(j.getJobStatus() == JobStatus.done) {
					sum += j.getCost();
				}
			}
		}
		return sum;
	}

	/**
	 * Return the average wait time for jobs already processed in Hour
	 * @return the average time in Hour
	 */
	public float getAverageWaitTime() {

		long sum = 0;
		int nbJob = 0;

		for(Queue q: this.queue) {
			for(Job j : q.jobsQueue) {
				if(j.getJobStatus() == JobStatus.done) {
					nbJob++;
					sum += j.getWaitingTime();
				}	
			}	
		}
		return (sum/nbJob)/3600f;
	}

	/**
	 * Return the average turn around ratio. WaitingTime/jobDuration. In hours
	 * @return the average time in hours
	 */
	public float getAverageTurnAroundRatio() {
		long sum = 0;
		int nbJob = 0;

		for(Queue q: this.queue) {
			for(Job j : q.jobsQueue) {
				if(j.getJobStatus() == JobStatus.done) {
					nbJob++;
					sum += j.getWaitingTime()/j.getDuration();
				}	
			}	
		}
		return (sum/nbJob)/3600f;
	}

	/**
	 * Return the cost of running the queue and the CPU associated for the given time in the settings
	 */
	public float getCenterCost() {
		float sum = 0;
		for ( Queue q : queue) {
			sum += q.getCenterCost();
		}
		return sum;
	}

	/**
	 * Redefine the method toString to print the value from the queue attached to the result object
	 */
	@Override
	public String toString() {
		String result = "";

		//Print the number of job processed by weeks
		int  [] nbJob = getNumberProcessedJobPerWeek();
		for( int i = 0 ; i < nbJob.length;i++) {
			result += " week "+(i+1)+": "+nbJob[i]+" jobs processed \n";
		}
		result += "The average wait time is: "+getAverageWaitTime()+"hours\n";
		result += "The users paid £"+getResultingPricePaidByUser()+"\n";
		result += "The cost of the center is £" +getCenterCost()+"\n";
		result += "The average turn around ratio is " + getAverageTurnAroundRatio();
		
		return result;
	}


}
