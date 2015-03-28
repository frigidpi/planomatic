package autoplan;

import java.util.*;
import java.text.*;

public class Slot{
	private static String startTest = "2012-07-10 14:58";
	private static String endTest = "2012-07-10 16:58";
	
	private static final long minOffset = 60 * 1000;
	
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
	
	public Slot( Date startDate, Date endDate ) {
		this.startTime = startDate;
		this.duration = (endDate.getTime() - startTime.getTime())/ minOffset;
	}
	public Slot( Date startDate, long duration ) {
		startTime = startDate;
		this.duration = duration;// new Date(startTime.getTime() + duration * minOffset);
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