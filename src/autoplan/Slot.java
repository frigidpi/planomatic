package autoplan;

import java.util.*;
import java.text.*;

public class Slot{
	
	private static final long minOffset = 60 * 1000;
	public static final int DEFAULT_DIFFICULTY = 5;
	
	private int difficulty;
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public static DateFormat minuteWise = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private Date startTime;	// = minuteWise.parse( startTest );
	private long duration;
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
	
	public Slot( Date startDate, Date endDate) {
		this(startDate, endDate, DEFAULT_DIFFICULTY);
	}
	public Slot( Date startDate, long duration) {
		this(startDate, duration, DEFAULT_DIFFICULTY);
	}
	
	public Slot( Date startDate, Date endDate, int difficulty) {
		this.startTime = startDate;
		this.duration = (endDate.getTime() - startTime.getTime())/ minOffset;
		this.difficulty = difficulty;
	}
	public Slot( Date startDate, long duration, int difficulty) {
		startTime = startDate;
		this.duration = duration;// new Date(startTime.getTime() + duration * minOffset);
		this.difficulty = difficulty;
	}
	
	public Date getEndTime(){
		return new Date(startTime.getTime() + duration * minOffset);
	}
	
	public long getDuration(){
		//return (endTime.getTime() - startTime.getTime())/ minOffset;
		return duration;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public int timeliness(int stress) {
		return Math.abs(stress + getDifficulty()*(int)getDuration());
	}

//	private static Date changeDateByMins( Date d, long byMins ){
//		return new Date( d.getTime() + byMins * minOffset );
//	}
	public void changeStarting( long byMinutes ){
		this.startTime = new Date( startTime.getTime() + byMinutes * minOffset );
		duration -= byMinutes;
	}
	
	public String toString() {
		return String.format("%s - %s (%d)", 
				minuteWise.format(getStartTime()),
				minuteWise.format(getEndTime()),
				duration);
	}
}