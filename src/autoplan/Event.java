import java.util.*;
import java.io.*;
import java.text.*;

public class Event extends Slot{
	public Event( String startTimeString, String endTimeString ) throws ParseException {
		super( startTimeString, endTimeString );
	}
	public Event( String startTimeString, long duration ) throws ParseException {
		super( startTimeString, duration );
	}
}