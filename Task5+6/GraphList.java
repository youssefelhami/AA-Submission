import java.io.*;
import java.util.*;


// We create a class for edges. Each edge has a source, destination and weight.
class Edge {
	 int source;
	 int destination;
	 int weight;
	 
	 public Edge(int source, int destination, int weight) {
		 this.source = source;
		 this.destination = destination;
		 this.weight = weight;
	 }
}


public class GraphList {
	// number of vertices
	private int V;
	
	// adjacency list: An array of linked lists of Edges
	private LinkedList<Edge> adj[];
	
	public GraphList(int v)
	 {
		 V = v;
		 // adj is an array of linked list of Edges for each Vertex
		 adj = new LinkedList[v];
		 for (int i = 0; i < v; ++i)
			 adj[i] = new LinkedList();
	 }
	
	public void addEdge(String source, String destination, int weight)
	 {
		// Transform Character to integer
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int sourceInt = alphaMap.indexOf(source); 
		int destInt = alphaMap.indexOf(destination); 
		
		// makes sure than the vertex name is in the array. 
		if (sourceInt > V-1 || destInt > V-1) {
			System.out.println("Insertion error: Invalid Vertex name");
		}
		
		
		// Creates edge
		Edge edge = new Edge(sourceInt, destInt, weight);
		
		// adds edge in the linked list of the source vertex.
		// This way every vertex is has all of its edges in the linked list.
		adj[sourceInt].add(edge);
		
	 }
	
	// Helper method to transform vertex id to its string when printing.
	static char numToString(int num) {
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return alphaMap.charAt(num);
	}
	
	
	public void printGraph() {
		// traverse every vertex
		for (int i =0; i< V; i++) {
			// Check for the edges in each vertex
			for (int j = 0; j<adj[i].size(); j++) {
				System.out.println("Vertex: " + numToString(i) + " is connected to " + 
									numToString(adj[i].get(j).destination) + " with weight "+ 
									adj[i].get(j).weight);
			}
		}
	}
	
	public void DFS(String root) {
		// array that checks if the vertices have been visited
		boolean visited[] = new boolean[V];
		Arrays.fill(visited, false);
		
		// Transform Character to integer
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int vertex = alphaMap.indexOf(root); 

		DFS(vertex, visited);
		
		System.out.print(" End \n");
	}
	
	private void DFS(int vertex, boolean visited[]) {
		// we mark the vertex as visited
		visited[vertex] = true;
		
		//print out the vertex
		System.out.print(numToString(vertex)+" -> ");
		
		//iterate over the edges connected to the vertex
		Iterator<Edge> i = adj[vertex].listIterator(); 
        while (i.hasNext()) 
        { 
            Edge edge = i.next(); 
            int n = edge.destination;
            // if the destination vertex hasn't been visited, call the function
            if (!visited[n]) 
                DFS(n, visited); 
        } 
	}
	
	public void BFS(String root) {
		// array that checks if the vertices have been visited
		boolean visited[] = new boolean[V];
		Arrays.fill(visited, false);
		
		// Transform Character to integer
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int vertex = alphaMap.indexOf(root); 
		
		// BFS queue (vertices to be visited)
        LinkedList<Integer> queue = new LinkedList<Integer>(); 
		
        // visit the root node
        visited[vertex] = true;
        queue.add(vertex);
        
        while(queue.size() != 0) {
        	vertex = queue.poll();
        	// print vertex
        	System.out.print(numToString(vertex)+" -> ");
        	//iterate in all the destinations and add them to the queue
        	Iterator<Edge> i = adj[vertex].listIterator();
        	while (i.hasNext()){ 
        		Edge edge = i.next(); 
                int n = edge.destination; 
                if (!visited[n]) { 
                    visited[n] = true; 
                    queue.add(n); 
                } 
            } 
        	
        }
        
        System.out.print(" End \n");
	}
	
	
	public static void main(String[] args) {
		GraphList graph = new GraphList(5);
		
		// Make sure that the names of the vertices are in alphabetical order.
		// For example in a graph of size 5, the Vertices can only have the
		// Characters from A to E as their names.
		
		graph.addEdge("A","B", 5);
		graph.addEdge("A","C", 2);
		graph.addEdge("B","D", 1);
		graph.addEdge("B","E", 7);
		graph.addEdge("C","E", 8);
		graph.addEdge("C","D", 5);
		graph.addEdge("D","E", 5);
		
		//graph.printGraph();
		
		graph.BFS("A");
	}

}
