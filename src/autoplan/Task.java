package autoplan;

public class Task extends Slot{
	
	//private fields
	private String name;
	private String text;
	private int value;
	private int urgency;
	public final int WEIGHT_VALUE = 10;
	public final int WEIGHT_URGENCY = 1;
	public final int WEIGHT_TIMELINESS = -5;
	
//	//constructor
//	public Task( String startTimeString, String endTimeString, String name, String text, int importance) throws ParseException {
//		super( startTimeString, endTimeString );
//		this.name = name;
//		this.text = text;
//		this.importance = importance;
//	}
	public Task(long duration, String name, String text, int value, int urgency, int difficulty) {
		super( null, duration, difficulty);
		this.name = name;
		this.text = text;
		this.value = value;
		this.urgency = urgency;
	}
	
	public Task(long duration, String name, String text, int value, int urgency) {
		this(duration, name, text, value, urgency, DEFAULT_DIFFICULTY);
	}
	
	public Task(Task t, int duration) {
		this(duration, t.name, t.text, t.value, t.urgency);
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
				name,
				minuteWise.format(getStartTime()),
				getDuration(), getDifficulty());
	}
	
}
