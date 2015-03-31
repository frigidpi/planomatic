package scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class JsonTest {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data.json"));
		JsonObject data = JsonObject.readFrom(br);	
		br.close();
		
		EventJsonConverter eventConvert = new EventJsonConverter();
		TaskGroupJsonConverter taskConvert = new TaskGroupJsonConverter();
		
		JsonArray jevents = data.get("events").asArray();
		List<Event> events = new ArrayList<>();
		for(JsonValue val : jevents)
			events.add(eventConvert.fromJson(val.asObject()));
		
		// System.out.println(events);
		Scheduler s = new Scheduler(events);
		
		System.out.println(s.getEvents());
		
		JsonArray jtasks = data.get("tasks").asArray();
		for(JsonValue val : jtasks)
			s.addTask(taskConvert.fromJson(val.asObject()));
//		System.out.println(s.getTasks());
		
		s.schedule();
		
		s.prettyPrintGap();	
		
	}
	
}
