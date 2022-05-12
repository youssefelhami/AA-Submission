public class Node
{
	public int data;
	public Node right;
	public Node left;
	
	public Node(int data)
	{
		this(data,null,null);	
	}
	
	public Node(int data, Node left, Node right)
	{
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
}
