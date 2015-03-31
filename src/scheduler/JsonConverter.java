package scheduler;

import com.eclipsesource.json.JsonObject;

/**
 * Interface for a class that converts to and from JSON objects
 * @author duncan
 *
 * @param <T> type to convert
 */
public interface JsonConverter<T> {
	
	/**
	 * Convert the JSON object into type T
	 * @param obj
	 * @return
	 */
	public T fromJson(JsonObject obj);
	
	/**
	 * Convert the object of T into a JSON object
	 * @param obj
	 * @return
	 */
	public JsonObject toJson(T obj);
	
}
