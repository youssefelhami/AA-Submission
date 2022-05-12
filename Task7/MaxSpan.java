import java.util.*;

class adjMatrix {
    // number of vertices
	int V;
    int adjMatrix[][];

    adjMatrix(int size) {
        V = size;
        adjMatrix = new int[size][size];
        // Matrix starts with -1 values (no connection)
        for (int[] row: adjMatrix)
		    Arrays.fill(row, -1);
    }

    void addEdge(int src, int dest, int weight) {
        
    	// the weight must be positive
    	if(weight < 0) {
    		System.out.println("Invalid Weight Value");
    		return;
    	}
    	
    	this.adjMatrix[src][dest] = weight;
        this.adjMatrix[dest][src] = weight;

    }
    // Creating example in CW
    void createExampleCW() {
    	V = 6;
    	adjMatrix = new int[6][6];
    	addEdge(0, 1, 7);
        addEdge(0, 3, 8);
        addEdge(1, 3, 3);
        addEdge(1, 2, 6);
        addEdge(2, 3, 4);
        addEdge(2, 4, 2);
        addEdge(2, 5, 5);
        addEdge(3, 4, 3);
        addEdge(4, 5, 2);
        // 0-> S, 1 -> A, 2-> B, 3-> C, 4 -> D, 5 -> T
    }
}

public class MaxSpan {
	
	adjMatrix adj;
	boolean[] visited;
	int[] parent;
	
	
	MaxSpan(adjMatrix adj){
		this.adj = adj;
		visited = new boolean[adj.V];
		Arrays.fill(visited, false);
		parent = new int[adj.V];
	}
	

	int findMax(int weight[])
	{
		int index = -1;
		// find maximum value in weight in unvisted vertices
		int max = Integer.MIN_VALUE;
		for(int i=0; i< adj.V; i++) {
			if (visited[i] == false && weight[i]>max) {
				max = weight[i];
				index = i;
			}
		}
		
		return index;
	}
	
	void executePrim() {
		int[] weights = new int[adj.V];
		Arrays.fill(weights, Integer.MIN_VALUE);
		
		
		// Initialize Root
		weights[0] = Integer.MAX_VALUE;
	    parent[0] = -1;
	    
		for (int i = 0; i < adj.V - 1; i++) {
			// find maximum
			int maxVertex = findMax(weights);
			// mark as visited
			visited[maxVertex] = true;
			//adjusts the parent and weights from new data
			for (int j = 0; j< adj.V; j++) {
				if (adj.adjMatrix[j][maxVertex] > 0 && visited[j] == false) {
					if(adj.adjMatrix[j][maxVertex]>weights[j]) {
						weights[j] = adj.adjMatrix[j][maxVertex];
						parent[j] = maxVertex;
					}
				}
			}
		}
		printSpanTree();
	}
	
	void printSpanTree() {
		for (int i=1; i<adj.V; i++) {
			System.out.println("Node " + i + " is connected to node " + parent[i] + 
								" with weight " + adj.adjMatrix[i][parent[i]]);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		adjMatrix graph = new adjMatrix(6);
		graph.createExampleCW();
		MaxSpan tree = new MaxSpan(graph);
		tree.executePrim();
	}

}
