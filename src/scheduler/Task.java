package scheduler;

public class Task extends Slot{
	
	private TaskGroup parent;
	
	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
	public Task(TaskGroup g, int duration, int difficulty) {
		super( null, duration, difficulty);
		parent = g;
	}
	
	public Task(TaskGroup g, int duration) {
		super(null, duration, DEFAULT_DIFFICULTY);
		parent = g;
	}
	
	/**
	 * Copy constructor
	 * @param t
	 * @param duration
	 */
	public Task(Task t, int duration) {
		this(t.parent, duration, t.getDifficulty());
	}
	
	public int priority(int stress) {
		return parent.getValue() * WEIGHT_VALUE + parent.getUrgency() * WEIGHT_URGENCY
				+ timeliness(stress) * WEIGHT_TIMELINESS;
	}

	public TaskGroup getParent() {
		return parent;
	}

	public void setParent(TaskGroup parent) {
		this.parent = parent;
	}
	
	public String getName() {
		return parent.getName();
	}

	public void setName(String name) {
		parent.setName(name);
	}

	public int getValue() {
		return parent.getValue();
	}

	public void setValue(int value) {
		parent.setValue(value);
	}

	/**
	 * Given daysUntilDue, urgency = 10 - (daysUntilDue - now)
	 * @return
	 */
	public int getUrgency() {
		return parent.getUrgency();
	}

	public void setUrgency(int urgency) {
		parent.setUrgency(urgency);
	}

	public String toString() {
		return String.format("%s (%s for %d, d=%d)\n", 
				parent.getName(),
				getStartTime() == null ? "unscheduled" : getStartTime().toString(fmt),
				getDuration(), getDifficulty());
	}
	
}
