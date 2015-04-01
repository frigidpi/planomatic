package scheduler;

import com.eclipsesource.json.JsonObject;

public class TaskJsonConverter implements JsonConverter<Task> {

	@Override
	public Task fromJson(JsonObject obj) {
		return new Task(null, obj.get("duration").asInt(),
				obj.get("difficulty").asInt());
	}

	@Override
	public JsonObject toJson(Task task) {
		JsonObject obj = new JsonObject();
		obj.set("id", task.getParent().getId());
		obj.set("name", task.getName());
		obj.set("duration", task.getDuration());
		obj.set("startTime", task.getStartTime().getMillis());
		return obj;
	}

	
	
}
