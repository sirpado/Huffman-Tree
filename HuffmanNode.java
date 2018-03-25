package assign1;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanNode implements Comparator<HuffmanNode>
{
	private final  int _freq;
	private final char _ch;
	//private HuffmanNode parent;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public HuffmanNode(final int freq, final char ch)
	{
		// TODO Auto-generated constructor stub
		_freq = freq;
		_ch = ch;
	}
	
	public int get_freq()
	{
		return _freq;
	}
	
	public char get_char()
	{
		return _ch;
	}
	public HuffmanNode BuildTree(int [] arr)
	{
		PriorityQueue<HuffmanNode> q = new PriorityQueue(arr.length,new HuffmanComperator());
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] != 0)
			{
				HuffmanNode hn = new HuffmanNode(arr[i],(char)i);
				q.add(hn);
			}
		}
		HuffmanNode root = null;
		while(q.size() >1)
		{
			HuffmanNode first = q.poll();
			HuffmanNode second = q.poll();
			HuffmanNode newNode = new HuffmanNode(first._freq + second._freq,'`');
			newNode.setLeft(first);
			newNode.setRight(second);
			root = newNode;
			q.add(newNode);
		}
		printCode(root, "");
		return root;
	}
    public static void printCode(HuffmanNode root, String s)
    {
 
        // base case; if the left and right are null
        // then its a leaf node and we print
        // the code s generated by traversing the tree.
        if (root.left
                == null
            && root.right
                   == null
            ) {
 
            // c is the character in the node
            System.out.println(root._ch + ":" + s);
 
            return;
        }
 
        // if we go to left then add "0" to the code.
        // if we go to the right add"1" to the code.
 
        // recursive calls for left and
        // right sub-tree of the generated tree.
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }
/*	public HuffmanNode getParent()
	{
		return this.parent;
	}
	*/
	public HuffmanNode getLeft()
	{
		return this.left;
	}
	
	public HuffmanNode getRight()
	{
		return this.right;
	}
	
	/*public void setParent(HuffmanNode parent)
	{
		this.parent = parent;
	}
	*/
	public void setLeft(HuffmanNode left)
	{
		this.left = left;
	}
	
	public void setRight(HuffmanNode right)
	{
		this.right = right;
	}

	@Override
	public int compare(HuffmanNode arg0, HuffmanNode arg1)
	{
		return arg0._freq-arg1._freq;
	}
}
