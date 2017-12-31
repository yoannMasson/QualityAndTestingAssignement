package simulation;

public class User {

	private float budget;
	private float initialBudget;
	private UserType type;
	
	
	public User(UserType type, float budget) {
		this.budget = budget;
		this.initialBudget = budget;
		this.type = type;
	}
	
	/**
	 * @return the type
	 */
	public UserType getType() {
		return type;
	}
	
	/**
	 * Return the overall sum that the user has pay through the simulation
	 * @return the sum that the user paid
	 */
	public float getBills() {
		return this.initialBudget - budget;
	}

	/**
	 * @return the budget
	 */
	public float getBudget() {
		return budget;
	}
	/**
	 * @param budget the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	/**
	 * Assign a job to the user
	 * @param the job to assign
	 */
	public void assignJob(Job job) {
		job.setUser(this);
	}

	public void pay(float cost) throws notEnoughBudgetException {
		if( this.budget < cost) {
			throw new notEnoughBudgetException();
		}else {
			this.budget -= cost;
		}
	}
}
