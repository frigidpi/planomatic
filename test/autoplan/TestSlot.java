package autoplan;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.junit.Test;

public class TestSlot {

	@Test
	public void test() throws ParseException {

		Slot mySlot = new Event( new DateTime(2012, 7, 10, 14, 28), 123, "Go to hell");
		long duration = mySlot.getDuration();
		assertEquals(duration, 123);
	}

}
