package autoplan;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

public class Gap extends Slot {

	// calculate the starting time of each task
	private List<Task> tasks;
	private int durationLeft;
	
	public Gap( DateTime startTimeString, DateTime endTimeString) {
		super( startTimeString, endTimeString, -2*DEFAULT_DIFFICULTY);
		tasks = new LinkedList<>();
		durationLeft = getDuration();
	}
	
	public Gap( DateTime startTimeString, int duration) {
		super( startTimeString, duration, -2*DEFAULT_DIFFICULTY);		
		tasks = new LinkedList<>();
		durationLeft = getDuration();
	}
	
	public boolean taskFits(Task t) {
		return t.getDuration() <= durationLeft;
	}
	
	public void addTask(Task t) {
		if(!taskFits(t)) return;
		if(tasks.isEmpty())
			t.setStartTime(getStartTime());
		else
			t.setStartTime(tasks.get(tasks.size() - 1).getEndTime());
		tasks.add(t);
		durationLeft -= t.getDuration();
	}
	
	public DateTime taskStartTime() {
		return tasks.get(tasks.size() - 1).getEndTime();
	}	
	
	public int getDurationLeft() {
		return durationLeft;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Task t : tasks)
			sb.append(t);
		return sb.toString();
	}
}