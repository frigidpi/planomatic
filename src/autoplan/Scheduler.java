package autoplan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

public class Scheduler {

	private int stress;
	private TGraph<Task> g;
	private List<Event> events;
	private ArrayList<Gap> gaps;
	public final int DEFAULT_SPLIT_THRESHOLD = 40;
	private int splitThreshold; 
	
	public Scheduler(List<Event> events) {
		this.events = events;
		gaps = new ArrayList<Gap>();
		g = new TGraph<Task>();
		splitThreshold = DEFAULT_SPLIT_THRESHOLD;
	}
	
	private void sortEvents() {
		Collections.sort(events, (Event ev1, Event ev2) -> (ev1.getStartTime()).compareTo(ev2.getStartTime()));
	}
	
	public List<Gap> getGaps() {
		return gaps;
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
			System.out.format("Task %s: %d (val=%d, urg=%d, diff=%d)\n", tp.getName(), tp.priority(stress), tp.getValue(), tp.getUrgency(),
					tp.getDifficulty());
		}
		System.out.println("------------");
		
		while(taskIterator.hasNext()) {
			t = taskIterator.next();
			
			if(gap.taskFits(t)) {
				gap.addTask(t);
//				t.setStartTime(gap.getStartTime());
//				gap.changeStarting(t.getDuration());
				
				found = t;
				taskIterator.remove();
				break;
			}
		}
		
		// remove task from graph
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
	
	public void prettyPrintGap() {
		System.out.println(events.get(0));
		for(int i = 0; i < gaps.size(); i++) {
			System.out.format("GAP %d (%s)\n", i, gaps.get(i).getStartTime().toString(Slot.fmt));
			System.out.println(gaps.get(i));	
			System.out.println(events.get(i + 1));
		}
	}
	
	private void findGaps() {
		gaps.clear();
		sortEvents();
		// find the gaps
		for(int i = 0; i < events.size() - 1; i++) {
			gaps.add(new Gap(events.get(i).getEndTime(), events.get(i + 1).getStartTime()));
		}
	}
	
	public void updateUrgencies() {
		for(Task t : g.getNodes())
			if(t.getUrgency() > 0)
				t.setUrgency(t.getUrgency() + 1);
	}
	
	public void schedule() {
		findGaps();
		List<Task> leaves = findLeaves();
		Iterator<Event> eventIterator = events.iterator();
		
		Slot prev = eventIterator.next();
		DateTime today = prev.getEndTime();
		stress = 0;
		
		int i = 0;
		
		while(i < gaps.size()) {
			stress += Slot.computeStress(prev.getDifficulty(), prev.getDuration());
			
			if(prev.getEndTime().getDayOfYear() != today.getDayOfYear()) {
				today = prev.getEndTime();
				updateUrgencies();
			}
			
			System.out.format("Stress meter: %d\n", stress);
			
			Gap cur = gaps.get(i);
			if(leaves.isEmpty())
				break;
			
			Task task = fitTask(leaves, cur);
			
			if(task == null) {
				stress += Slot.computeStress(cur.getDifficulty(), cur.getDurationLeft());
				System.out.format("Stress meter after gap: %d\n", stress);
				prev = eventIterator.next();
				i++;
			} else {
				System.out.format("Selected task %s\n", task.getName());
				prev = task;
			}
		}
	}
	
	public void addTask(Task t) {
		// divide tasks
		if(t.getDuration() >= splitThreshold) {
			int numChunks = (int)Math.floor((double)t.getDuration()/(splitThreshold/2));
			int rem = (int)t.getDuration() % numChunks;
			for(int i = 0; i < numChunks; i++) {
				int add = (i < rem) ? 1 : 0;
				g.addNode(new Task(t, (int)t.getDuration()/numChunks + add));
			}
		} else {
			g.addNode(t);
		}
	}
	
	public Set<Task> getTasks() {
		return g.getNodes();
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
