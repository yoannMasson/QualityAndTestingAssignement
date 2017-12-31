package simulation;

public class LargeQueue extends Queue {

	public LargeQueue(int nbTraditionalCore, int nbAcceleratedCore, int nbSpecializedCore,
			SimulationSettings settings) {
		super(nbTraditionalCore, nbAcceleratedCore, nbSpecializedCore, settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getCost() {
		
		return getSettings().get("LARGE_PRICE"); 
	}

	@Override
	public boolean addJob(Job job) {
		if(job.getNodes() <=  0.5*getSettings().getTotalNumberNodes() && job.getDuration() <= 3600*16) {
			this.jobsQueue.add(job);
			return true;
		}
		return false;
	}

}
