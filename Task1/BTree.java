import java.util.*;
public class BTree {
	private Node root;

	public BTree() {
		root = null;
	}


	public void displayTree()
	{
		java.util.Stack<Node> globalStack = new java.util.Stack<Node>();
			globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
			System.out.println("......................................................");
		while(isRowEmpty==false){
			java.util.Stack<Node> localStack = new java.util.Stack<Node>();
			isRowEmpty = true;
			for(int j=0; j<nBlanks; j++)
				System.out.print(' ');
			while(globalStack.isEmpty()==false)
			{
				Node temp = globalStack.pop();
				if(temp != null)
				{
					System.out.print(temp.data);
					localStack.push(temp.left);
					localStack.push(temp.right);
					if(temp.left != null ||temp.right != null)   isRowEmpty = false;
				}
				else
				{
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j=0; j<nBlanks*2-2; j++)
				System.out.print(' ');
			} // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		} // end while isRowEmpty is false
		System.out.println("......................................................");
	}
	
	public BTree duplicate_tree() {
		BTree tree = new BTree();
		if (this.root == null)
			return tree;
		Node new_root = new Node(this.root.data);
		duplicate_tree(this.root, new_root);
	    tree.root = new_root;
	    return tree;
	}
	
	static private void duplicate_tree(Node originalNode, Node newNode) {
        if (originalNode == null) {
            return;
        }
        newNode.left = new Node(originalNode.data);
        if (originalNode.left != null) {
            newNode.left.left = new Node(originalNode.left.data);
            duplicate_tree(originalNode.left, newNode.left.left);
        }
        if (originalNode.right != null) {
            newNode.right = new Node(originalNode.right.data);
            duplicate_tree(originalNode.right, newNode.right);
        }

	}
	public void createExampleTree() {
		root = null;
		root = new Node(2);
		root.left = new Node(1);
		root.right = new Node(3);
		root.left.right = new Node(4);
	}
	public void createEmptyTree() {
		root = null;
	}
	
	
	public static void main(String args[])  //static method  
	{  
		BTree tree = new BTree(); 
		tree.createExampleTree();
		System.out.println("Tree before duplication");
		tree.displayTree();
		BTree new_tree = tree.duplicate_tree();
		System.out.println("Original Tree after duplication");
		tree.displayTree();
		System.out.println("Duplicated tree Tree after duplication");
		new_tree.displayTree();
	}  
	
}