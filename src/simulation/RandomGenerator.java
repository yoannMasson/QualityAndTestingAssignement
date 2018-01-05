package simulation;

/**
 * This class is used to get the random value of the simulation.
 * @author Yoann Masson
 *
 */
public class RandomGenerator {

	private SimulationSettings settings;//Settings from the user
	
	/**
	 * Construct a random generator from the user settings
	 * @param settings
	 */
	public RandomGenerator(SimulationSettings settings) {
	
		this.settings = settings;

	}
	
	/**
	 * Return the run time of a job in seconds
	 * @return the job duration in seconds.
	 */
	public int getJobTime() {
		float lambdaJobTime = this.settings.get("DELTA_JOB_TIME");
		if(lambdaJobTime == -1) {
			lambdaJobTime = 1f;
		}
		return (int) (3600*expNumber(lambdaJobTime))+1;
	}
	
	/**
	 * Return time between two consecutive job requests
	 * @return the time between two jobs in seconds.
	 */
	public int getTimeBetweenJobs() {
		float lambdaRequestTime = this.settings.get("DELTA_REQUEST_TIME");
		if(lambdaRequestTime == -1) {
			lambdaRequestTime = 1f;
		}
		return (int) (3600*expNumber(lambdaRequestTime));
		
	}
	
	/**
	 * Return the number of cores required by a job, will be at least 1
	 * @return the size of  job.
	 */
	public int getJobSize() {
		float lambdaJobSize = this.settings.get("DELTA_SIZE");
		if(lambdaJobSize == -1) {
			lambdaJobSize = 1f;
		}
		return (int) (2*expNumber(lambdaJobSize)+1);
		
	}
	
	/**
	 * Return a random value following an exponential distribution
	 * @param lambda, the value of lambda for the exponential distribution
	 * @return a random number
	 */
	private double expNumber(double lambda) {
		
		double rand = Math.random();
	    return  Math.log(1-rand)/(-lambda);
	    
	}
}
