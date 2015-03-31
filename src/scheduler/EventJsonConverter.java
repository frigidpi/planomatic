package scheduler;

import org.joda.time.DateTime;

import com.eclipsesource.json.JsonObject;

public class EventJsonConverter implements JsonConverter<Event> {

	@Override
	public Event fromJson(JsonObject obj) {
		
		String startTimeString = obj.get("startTime").asString();
		DateTime startTime = null;
		
		// check if start time is given in millis or time format
		if(startTimeString.matches("^\\d+$")) {
			startTime = new DateTime(Long.parseLong(startTimeString));
		} else {
			startTime = Slot.fmt.parseDateTime(startTimeString);
		}
		
		return new Event(startTime, obj.getInt("duration", 0),
				obj.get("name").asString(), obj.getInt("difficulty", Slot.DEFAULT_DIFFICULTY));
	}

	@Override
	public JsonObject toJson(Event event) {
		JsonObject obj = new JsonObject();
		obj.set("name", event.getName());
		obj.set("difficulty", event.getDifficulty());
		obj.set("startTime", event.getStartTime().getMillis());
		return obj;
	}

}
