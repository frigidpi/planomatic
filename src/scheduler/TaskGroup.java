package scheduler;

import java.util.LinkedList;

public class TaskGroup {

	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
	private int id;
	private String name;
	private String text;
	private int value;
	private int urgency;
	private int duration;
	private int difficulty;
	LinkedList<Task> chunks;
	
	public TaskGroup(int id, String name, int duration, String text, int value, int urgency, int difficulty, int threshold) {
		this.name = name;
		this.text = text;
		this.value = value;
		this.urgency = urgency;
		this.duration = duration;
		this.difficulty = difficulty;
		this.id = id;
		createChunks(threshold);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Given daysUntilDue, urgency = 10 - (daysUntilDue - now)
	 * @return
	 */
	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int priority(int stress) {
		return getValue() * WEIGHT_VALUE + getUrgency() * WEIGHT_URGENCY
				+ Slot.timeliness(stress, getDifficulty(), getDuration()) * WEIGHT_TIMELINESS;
	}
	
	/**
	 * Separates the task into chunks if the duration of the task is longer than the given threshold.
	 * The number of chunks is given by floor(duration / (threshold / 2))
	 * @param threshold
	 */
	private void createChunks(int threshold) {
		chunks = new LinkedList<Task>();
		if(getDuration() >= threshold) {
			int numChunks = (int)Math.floor((double)getDuration()/(threshold/2));
			int rem = (int)getDuration() % numChunks;
			for(int i = 0; i < numChunks; i++) {
				int add = (i < rem) ? 1 : 0;
				chunks.push(new Task(this, (int)getDuration()/numChunks + add, getDifficulty()));
			}
		} else {
			chunks.push(new Task(this, getDuration(), getDifficulty()));
		}
		System.out.format("Created %d chunks for %s\n", chunks.size(), getName());
	}
	
	public boolean hasChunks() {
		return !chunks.isEmpty();
	}
	
	public Task nextChunk() {
		return chunks.poll();
	}
	
	public Task peekChunk() {
		return chunks.peek();
	}
	
}
