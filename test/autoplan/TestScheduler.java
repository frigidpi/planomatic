package autoplan;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import scheduler.Event;
import scheduler.Scheduler;

public class TestScheduler {
	
	Event e1, e2, e3, e4;
	List<Event> events = new ArrayList<>();
	
	public TestScheduler() {
		
		e1 = new Event( new DateTime(2015, 3, 28, 10, 36), 10, "Frist date");
		e2 = new Event( new DateTime(2015, 3, 28, 11, 36), 10, "Take bath");
		e3 = new Event( new DateTime(2015, 3, 28, 12, 36), 10, "Algebra class");
		e4 = new Event( new DateTime(2015, 3, 28, 13, 36), 10, "Listen to prawn");
		
//		e1 = new Event( Slot.minuteWise.parse("2012-07-10 14:58"), 10 );
//		e2 = new Event( Slot.minuteWise.parse("2012-07-10 15:58"), 10 );
//		e3 = new Event( Slot.minuteWise.parse("2012-07-10 16:58"), 10 );
//		e4 = new Event( Slot.minuteWise.parse("2012-07-10 17:58"), 10 );
		
		events.add(e2);
		events.add(e3);
		events.add(e1);
		events.add(e4);
	}
	
	@Test
	public void testSortEvents() throws ParseException {
		
		Scheduler s = new Scheduler(events);
		
		List<Event> expected = new LinkedList<Event>();
		expected.add(e1);
		expected.add(e2);
		expected.add(e3);
		expected.add(e4);
		
		s.schedule();
		
		assertTrue(s.getEvents().equals(expected));
	}
	
	@Test
	public void testGaps() throws ParseException {
		
		Scheduler s = new Scheduler(events);
		
		s.schedule();

		assertEquals(s.getGaps().size(), events.size() - 1);
	}
	
//	@Test
//	public void testTasks() throws ParseException {
//		
//		Scheduler s = new Scheduler(events);
//		
//		Task[] tasks = new Task[4];
//		tasks[0] = new Task(15, "0", null, 0, 100);
//		tasks[1] = new Task(5, "1", null, 5, 0);
//		tasks[2] = new Task(45, "2", null, 10, 0);
//		tasks[3] = new Task(30, "3", null, 3, 0);
//		//tasks[4] = new Task(30, "3", null, 3, 0);
//		
//		for(int i = 0; i < tasks.length; i++)
//			s.addTask(tasks[i]);
//		
////		s.addDependency(tasks[2], tasks[1]);
////		s.addDependency(tasks[1], tasks[3]);
////		s.addDependency(tasks[3], tasks[0]);
//		
//		s.addDependency(tasks[1], tasks[3]);
//		s.addDependency(tasks[0], tasks[2]);
//		
//		System.out.println(s.getGaps());
//		
//		assertTrue(true);
//	}
	
	@Test
	public void testTasks2() throws ParseException {
		
		Scheduler s = new Scheduler(events);
		
//		Task[] tasks = new Task[5];
//		tasks[0] = new Task("0", 45, null, 0, 0, 2);
//		tasks[1] = new Task("1", 20, null, 0, 3, 2);
//		tasks[2] = new Task("2", 20, null, 0, 0, 2);
//		tasks[3] = new Task("3", 20, null, 0, 0, -5);
//		tasks[4] = new Task("4", 20, null, 0, 0, -5);
//		
//		for(int i = 0; i < tasks.length; i++)
//			s.addTask(tasks[i]);
		
//		s.addDependency(tasks[2], tasks[1]);
//		s.addDependency(tasks[1], tasks[3]);
//		s.addDependency(tasks[3], tasks[0]);
		
//		s.addDependency(tasks[1], tasks[3]);
//		s.addDependency(tasks[0], tasks[2]);
		
		s.schedule();
		s.prettyPrintGap();
		
		assertTrue(true);
	}

}
