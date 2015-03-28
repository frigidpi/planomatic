package autoplan;

import java.util.List;

public class Scheduler {

	private List< Task > tasksOrdered;
	private List< Event > events;
	private List< Gap > gaps;

	public addEvent( Event ev ){
		events.add( ev );
		Collections.sort( events, (Event ev1, Event ev2) -> (ev1.getStartDate()).compareTo(ev2.getStartDate()) );
	}
	
	
	
	public List<Task> schedule() {
		return null;
	}
	
}
