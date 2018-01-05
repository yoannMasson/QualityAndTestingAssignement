package simulation;

import java.util.Date;
import java.util.LinkedList;

public class HugeQueue extends Queue {

	public HugeQueue(int nbTraditionalCore, int nbAcceleratedCore, int nbSpecializedCore,
			SimulationSettings settings) {
		super(nbTraditionalCore, nbAcceleratedCore, nbSpecializedCore, settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getCost() {

		return getSettings().get("HUGE_PRICE"); 
	}

	@Override
	public boolean addJob(Job job) {
		if(job.getNodes() <= getSettings().getTotalNumberNodes() && job.getDuration() <= 3600*48) {
			this.jobsQueue.add(job);
			return true;
		}
		return false;
	}

	/**
	 * Process the job in the queue resulting in the wanted output, update the status of jobs and the user budget
	 * @return the processed result
	 */
	@Override
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
					if (isWeekEnd(currentDate) && isWeekEnd(finishedDate)) {//This is the week end
						try {
							currentJob.startJob(currentDate);
							occupieCores(currentJob.getNodes());
							currentJob = getNextJob();//Moving forward in the job list
							currentDate = currentJob.getDate();
						} catch (notEnoughBudgetException e) {
							e.printStackTrace();
						}

					}else {//Waiting until the next week end

						currentDate = new Date(currentDate.getTime());
						currentDate.setHours(17);
						currentDate.setMinutes(0);
						currentDate.setSeconds(1);
						switch(currentDate.getDay()) {
						case 0:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24*5);
							break;
						case 1:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24*4);
							break;
						case 2:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24*3);
							break;
						case 3:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24*2);
							break;
						case 4:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24);
							break;
						case 6:
							currentDate = new Date(currentDate.getTime()+1000L*3600*24*6);
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
		}
	}
}
