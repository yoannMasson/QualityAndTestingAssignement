package simulation;

import java.util.Date;

import com.sun.scenario.Settings;

public class Job {

	private int nbCores;
	private long duration;
	private Date requestDate;
	private Date startDate;
	private JobStatus status;
	private User user;
	private float cost;

	public Job(int nbCores,Date requestDate, long duration, Queue queue) {

		this.nbCores = nbCores;
		this.status = JobStatus.waiting;
		this.requestDate = requestDate;
		this.duration = duration;
		
		float cost = this.duration%60+1;// We assume that one minute is counted as one hour.
		cost *= queue.getCost();
		this.cost = cost;
	}

	/**
	 * Return the number of nodes the job need
	 * @return the number of nodes required
	 */
	public int getNodes() {
		return nbCores;
	}


	/**
	 * Start the job, you have to provide the date since it is a simulation.
	 * if the job is assigned to a user the method will check that the associated user
	 * has enough budget or will throw an exception otherwise
	 * @param date, the current date
	 * @throws notEnoughBudgetException, when the user has not enough budget
	 */
	public void startJob(Date date) throws notEnoughBudgetException {

		if(this.user != null ) {
			if(user.getBudget() < cost) {
				throw new notEnoughBudgetException("The user can not pay $"+this.getCost());
			}else {
				this.startDate = date;
				user.pay(cost);
			}
		}else {
			this.startDate = date;
		}
		this.status = JobStatus.processing;
	}

	/**
	 * Return the job cost
	 * @return the job cost
	 */
	public float getCost() {
		
		return this.cost;
	}

	/**
	 * Return the date when the job is finished, CHECK that the job is finished before or you will get a null pointer exception
	 * @return the finishing date
	 */
	public Date getFinishedDate() {

		long date =  (this.startDate.getTime()+this.duration*1000);
		return new Date(date);
	}

	/**
	 * return the time that the programm waited between the request and the starting date
	 */
	public long getWaitingTime() {

		return (Math.abs(this.startDate.getTime() - this.requestDate.getTime())*1000);
	}

	/**
	 * Return the time the job will run in seconds
	 * @return the duration of the job in seconds
	 */
	public long getDuration() {

		return duration;
	}


	/**
	 * Set the status of the job
	 * @param status, the new status of the job
	 */
	public void setStatus(JobStatus status) {
		this.status = status;
	}

	/**
	 * Return the status of the job
	 * @return the status of the job.
	 */
	public JobStatus getJobStatus() {
		return this.status;
	}

	public String toString() {
		return "the job is: "+this.getJobStatus()+"\nIt was created on "+this.requestDate+"\n and is asking for "+this.getNodes()+" nodes";
	}

	public void setUser(User user) {

		this.user = user;

	}

	/**
	 * Return the date of the creation of the job
	 * @return the creation date
	 */
	public Date getDate() {
		
		return this.requestDate;
	}

}
