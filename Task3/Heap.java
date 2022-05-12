import java.util.Scanner;

public class Heap {
	// array of data
	private int[] data;
	// current size of heap
	private int size;
	// maximum size of heap
	private int maxSize;
	// root location in array (for simplification)
	private static final int FRONT = 1;
	
	//intializes heap with max size
	public Heap(int maxSize) {
		this.maxSize = maxSize;
		this.size = 0;
		data = new int[maxSize + 1];
		data[0] = Integer.MIN_VALUE;
	}
	
	private int parent(int pos) {return pos/2;}
	
	private int leftChild(int pos) {return 2 * pos;}
	
	private int rightChild(int pos) {return 2 * pos +1 ;}
	

	
	private void insert(int key) {
		// check if heap is full
		if (size >= maxSize) {
			System.out.println("Cannot insert data: Heap is full");
			return;
		}
		// insert data at the end of the heap
		data[FRONT + size] = key;
		size++;
		
		//check if data is smaller than parent and keep swapping.
		int pos = size;
		while(data[pos]< data[parent(pos)]) {
			swap(pos, parent(pos));
			pos = parent(pos);
		}
	}
	
	
	

	
	// Checks if data is larger than its children. 
	// Continues swapping until the data is consistent or it's a leaf
	private void heapify(int pos) {
		if (isLeaf(pos))
			return;
		int min;
		if (data[pos] > data[leftChild(pos)] || data[pos] > data[rightChild(pos)]) {
			min = minimum(leftChild(pos), rightChild(pos));
			swap(pos, min);
			heapify(min);
		}
	}
	
	//gets minimum between two positions
	private int minimum(int pos1, int pos2) {
		if (data[pos1] < data[pos2])
			return pos1;
		return pos2;
	}
	
	private boolean isLeaf(int pos) {
		if (pos > size/2 && pos <= FRONT+size)
			return true;
		return false;
	}
	
	private int pop() {
		//remove root of tree
		int key = data[FRONT];
		// add last element to the root of the tree.
		data[FRONT] = data[size];
		// with an element removed, the size is 1 less
		size--;
		// checks if children is smaller, if then keep swapping and repeat.
		heapify(FRONT);
		return key;
	}
	
	
	private void swap(int pos1, int pos2) {
		int temp = data[pos1];
		data[pos1] = data[pos2];
		data[pos2] = temp;
	}
	
	public int[] heapSort(int[] arr) {
		// adds elements in heap
		for (int i=0; i<arr.length; i++)
			insert(arr[i]);
		// pops heap root (minimum)
		for (int i = 0; i<arr.length; i++)
			arr[i] = pop();
		
		return arr;
	}
	
	public static int[] inputArray() {
		int size = 0;
		Scanner sc=new Scanner(System.in);  
		System.out.print("Enter the number of elements in your array: ");
		size=sc.nextInt();
		int[] array = new int[size];
		System.out.println("Enter "+ size +" Integer values: ");
		for (int i=0; i<size; i++) {
			array[i] = sc.nextInt();
		}
		return array;
	}
	
	
	public static void main(String[] args) {
		// example array
		//int [] arr = {90,87,65,11,23,45,21};
		int [] arr = inputArray();
		System.out.println("------------------ORIGINAL ARRAY------------------");
		for (int i=0; i<arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.print("\n");
		
		Heap heap = new Heap(arr.length);
		int[] sorted_arr = heap.heapSort(arr);
		
		System.out.println("------------------SORTED ARRAY------------------");

		for (int i=0; i<sorted_arr.length; i++)
			System.out.print(sorted_arr[i] + " ");
		System.out.print("\n");

	}

}
