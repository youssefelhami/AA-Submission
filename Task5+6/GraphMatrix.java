import java.util.Arrays;

public class GraphMatrix {
	// number of vertices
	int V;
	// 2D matrix of size v*v
	double adjMatrix[][];
	
	// Initialize v and matrix
	GraphMatrix(int v){
		V = v;
		adjMatrix = new double[v][v];
		for (double[] row: adjMatrix)
		    Arrays.fill(row, Double.POSITIVE_INFINITY);
	}
	
	void addEdge(String source, String destination, int weight) {
		// Transform Character to integer
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int sourceInt = alphaMap.indexOf(source); 
		int destInt = alphaMap.indexOf(destination); 
		
		// makes sure than the vertex name is in the array. 
		if (sourceInt > V-1 || destInt > V-1) {
			System.out.println("Insertion error: Invalid Vertex name");
		}
		// adds the weight to the matrix
		adjMatrix[sourceInt][destInt] = weight;
	}
	
	// Helper method to transform vertex id to its string when printing.
	static char numToString(int num) {
		String alphaMap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return alphaMap.charAt(num);
	}
	
	void printGraph() {
		// traverse every vertex
		for (int i=0; i< V; i++) {
			// traverse every other vertex
			for (int j = 0; j<V; j++) {
				// if there is an edge the weight can't be infinite
				if(adjMatrix[i][j] != Double.POSITIVE_INFINITY) {
					System.out.println("Vertex: " + numToString(i) + " is connected to " + 
							numToString(j) + " with weight "+ adjMatrix[i][j]);
				}
				
			}
		}
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
		
		graph.printGraph();
	}
}
