package scheduler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A graph that keeps a copy of its own transpose.
 * @author duncan
 *
 * @param <T>
 */

public class TGraph<T> extends Graph<T> {
	private HashMap<T, List<T>> transpose;
	
	public TGraph() {
		transpose = new HashMap<>();
	}
	
	public void addNode(T node) {
		super.addNode(node);
		transpose.put(node, new LinkedList<>());
	}
	
	public void removeNode(T node) {
		super.removeNode(node);
		transpose.remove(node);
		for(List<T> otherList : transpose.values()) {
			otherList.remove(node);
		}
	}
	
	public void addEdge(T u, T v) {
		super.addEdge(u, v);
		transpose.get(v).add(u);
	}
	
	public List<T> inNeighbours(T node) {
		if(!transpose.containsKey(node))
			throw new RuntimeException("Graph does not contain " + node);
		return transpose.get(node);
	}
	
	public int indegree(T node) {
		return inNeighbours(node).size();
	}
	
	public int outdegree(T node) {
		return neighbours(node).size();
	}
}
