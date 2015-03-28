package autoplan;

import java.util.Date;

public class Event extends Slot{
//	public Event( String startTimeString, String endTimeString ) throws ParseException {
//		super( startTimeString, endTimeString );
//	}
//	public Event( String startTimeString, long duration ) throws ParseException {
//		super( startTimeString, duration );
//	}
	public Event( Date startTimeString, Date endTimeString) {
		super( startTimeString, endTimeString);
	}
	public Event( Date startTimeString, long duration) {
		super( startTimeString, duration);
	}
	public Event( Date startTimeString, Date endTimeString, int difficulty) {
		super( startTimeString, endTimeString, difficulty);
	}
	public Event( Date startTimeString, long duration, int difficulty) {
		super( startTimeString, duration, difficulty);
	}
}