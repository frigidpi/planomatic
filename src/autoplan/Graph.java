package autoplan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph<T> {

	private HashMap<T, List<T>> adj;
	
	public Graph() {
		adj = new HashMap<>();
	}
	
	public Set<T> getNodes() {
		return adj.keySet();
	}
	
	public void addNode(T node) {
		adj.put(node, new LinkedList<T>());
	}
	
	public void removeNode(T node) {
		if(!adj.containsKey(node))
			throw new RuntimeException("Graph does not contain " + node);
		adj.remove(node);
		for(List<T> otherList : adj.values()) {
			otherList.remove(node);
		}
	}
	
	public void addEdge(T u, T v) {
		if(!adj.containsKey(u))
			throw new RuntimeException("Graph does not contain " + u);
		adj.get(u).add(v);
	}
	
	public boolean existsEdge(T u, T v) {
		if(!adj.containsKey(u))
			throw new RuntimeException("Graph does not contain " + u);
		return adj.get(u).contains(v);
	}
	
	public List<T> neighbours(T node) {
		if(!adj.containsKey(node))
			throw new RuntimeException("Graph does not contain " + node);
		return adj.get(node);
	}
	
}
