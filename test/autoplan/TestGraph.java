package autoplan;

import static org.junit.Assert.*;

import org.junit.Test;

import scheduler.Graph;

public class TestGraph {

	@Test
	public void test() {
		Graph<Integer> g = new Graph<Integer>();
		for(int i = 0; i < 5; i++)
			g.addNode(i);
		
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(0, 4);

		Integer[] expected = {1, 2, 4};
		Integer[] actual = new Integer[3];
		
		assertArrayEquals(expected, g.neighbours(0).toArray(actual));
	}

}
