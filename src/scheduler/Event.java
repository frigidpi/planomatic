package scheduler;

import org.joda.time.DateTime;

/**
 * A fixed time slot
 * @author duncan
 *
 */

public class Event extends Slot {
	private String name;
	
	public Event() {
		super(null, DEFAULT_DIFFICULTY);
	}
	
	public Event( DateTime startTimeString, DateTime endTimeString, String name) {
		super( startTimeString, endTimeString);
		this.name = name;
	}
	
	public Event( DateTime startTimeString, int duration, String name) {
		super( startTimeString, duration);
		this.name = name;
	}
	
	public Event( DateTime startTimeString, DateTime endTimeString, String name, int difficulty) {
		this( startTimeString, endTimeString, name);
		setDifficulty(difficulty);
	}
	
	public Event( DateTime startTimeString, int duration, String name, int difficulty) {
		this( startTimeString, duration, name);		
		setDifficulty(difficulty);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public String toString() {
		return String.format("%s (%s - %s)", 
				name,
				getStartTime().toString(fmt),
				getEndTime().toString(fmt));
	}
}