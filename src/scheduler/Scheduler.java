package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * The main scheduler class that contains the scheduling algorithm
 * @author duncan
 *
 */

public class Scheduler {

	private int stress;
	private TGraph<TaskGroup> g;
	private List<Event> events;
	private ArrayList<Gap> gaps; 
	
	public Scheduler(List<Event> events) {
		this.events = events;
		gaps = new ArrayList<Gap>();
		g = new TGraph<TaskGroup>();
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
	public Task fitTask(List<TaskGroup> leaves, Gap gap) {
		sortLeaves(leaves);
		Task task = null;
		TaskGroup chosenGroup = null;
		
		System.out.println("---LEAVES----");
		for(TaskGroup t : leaves) {
			System.out.format("Task %s: %d (val=%d, urg=%d, diff=%d)\n", t.getName(), t.priority(stress), t.getValue(), t.getUrgency(),
					t.getDifficulty());
		}
		System.out.println("------------");
		
		// For each group, find the first group that fits
		for(TaskGroup taskGroup : leaves) {	
			if(!taskGroup.hasChunks()) {
				System.out.println("Something's wrong!");
			}
			if(gap.taskFits(taskGroup.peekChunk())) {
				task = taskGroup.nextChunk();
				gap.addTask(task);
//				t.setStartTime(gap.getStartTime());
//				gap.changeStarting(t.getDuration());	
				chosenGroup = taskGroup;	
				break;
			}
		}
		
		// remove task from graph if it has no more subtasks
		if(chosenGroup != null && !chosenGroup.hasChunks()) {
			leaves.remove(chosenGroup);
			// make all neighbours that only depend on this leaves
			for(TaskGroup dependent : g.inNeighbours(chosenGroup)) {
				if(g.outdegree(dependent) == 1)
					leaves.add(dependent);
			}
			g.removeNode(chosenGroup);
		}
		
		return task;
	}
	
	private List<TaskGroup> findLeaves() {
		// finds all the leaves
		List<TaskGroup> leaves = new ArrayList<>();
		for(TaskGroup t : g.getNodes()) {
			if(g.outdegree(t) == 0)
				leaves.add(t);
		}
		return leaves;
	}
	
	public void sortLeaves(List<TaskGroup> leaves) {
		Collections.sort(leaves,
				(TaskGroup t1, TaskGroup t2) -> t2.priority(stress) - t1.priority(stress));
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
		for(TaskGroup t : g.getNodes())
			if(t.getUrgency() > 0)
				t.setUrgency(t.getUrgency() + 1);
	}
	
	public void schedule() {
		findGaps();
		List<TaskGroup> leaves = findLeaves();
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
	
	public void addTask(TaskGroup t) {
		g.addNode(t);
	}
	
	public List<Task> chunk(TaskGroup tg) {
		return null;
	}
		
	public Set<TaskGroup> getTasks() {
		return g.getNodes();
	}
	
	public void addDependency(TaskGroup t1, TaskGroup t2) {
		g.addEdge(t1, t2);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
