package simulation;

public class SmallQueue extends Queue {

	public SmallQueue(int nbTraditionalCore, int nbAcceleratedCore, int nbSpecializedCore,
			SimulationSettings settings) {
		super(nbTraditionalCore, nbAcceleratedCore, nbSpecializedCore, settings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getCost() {

		return getSettings().get("SMALL_PRICE"); 

	}

	@Override
	public boolean addJob(Job job) {
		if(job.getNodes() <= 2 && job.getDuration() <= 3600) {
			this.jobsQueue.add(job);
			return true;
		}
		return false;
	}

}
