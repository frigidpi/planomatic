package autoplan;

import java.util.Date;

public class Gap extends Slot{
//	public Gap( String startTimeString, String endTimeString ) throws ParseException {
//		super( startTimeString, endTimeString );
//	}
//	public Gap( String startTimeString, long duration ) throws ParseException {
//		super( startTimeString, duration );
//	}
	public Gap( Date startTimeString, Date endTimeString ) {
		super( startTimeString, endTimeString );
	}
	public Gap( Date startTimeString, long duration ) {
		super( startTimeString, duration );
	}
}