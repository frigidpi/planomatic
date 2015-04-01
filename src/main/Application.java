package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import scheduler.Event;
import scheduler.EventJsonConverter;
import scheduler.Scheduler;
import scheduler.TaskGroup;
import scheduler.TaskGroupJsonConverter;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;

public class Application {
	
	/**
	 * Runs the scheduler.
	 * @param in Reader for a JSON file containing events, tasks and dependencies
	 * @return JsonObject containing the list of scheduled tasks
	 * @throws IOException
	 */
	public static JsonObject run(Reader in) throws IOException {
		JsonObject data = JsonObject.readFrom(in);
		
		EventJsonConverter eventConverter = new EventJsonConverter();
		TaskGroupJsonConverter taskConverter = new TaskGroupJsonConverter();
		
		JsonArray jevents = data.get("events").asArray();
		List<Event> events = new ArrayList<>();
		for(JsonValue val : jevents)
			events.add(eventConverter.fromJson(val.asObject()));
		
		
		
		JsonArray jtasks = data.get("tasks").asArray();
		ArrayList<TaskGroup> tasks = new ArrayList<>();
		for(JsonValue val : jtasks)
			tasks.add(taskConverter.fromJson(val.asObject()));
		
//		 System.out.println(events);
		Scheduler s = new Scheduler(events, tasks);
//		System.out.println(s.getEvents());
		
//		System.out.println(s.getTasks());
		
		// Add dependencies
		JsonObject jdeps = data.get("dependencies").asObject();
		for(Member m : jdeps) {
			int i = Integer.parseInt(m.getName());
			for(JsonValue val : m.getValue().asArray()) {
				int j = val.asInt();
				s.addDependency(tasks.get(i), tasks.get(j));
			}
		}
		
		s.schedule();
		
		return s.toJson();
		
//		s.prettyPrintGap();		
		
	}

	public static void main(String[] args) throws IOException {
		
		if(args.length != 2) {
			System.out.println("Must take arguments: infile outfile");
			System.exit(0);
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		JsonObject result = run(reader);
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
		result.writeTo(writer);
		writer.close();
	}
	
}
