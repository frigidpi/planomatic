package autoplan;

public class Task extends Slot{
	
	//private fields
	private String name;
	private String text;
	private int value;
	private int urgency;
	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
//	//constructor
//	public Task( String startTimeString, String endTimeString, String name, String text, int importance) throws ParseException {
//		super( startTimeString, endTimeString );
//		this.name = name;
//		this.text = text;
//		this.importance = importance;
//	}
	public Task(String name, int duration, String text, int value, int urgency, int difficulty) {
		super( null, duration, difficulty);
		this.name = name;
		this.text = text;
		this.value = value;
		this.urgency = urgency;
	}
	
	public Task(String name, int duration, String text, int value) {
		this(name, duration, text, value, DEFAULT_URGENCY, DEFAULT_DIFFICULTY);
	}
	
	public Task(Task t, int duration) {
		this(t.name, duration, t.text, t.value, t.urgency, t.getDifficulty());
	}
	
	//access
	public String getName(){
		return name;
	} 
	public String getText(){
		return text;
	}
	public int getValue(){
		return value;
	}
	
	public void setName( String newName ){
		name = newName;
	} 
	public void setText( String newText ){
		text = newText;
	} 	
	public void setValue( int newImportance ){
		value = newImportance;
	}
	
	/**
	 * Given daysUntilDue, urgency = 10 - (daysUntilDue - now)
	 * @return
	 */
	public int getUrgency() {
		return urgency;
	}
	
	public int priority(int stress) {
		return value * WEIGHT_VALUE + getUrgency() * WEIGHT_URGENCY
				+ timeliness(stress) * WEIGHT_TIMELINESS;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public String toString() {
		return String.format("%s (%s for %d, d=%d)\n", 
				getName(),
				getStartTime() == null ? "unscheduled" : getStartTime().toString(fmt),
				getDuration(), getDifficulty());
	}
	
}
