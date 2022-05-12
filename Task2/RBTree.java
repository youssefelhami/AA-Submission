
public class RBTree {
	static final int RED = 1;
	static final int BLACK = 0;
	private Node root;
	private Node TNULL;
	public RBTree(){
		TNULL = new Node();
		TNULL.color = BLACK;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
	}
	// 0 -> black, 1-> red
	public void insert(int key) {
		// Create Node
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = RED;
		Node y = null;
		Node x = this.root;
		// Traverse the tree to the left or right depending on the key
		while (x != TNULL) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		// Insert Node
		node.parent = y;
		if (y == null) {
			 root = node;
		} else if (node.data < y.data) {
			 y.left = node;
		} else {
			 y.right = node;
		}
		if (node.parent == null) {
			 node.color = BLACK;
			 return;
		}
		// there is no need to balance anything when the parent is the root.
		if (node.parent.parent == null) {
			 return;
		}
		//balance node after insertion
		fixInsert(node);
	}
	
	private void fixInsert(Node node) {
		Node uncle;
		// if the parent is black then the third property is satisfied.
		while (node.parent.color == RED) {
			// get uncle of node
			uncle = getUncle(node);
			// Case where parent is red and uncle is red
			if (uncle.color == RED) {
				uncle.color = BLACK;
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				node = node.parent.parent;
			}
			// Case when parent is red and uncle is black
			else {
				// if the parent is right child
				if (node.parent == node.parent.parent.right) {
					// if node is left child
					if (node == node.parent.left) {
						node = node.parent;
						rotateRight(node);
					}
					node.parent.color = BLACK;
					node.parent.parent.color = RED;
					rotateLeft(node.parent.parent);
				}
				// if parent is left child
				else {
					// if node is right child
					if (node == node.parent.right) {
						node = node.parent;
						rotateLeft(node);
					}
					node.parent.color = BLACK;
					node.parent.parent.color = RED;
					rotateRight(node.parent.parent);
				}
			}
			// If we traverse the tree
			if (node == root)
				break;
		}
		// making sure root is always black
		root.color = BLACK;
	}
	
	
	private Node getUncle(Node node) {
		Node parent = node.parent;
		Node grandparent = parent.parent;
		if (grandparent.left == parent) {
		    return grandparent.right;
		}
		else {
		    return grandparent.left;
		}
	}
	
	private void rotateRight(Node node){
		Node leftChild = node.left;
		// right child of left child become left child
		node.left = leftChild.right;
		// connect parent
		if (leftChild.right != TNULL) {
			leftChild.right.parent = node;
		}
		leftChild.parent = node.parent;
		// if node was root, if node is right child or left child
		if (node.parent == null) {
			this.root = leftChild;
		} else if (node == node.parent.right) {
			node.parent.right = leftChild;
		} else {
			node.parent.left = leftChild;
		}
		leftChild.right = node;
		node.parent = leftChild;
	}
	
	private void rotateLeft(Node node){
		Node rightChild = node.right;
		// left child of right child become right child
		node.right = rightChild.left;
		// connect parent
		if (rightChild.left != TNULL) {
			rightChild.left.parent = node;
		}
		rightChild.parent = node.parent;
		// if node was root, if node is right child or left child
		if (node.parent == null) {
			this.root = rightChild;
		} else if (node == node.parent.left) {
			node.parent.left = rightChild;
		} else {
			node.parent.right = rightChild;
		}
		rightChild.left = node;
		node.parent = rightChild;
	}
	private void printHelper(Node root, String indent, boolean last) {
		 if (root != TNULL) {
			 System.out.print(indent);
			 if (last) {
				 System.out.print("R----");
				 indent += " ";
		 } else {
			 System.out.print("L----");
			 indent += "| ";
		 }
		 String sColor = root.color == 1 ? "RED" : "BLACK";
		 System.out.println(root.data + "(" + sColor + ")");
		 printHelper(root.left, indent, false);
		 printHelper(root.right, indent, true);
		 }
	}
	
	public void displayTree() {
		printHelper(this.root, "", true);
	}

