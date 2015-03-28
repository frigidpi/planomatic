package autoplan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {

	private int stress;
	private TGraph<Task> g;
	private List<Event> events;
	private ArrayList<Gap> gaps;
	public final int DEFAULT_SPLIT_THRESHOLD = 40;
	private int splitThreshold; 
	
	public Scheduler() {
		events = new ArrayList<Event>();
		gaps = new ArrayList<Gap>();
		g = new TGraph<Task>();
		splitThreshold = DEFAULT_SPLIT_THRESHOLD;
	}

	public void findGaps() {
		sortEvents();
		for(int i = 0; i < events.size() - 1; i++) {
			gaps.add(new Gap(events.get(i).getEndTime(), events.get(i + 1).getStartTime()));
		}
	}
	
	public List<Gap> getGaps() {
		return gaps;
	}

	public void addEvent( Event ev ){
		events.add( ev );
	}
	
	public void sortEvents() {
		Collections.sort( events, (Event ev1, Event ev2) -> (ev1.getStartTime()).compareTo(ev2.getStartTime()) );
	}
	
	/**
	 * find first task that fits in current gap
	 * assign start time to task, remove from list
	 * and add to assignedTasks list; delay the gap
	 * @param task
	 * @param gap
	 * @return
	 */
	public Task fitTask(List<Task> leaves, Gap gap) {
		sortLeaves(leaves);
		Iterator<Task> taskIterator = leaves.iterator();
		Task t, found = null;
		
		System.out.println("---LEAVES----");
		for(Task tp : leaves) {
			System.out.format("Task %s: %d\n", tp.getName(), tp.priority(stress));
		}
		System.out.println("------------");
		
		while(taskIterator.hasNext()) {
			t = taskIterator.next();
			
			if(t.getDuration() <= gap.getDuration()) {
				t.setStartTime(gap.getStartTime());
				gap.changeStarting(t.getDuration());
				
				found = t;
				taskIterator.remove();
				break;
			}
		}
		if(found != null) {
			// make all neighbours that only depend on this leaves
			for(Task dependent : g.inNeighbours(found)) {
				if(g.outdegree(dependent) == 1)
					leaves.add(dependent);
			}
			g.removeNode(found);
		}
		
		return found;
	}
	
	public List<Task> findLeaves() {
		// finds all the leaves
		List<Task> leaves = new ArrayList<>();
		for(Task t : g.getNodes()) {
			if(g.outdegree(t) == 0)
				leaves.add(t);
		}
		return leaves;
	}
	
	public void sortLeaves(List<Task> leaves) {
		Collections.sort(leaves,
				(Task t1, Task t2) -> t2.priority(stress) - t1.priority(stress));
	}
	
	public List<Task> schedule() {
		
		List<Task> leaves = findLeaves();
		Iterator<Event> eventIterator = events.iterator();
		
		Slot prev = eventIterator.next();
		stress = 0;
		
		List<Task> assignedTasks = new LinkedList<>();
		
		int i = 0;
		
		while(i < gaps.size()) {
			stress += prev.getDifficulty() * (int)prev.getDuration();
			System.out.format("Stress meter: %d\n", stress);
			
			Gap cur = gaps.get(i);
			if(leaves.isEmpty())
				break;
			
			Task task = fitTask(leaves, cur);
			
			if(task == null) {
				stress += cur.getDifficulty() * (int)cur.getDuration();
				System.out.format("Stress meter after gap: %d\n", stress);
				prev = eventIterator.next();
				i++;
			} else {
				System.out.format("Selected task %s\n", task.getName());
				assignedTasks.add(task);
				prev = task;
			}
		}
		
		return assignedTasks;
	}
	
	public void addTask(Task t) {
		g.addNode(t);
	}
	
	public void addDependency(Task t1, Task t2) {
		g.addEdge(t1, t2);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
