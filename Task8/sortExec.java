import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class ParallelMergeSort extends RecursiveAction{
	private int[] arr;
	
	public ParallelMergeSort(int[] arr) {
        this.arr = arr;
    }
	
	@Override
    public void compute() {
		// minimum length
		if (arr.length < 2) 
			return;
		int mid = arr.length / 2;
		
		//create copy of left array
		int[] left = new int[mid];
		System.arraycopy(arr, 0, left, 0, mid);
		
		//create copy of right array
		int[] right = new int[arr.length - mid];
	    System.arraycopy(arr, mid, right, 0, arr.length - mid);
	    
	    //Fork tasks
	    invokeAll(new ParallelMergeSort(left), new ParallelMergeSort(right));
	    
	    //merge arrays
	    merge(left, right);
	}
	
	private void merge(int[] left, int[] right) {
	    int leftIndex = 0, rightIndex = 0, sortedIndex = 0;
	    
	    //pick smallest element in arrays until one of them is fully used
	    while (leftIndex < left.length && rightIndex < right.length) {
	        if (left[leftIndex] < right[rightIndex])
	            arr[sortedIndex++] = left[leftIndex++];
	        else
	            arr[sortedIndex++] = right[rightIndex++];
	    }
	    
	    //if the right array is fully used, fill with rest of left array
	    while (leftIndex < left.length) {
	        arr[sortedIndex++] = left[leftIndex++];
	    }
	    //if the left array is fully used, fill with rest of right array 
	    while (rightIndex < right.length) {
	        arr[sortedIndex++] = right[rightIndex++];
	    }
	}
}




public class sortExec {
	
	public void sort(int[] arr) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new ParallelMergeSort(arr));
	}
	
	public static void main(String[] args) {
		
		System.out.println("-----------ORIGINAL ARRAY-----------");
		int arr [] = {2,4,1,6,3,7,2,8, 14, 22, 9, 10};
		for (int i = 0; i<arr.length; i++)
			System.out.print(arr[i]+ " ");
		System.out.print("\n");
		
		sortExec action = new sortExec();

		action.sort(arr);
		
		System.out.println("-----------ORIGINAL ARRAY-----------");
		for (int i = 0; i<arr.length; i++)
			System.out.print(arr[i]+ " ");
	}

}
