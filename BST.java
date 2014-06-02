import java.util.Scanner;

public class BST {
	public Node ROOT = new Node(null,null,0); // init root.
	int indentShift = 4;
	
	// --> Node( Node leftChild , Node rightChild, int value)
	
	// Search for node.
	public boolean search(int value, Node node)
	{
		if(node == null) 
			return false;
		else if( node.value > value ) 
			return search(value, node.left);
		else if( node.value < value ) 
			return search(value, node.right);
		return true;
	}
	
	// Insert node.
	public void insert(int value, Node node)
	{
		if( node.value > value ) { 
			if( node.left == null ) node.left = new Node(null, null, value);
			else insert(value, node.left);
		} else if( node.value < value ) {
			if( node.right == null ) node.right = new Node(null, null, value);
			else insert(value, node.right);
		} else {
			System.out.println("This node already exist");
		}
	}
	
	public void printPostTree(Node node, int indent){
		if( node != null){
			if(node.left != null) 
				printPostTree(node.left, indent + indentShift);
			if(node.right != null) 
				printPostTree(node.right, indent + indentShift);
			if(indent > 0)
				for(int i = 0; i < indent; i++) System.out.print(" ");
			System.out.println(node.value);
		}
	}
	
	public BST(){
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter the root's value (ex:50 )");
		int root = reader.nextInt();
		ROOT.value = root;
		int newNodeVal = 0;
		while(newNodeVal != -1){
			System.out.println("Enter a new node value : ( -1 to exit )");
			newNodeVal = reader.nextInt();
			if(newNodeVal != -1) insert(newNodeVal, ROOT);
		}
		reader.close(); 
		printPostTree(ROOT, 0); // print tree.
	}
	
	public static void main(String[] args){
		new BST();
	}
}
