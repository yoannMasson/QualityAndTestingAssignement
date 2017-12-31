package simulation;

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

}
