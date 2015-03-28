import java.util.*;
import java.io.*;
import java.text.*;

public class Gap extends Slot{
	public Gap( String startTimeString, String endTimeString ) throws ParseException {
		super( startTimeString, endTimeString );
	}
	public Gap( String startTimeString, long duration ) throws ParseException {
		super( startTimeString, duration );
	}
}