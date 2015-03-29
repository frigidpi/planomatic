package autoplan;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Gap extends Slot {

	// calculate the starting time of each task
	private List<Task> tasks;
	private long durationLeft;
	
	public Gap( Date startTimeString, Date endTimeString) {
		super( startTimeString, endTimeString, -2*DEFAULT_DIFFICULTY);
		tasks = new LinkedList<>();
		durationLeft = getDuration();
	}
	
	public Gap( Date startTimeString, long duration) {
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
	
	public Date taskStartTime() {
		return tasks.get(tasks.size() - 1).getEndTime();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Task t : tasks)
			sb.append(t);
		return sb.toString();
	}
}