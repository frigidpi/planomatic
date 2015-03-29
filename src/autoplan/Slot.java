package autoplan;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;
import java.text.*;

public class Slot{
	
	private static final long minOffset = 60 * 1000;
	public static final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a");
	public static final int DEFAULT_DIFFICULTY = 5;
	
	private int difficulty;
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public static DateFormat minuteWise = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private DateTime startTime;	// = minuteWise.parse( startTest );
	private int duration;
	//private Date endTime;	// = minuteWise.parse( endTest );
	
	public Date minuteWiseParse( String timeString ) throws ParseException{
		return ( minuteWise.parse( timeString ) );
	}
	
	//constructors
//	public Slot( String startTimeString, String endTimeString ) throws ParseException {
//		startTime = minuteWiseParse( startTimeString );
//		endTime = minuteWiseParse( endTimeString );
//	}
//	public Slot( String startTimeString, long duration ) throws ParseException {
//		startTime = minuteWiseParse( startTimeString );
//		this.duration = duration;
//		//endTime = new Date(startTime.getTime() + duration * minOffset);
//	}
	
	public Slot( DateTime startDate, DateTime endDate) {
		this(startDate, endDate, DEFAULT_DIFFICULTY);
	}
	public Slot( DateTime startDate, int duration) {
		this(startDate, duration, DEFAULT_DIFFICULTY);
	}
	
	public Slot( DateTime startDate, DateTime endDate, int difficulty) {
		this.startTime = startDate;
		this.duration = (int)((endDate.getMillis() - startTime.getMillis())/ minOffset);
		this.difficulty = difficulty;
	}
	public Slot( DateTime startDate, int duration, int difficulty) {
		startTime = startDate;
		this.duration = duration;// new Date(startTime.getTime() + duration * minOffset);
		this.difficulty = difficulty;
	}
	
	public DateTime getEndTime(){
		return startTime.plusMinutes(duration);
	}
	
	public int getDuration(){
		//return (endTime.getTime() - startTime.getTime())/ minOffset;
		return duration;
	}
	
	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	
	public static int computeStress(int difficulty, int duration) {
		return (int)(difficulty*Math.log(duration));
	}
	
	public int computeStress() {
		return computeStress(difficulty, duration);
	}
	
	public int timeliness(int stress) {
		return Math.abs(stress + computeStress());
	}

//	private static Date changeDateByMins( Date d, long byMins ){
//		return new Date( d.getTime() + byMins * minOffset );
//	}
	public void changeStarting( int byMinutes ){
		this.startTime = startTime.plusMinutes(byMinutes);
		duration -= byMinutes;
	}
	
	public String toString() {
		return String.format("%s - %s (%d)", 
				getStartTime().toString(fmt),
				getEndTime().toString(fmt),
				duration);
	}
}