	public void deleteNode(int key) {
		Node node = root;
		Node nodeToDelete = TNULL;
		// find node
		while (node != TNULL) {
			if (node.data == key) {
				nodeToDelete = node;
				break;
			}
			if (node.data <= key) {
				node = node.right;
			} else {
				node = node.left;
			}
		}
		if (nodeToDelete == TNULL) {
			System.out.println("Key doesn't exist in tree");
			return;
		}
		
		Node copyNode = nodeToDelete;
		Node movedUpNode;
		int deletedColor = nodeToDelete.color;
		// no left child
		if (nodeToDelete.left == TNULL) {
			movedUpNode = nodeToDelete.right;
			replaceNode(nodeToDelete, nodeToDelete.right);
		} // no right child
		else if (nodeToDelete.right == TNULL) {
			movedUpNode = nodeToDelete.left;
			replaceNode(nodeToDelete, nodeToDelete.left);
		} // both red and right child
		else {
			copyNode = getMinimum(nodeToDelete.right);
			// we only copy the data so that the color remains the same
			nodeToDelete.data = copyNode.data;
			// the node actually deleted is the mimimum node which means
			// that we only care about its color not the color of the original
			// node
			deletedColor = copyNode.color;
			movedUpNode = copyNode.right;
			replaceNode(copyNode, copyNode.right);
		}
		if (deletedColor == BLACK) {
			fixDelete(movedUpNode);
		}
	}
	
	// replace an old node with new node
	private void replaceNode(Node oldNode, Node newNode) {
		if(oldNode.parent == null) {
			root = newNode;
		} else if (oldNode == oldNode.parent.left) {
			oldNode.parent.left = newNode;
		} else {
			oldNode.parent.right = newNode;
		}
		newNode.parent = oldNode.parent;
	}
	
	// we only need to traverse the left nodes to find the minimum
	private Node getMinimum(Node node) {
		while (node.left != TNULL)
			node = node.left;
		return node;
	}
	
	private void fixDelete(Node node) {
		Node sibling;
		// node is black and we haven't traversed the root
		while (node != root && node.color == BLACK) {
			sibling = getSibling(node);
			// node is left child
			if (node == node.parent.left)
			{
				// Case sibling is red
				if (sibling.color == RED) {
					node.parent.color = RED;
					sibling.color = BLACK;
					rotateLeft(node.parent);
					sibling = node.parent.right;
				}
				// case both sibling's children are black
				if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
					sibling.color = RED;
					node = node.parent;
				} // case one of the children is red
				else {
					// make sure that correct sibling child is black to find balance
					if (sibling.right.color == BLACK) {
						sibling.left.color = BLACK;
						sibling.color = RED;
						rotateRight(sibling);
						sibling = node.parent.right;
					}
					// rotate and recolor black
					sibling.color = node.parent.color;
					node.parent.color = BLACK;
					sibling.right.color = BLACK;
					rotateLeft(node.parent);
					node = root;
				}
			
			} // node is right child
			else {
				if (sibling.color == RED) {
					node.parent.color = RED;
					sibling.color = BLACK;
					rotateRight(node.parent);
					sibling = node.parent.left;
				}
				if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
					sibling.color = RED;
					node = node.parent;
				} else {
					// make sure that correct sibling child is black to find balance
					if (sibling.left.color == BLACK) {
						sibling.right.color = BLACK;
						sibling.color = RED;
						rotateLeft(sibling);
						sibling = node.parent.left;
					}
					// rotate and recolor black
					sibling.color = node.parent.color;
					node.parent.color = BLACK;
					sibling.left.color = BLACK;
					rotateRight(node.parent);
					node = root;
				}
			}
			
			node.color = BLACK;
			
		}
	}
	
	private Node getSibling(Node node) {
		Node parent = node.parent;
		if (node == parent.left) {
			return parent.right;
		}
		return parent.left;
	}
	
	
	public static void main(String[] args) {
		RBTree tree = new RBTree();
		int [] arr = {30, 15, 45, 35, 60, 55};
		System.out.println("-----------INSERT------------");
		for (int i=0; i<arr.length; i++) {
			tree.insert(arr[i]);
			tree.displayTree();
			System.out.println("--------------------------------");
		}
		
		System.out.println("-----------DELETE------------");
		tree.deleteNode(45);
		tree.displayTree();
	}
}
