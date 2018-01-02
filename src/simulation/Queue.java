package simulation;

import java.util.*;

public abstract class Queue {

	private int nbOccupiedCore;
	private int nbCore;
	private SimulationSettings settings;
	protected List<Job> jobsQueue;
	private int index;
	/**
	 * Create a queue with a given size for the job it will be in charge of and the number of associated cores
	 * @param size, the size of the job the queue will deal with
	 * @param nbTraditionalCore, the number of traditional cores of the queue
	 * @param nbAcceleratedCore, the number of accelerated cores of the queue
	 * @param nbSpecializedCore, the number of specialized cores of the queue
	 */
	public Queue( int nbTraditionalCore, int nbAcceleratedCore,int nbSpecializedCore, SimulationSettings settings) {

		this.index = 0;
		this.nbCore = nbAcceleratedCore+nbSpecializedCore+nbTraditionalCore;
		this.nbOccupiedCore = 0;
		this.settings = settings;
		this.jobsQueue = new LinkedList<Job>();

	}

	/**
	 * Return the cost of running a job in the queue for one hour
	 * @return the cost;
	 */
	public abstract float getCost() ;

	/**
	 * Return the settings used in this queue
	 * @return the settings
	 */
	public SimulationSettings getSettings() {
		return settings;
	}


	/**
	 * Get the next job in the queue and moving to the next job
	 * @return the next job the be executed in the queue
	 */
	public Job getNextJob() {
		if(index < this.jobsQueue.size()) {
			this.index++;
		}
		return this.jobsQueue.get(index-1);	
	}


	/**
	 * Finish the next job and release the associated resources and return the finished job
	 * @return the job just finished
	 */
	public Job finishJob() {
		Job finishedJob = randomProcessingJob() ;

		for(Job j : this.jobsQueue) {
			if(j.getJobStatus() == JobStatus.processing) {//We consider processing jobs and release the next one to finish
				if(finishedJob == null ||j.getFinishedDate().before(finishedJob.getFinishedDate())) {
					finishedJob = j;
				}
			}
		}
		this.nbOccupiedCore -= finishedJob.getNodes();
		finishedJob.setStatus(JobStatus.done);
		return finishedJob;
	}

	private Job randomProcessingJob() {
		Job result = null;
		for(Job j : this.jobsQueue) {
			if(j.getJobStatus() == JobStatus.processing) {
				return j;
			}
		}
		return result;
	}

	/**
	 * Add a job to the end of the queue if it fits the queue type
	 * @return true if it has been added, false if the job doesn't fit the requirements
	 */
	public abstract boolean addJob(Job job);

	/**
	 * Return the number of cores available for this queue;
	 * @return the number of cores
	 */
	public int getNbTotalCores() {
		return nbCore;
	}

	/**
	 *  State if all jobs in the queue have started
	 * @return true if all jobs have started, false otherwise
	 */
	public boolean jobStarted() {
		boolean bool = true ;
		int i = 0;
		Job j;
		while(bool && i < jobsQueue.size()) {
			j = this.jobsQueue.get(i);
			if(j.getJobStatus() != JobStatus.done && j.getJobStatus() != JobStatus.processing) {
				bool = false;
			}
			i++;
		}
		return bool;
	}
	
	/**
	 *  State if all jobs in the queue have finished
	 * @return true if all jobs are finished, false otherwise
	 */
	public boolean jobDone() {
		boolean bool = true ;
		int i = 0;
		Job j ;
		while(bool && i < jobsQueue.size()) {
			j = this.jobsQueue.get(i);
			if(j.getJobStatus() != JobStatus.done ) {
				bool = false;
			}
			i++;
		}
		return bool;
	}

	/**
	 * Process the job in the queue resulting in the wanted output, update the status of jobs and the user budget
	 * @return the processed result
	 */
	public Result processJobs(){
		long completionTime;
		Date finishedDate,currentDate;
		Result res = new Result(this,this.settings);
		Job finishJob;
		Job currentJob = getNextJob();
		currentDate = currentJob.getDate();

		while(!jobStarted() ) {

			if(currentJob.getNodes() < nbCore-nbOccupiedCore) {//There is enough resources
				completionTime = currentDate.getTime()+currentJob.getDuration()*1000;
				finishedDate = new Date(completionTime);
				//System.out.println(currentJob);
				if (!(finishedDate.getDay() == 6 ||
						finishedDate.getDay() == 0 ||
						(finishedDate.getDay() == 5 && finishedDate.getHours() >= 5) ||
						(finishedDate.getDay() == 1 && finishedDate.getHours() < 9))) {//This is not the week end
					try {
						currentJob.startJob(currentDate);
						nbOccupiedCore += currentJob.getNodes();
						currentJob = getNextJob();//Moving forward in the job list
						currentDate = currentJob.getDate();
					} catch (notEnoughBudgetException e) {
						e.printStackTrace();
					}

				}else {//Waiting until the new week
					
					currentDate = new Date(currentDate.getTime());
					currentDate.setHours(9);
					currentDate.setMinutes(0);
					currentDate.setSeconds(1);
					switch(currentDate.getDay()) {
					case 6:
						currentDate = new Date(currentDate.getTime()+1000L*3600*24*2);
						break;
					case 5:
						currentDate = new Date(currentDate.getTime()+1000L*3600*24*3);
						break;
					case 0:
						currentDate = new Date(currentDate.getTime()+1000L*3600*24);
						break;
					}

				}
			}else {//There is not enough resources, waiting for the next job to finish
				finishJob = finishJob();
				currentDate = finishJob.getFinishedDate();
				
			}
		}
		while(!jobDone() ) {
			finishJob();
		}
		return res;
	}

	/**
	 * Return the number of jobs accepted in the queue whether they are already processed or not
	 * @return the number of jobs
	 */
	public int getNbJobInTheQueue() {
		return this.jobsQueue.size();
	};

}
