package autoplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonParser {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data.json"));
		JsonObject data = JsonObject.readFrom(br);		
		br.close();
		
		JsonArray jevents = data.get("events").asArray();
		List<Event> events = new ArrayList<>();
		for(JsonValue val : jevents) {
			JsonObject obj = val.asObject();
			events.add(new Event(Slot.fmt.parseDateTime(obj.get("startTime").asString()),
					obj.getInt("duration", 0), obj.get("name").asString(), obj.getInt("difficulty", Slot.DEFAULT_DIFFICULTY)));
		}
		
		// System.out.println(events);
		Scheduler s = new Scheduler(events);	
		
		System.out.println(s.getEvents());
		
		JsonArray jtasks = data.get("tasks").asArray();
		for(JsonValue val : jtasks) {
			JsonObject obj = val.asObject();
			System.out.println(obj.get("name"));
			s.addTask(new Task(obj.get("name").asString(), obj.get("duration").asInt(),
					null, obj.getInt("value", 0), obj.getInt("urgency", 0),
					obj.get("difficulty").asInt()));
		}
//		System.out.println(s.getTasks());
		
		s.schedule();
		
		s.prettyPrintGap();	
		
	}
	
}
