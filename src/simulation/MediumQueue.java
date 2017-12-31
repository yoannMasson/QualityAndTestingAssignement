package simulation;

public class MediumQueue extends Queue {

	public MediumQueue(int nbTraditionalCore, int nbAcceleratedCore, int nbSpecializedCore,
			SimulationSettings settings) {
		super(nbTraditionalCore, nbAcceleratedCore, nbSpecializedCore, settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getCost() {
		
		return getSettings().get("MEDIUM_PRICE"); 
	}

	@Override
	public boolean addJob(Job job) {
		if(job.getNodes() <= 0.1*getSettings().getTotalNumberNodes() && job.getDuration() <= 3600*8) {
			this.jobsQueue.add(job);
			return true;
		}
		return false;
	}

}
