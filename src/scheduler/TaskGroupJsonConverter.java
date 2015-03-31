package scheduler;

import com.eclipsesource.json.JsonObject;

public class TaskGroupJsonConverter implements JsonConverter<TaskGroup> {
	
	public static final int DEFAULT_SPLIT_THRESHOLD = 40;

	@Override
	public TaskGroup fromJson(JsonObject obj) {
		// TODO Auto-generated method stub
		return new TaskGroup(obj.get("name").asString(), obj.get("duration").asInt(), null,
				obj.get("value").asInt(), obj.getInt("urgency", 0),
				obj.getInt("difficulty", Slot.DEFAULT_DIFFICULTY), DEFAULT_SPLIT_THRESHOLD);
	}

	@Override
	public JsonObject toJson(TaskGroup obj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
