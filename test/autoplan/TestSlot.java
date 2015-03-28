package autoplan;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class TestSlot {

	@Test
	public void test() throws ParseException {
		String startTest = "2012-07-10 14:58";
		String endTest = "2012-07-10 17:01";
		
		Slot mySlot = new Event( startTest, endTest );
		long duration = mySlot.getDuration();
		assertEquals(duration, 123);
	}

}
