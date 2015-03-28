package autoplan;

import java.text.*;

public class Task extends Slot{
	
	//private fields
	private String name;
	private String text;
	private int importance;
	
//	//constructor
//	public Task( String startTimeString, String endTimeString, String name, String text, int importance) throws ParseException {
//		super( startTimeString, endTimeString );
//		this.name = name;
//		this.text = text;
//		this.importance = importance;
//	}
	public Task(long duration, String name, String text, int importance ) throws ParseException {
		super( null, duration );
		this.name = name;
		this.text = text;
		this.importance = importance;		
	}	
	
	//access
	public String getName(){
		return name;
	} 
	public String getText(){
		return text;
	}
	public int getImportance(){
		return importance;
	}
	
	//change
	public void setName( String newName ){
		name = newName;
	} 
	public void setText( String newText ){
		text = newText;
	} 	
	public void setImportance( int newImportance ){
		importance = newImportance;
	}
	
	public String toString() {
		return String.format("%s (%s for %d)", 
				name,
				minuteWise.format(getStartTime()),
				getDuration());
	}
	
}
