package simulation;

import java.util.*;

public abstract class Queue {

	private int nbOccupiedCore;
	private int nbCore;
	private SimulationSettings settings;
	protected List<Job> jobsQueue;
	private List<Job> processingJobs;
	protected List<Job> jobsToProcess;

	/**
	 * Create a queue with a given size for the job it will be in charge of and the number of associated cores
	 * @param size, the size of the job the queue will deal with
	 * @param nbTraditionalCore, the number of traditional cores of the queue
	 * @param nbAcceleratedCore, the number of accelerated cores of the queue
	 * @param nbSpecializedCore, the number of specialized cores of the queue
	 */
	public Queue( int nbTraditionalCore, int nbAcceleratedCore,int nbSpecializedCore, SimulationSettings settings) {

	
		this.nbCore = nbAcceleratedCore+nbSpecializedCore+nbTraditionalCore;
		this.nbOccupiedCore = 0;
		this.settings = settings;
		this.jobsQueue = new LinkedList<Job>();
		this.processingJobs = new LinkedList<Job>();
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
	protected Job getNextJob() {
		if(this.jobsToProcess.size() == 0) {
			return null;
		}else {
			Job j = jobsToProcess.get(0);
			return j;
		}
	}


	/**
	 * Finish the next job and release the associated resources and return the finished job
	 * @return the job just finished
	 */
	public Job finishJob() {
		Job finishedJob = null;

		for(Job j : this.processingJobs) {
			if(finishedJob == null ||j.getFinishedDate().before(finishedJob.getFinishedDate())) {
				finishedJob = j;
			}
		}
		this.nbOccupiedCore -= finishedJob.getNodes();
		finishedJob.setStatus(JobStatus.done);
		this.processingJobs.remove(finishedJob);
		return finishedJob;
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
	protected boolean jobStarted() {
		return jobsToProcess.size() == 0;
	}

	/**
	 *  State if all jobs in the queue have finished
	 * @return true if all jobs are finished, false otherwise
	 */
	protected boolean jobDone() {

		return this.processingJobs.size() == 0;
	}

	/**
	 * Process the job in the queue resulting in the wanted output,
	 *  update the status of jobs and the user budget
	 *  every job that can not be paid will be remove from the queue
	 */
	public void processJobs(){

		long completionTime;
		Date finishedDate,currentDate;
		Job finishJob;

		jobsToProcess = new LinkedList<>(jobsQueue);
		Job currentJob = getNextJob();

		if(currentJob != null ) {
			currentDate = currentJob.getDate();
			while(!jobStarted() ) {
				if(currentJob.getNodes() <= getNbAvailableCore()) {//There is enough resources
					completionTime = currentDate.getTime()+currentJob.getDuration()*1000;
					finishedDate = new Date(completionTime);
					if (!isWeekEnd(finishedDate)) {//This is not the week end
						try {
							this.jobsToProcess.remove(currentJob);
							currentJob.startJob(currentDate);
							this.processingJobs.add(currentJob);
							nbOccupiedCore += currentJob.getNodes();	
						} catch (notEnoughBudgetException e) {
							e.printStackTrace();
							System.out.println("A user is unable to pay, removing its job from the queue");
							this.jobsQueue.remove(currentJob);
							this.processingJobs.remove(currentJob);
							this.jobsToProcess.remove(currentJob);
						}
						currentJob = getNextJob();//Moving forward in the job list
						if(currentJob != null)currentDate = currentJob.getDate();

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
					if(finishJob.getFinishedDate().after(currentJob.getRequestDate())) {//In case the job finish before the one that need the resources
						currentDate = finishJob.getFinishedDate();		
					}

				}

			}
			while(!jobDone()){//Finish all the job processing
				finishJob();
			}
		}
	}

	/**
	 * Return the number of jobs accepted in the queue whether they are already processed or not
	 * @return the number of jobs
	 */
	public int getNbJobInTheQueue() {
		return this.jobsQueue.size();
	};

	/**
	 * Return the overall number of core in the queue
	 * @return the total number of core in the queue
	 */
	public int getNbCore() {
		return nbCore;
	}

	/**
	 * Return the number of occupied core in the queue
	 * @return the total number of occupied core in the queue
	 */
	public int getNbOccupiedCore() {
		return nbOccupiedCore;
	}

	/**
	 * Return the number of available core in the queue
	 * @return the total number of available core in the queue
	 */
	public int getNbAvailableCore() {
		return getNbCore() - getNbOccupiedCore();
	}

	/**
	 * Occupy the number of core given in parameter
	 * @param the number of core to occupy
	 * @return true if there is enough availbale cores false otherwise
	 */
	public boolean occupieCores(int nb) {
		if(nb > getNbAvailableCore()) {
			return false;
		}else {
			nbOccupiedCore += nb;
			return true;
		}
	}

	/**
	 * Free the number of core given in parameter
	 * @param the number of core to free
	 * @return true if there is enough occupied cores false otherwise
	 */
	public boolean freeCores(int nb) {
		if(nb > getNbAvailableCore()) {
			nbOccupiedCore -= nb;
			return true;
		}else {
			return false;
		}
	}

	/**
	 * State if a date is in the week end reserve to huge job
	 * @param date the date to check
	 * @return true if the date is bewtween friday 17h and monday 9h, false otherwise
	 */
	public boolean isWeekEnd(Date date) {
		return ((date.getDay() == 6 ||
				date.getDay() == 0 ||
				(date.getDay() == 5 && date.getHours() >= 17) ||
				(date.getDay() == 1 && date.getHours() < 9)));
	}

	public float getCenterCost() {

		if(jobsQueue.size() != 0) {
			Date firstDate = jobsQueue.get(0).getRequestDate();
			System.out.println(jobsQueue.get(jobsQueue.size()-1));
			Date lastDate = jobsQueue.get(jobsQueue.size()-1).getFinishedDate();
			long time = (lastDate.getTime()-firstDate.getTime())/(1000*3600);
			float cost = settings.get("COMPUTER_COST");

			if (((this.nbCore/32f) == (int)(this.nbCore/32))) {

				cost *= (int)(this.nbCore/32);
			}else {
				cost *= (int)(this.nbCore/32)+1;
			}
			cost *= time;
			return cost;
		}else {
			return 0;
		}
	}
}
