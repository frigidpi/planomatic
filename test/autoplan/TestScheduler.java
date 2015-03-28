package autoplan;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class TestScheduler {

	
	@Test
	public void testSortEvents() throws ParseException {
		
		Scheduler s = new Scheduler();
		
		Event e1 = new Event( Slot.minuteWise.parse("2012-07-10 14:58"), 10 );
		Event e2 = new Event( Slot.minuteWise.parse("2012-07-10 15:58"), 10 );
		Event e3 = new Event( Slot.minuteWise.parse("2012-07-10 16:58"), 10 );
		
		s.addEvent(e2);
		s.addEvent(e3);
		s.addEvent(e1);
		
		List<Event> expected = new LinkedList<Event>();
		expected.add(e1);
		expected.add(e2);
		expected.add(e3);
		
		s.sortEvents();
		
		assertTrue(s.getEvents().equals(expected));
	}
	
	@Test
	public void testGaps() throws ParseException {
		
		Scheduler s = new Scheduler();
		
		Event e1 = new Event( Slot.minuteWise.parse("2012-07-10 14:58"), 10 );
		Event e2 = new Event( Slot.minuteWise.parse("2012-07-10 15:58"), 10 );
		Event e3 = new Event( Slot.minuteWise.parse("2012-07-10 16:58"), 10 );
		
		s.addEvent(e2);
		s.addEvent(e3);
		s.addEvent(e1);
		
		s.findGaps();
		
		System.out.println(s.getGaps());
		
		assertTrue(true);
	}
	
	@Test
	public void testTasks() throws ParseException {
		
		List<Task> res;
		Scheduler s = new Scheduler();
		
		Task[] tasks = new Task[4];
		tasks[0] = new Task(15, "Hello world", null, 0);
		tasks[1] = new Task(5, "Hello world", null, 5);
		tasks[2] = new Task(45, "Hello world", null, 10);
		tasks[3] = new Task(30, "Hello world", null, 3);
		
		for(int i = 0; i < tasks.length; i++)
			s.addTask(tasks[i]);
		
		s.addDependency(tasks[2], tasks[1]);
		s.addDependency(tasks[1], tasks[3]);
		s.addDependency(tasks[3], tasks[0]);
		
		Event e1 = new Event( Slot.minuteWise.parse("2012-07-10 14:58"), 10 );
		Event e2 = new Event( Slot.minuteWise.parse("2012-07-10 15:58"), 10 );
		Event e3 = new Event( Slot.minuteWise.parse("2012-07-10 16:58"), 10 );
		
		s.addEvent(e2);
		s.addEvent(e3);
		s.addEvent(e1);
		
		s.findGaps();
		
		res = s.schedule();
		
		System.out.println(res);
		
		assertTrue(true);
	}

}
