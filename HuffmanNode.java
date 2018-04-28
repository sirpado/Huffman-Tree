package assign1;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanNode
{
	private final  int _freq;
	private char _ch;
	//private HuffmanNode parent;
	private HuffmanNode left;
	private HuffmanNode right;
	
	public HuffmanNode(final int freq, final char ch)
	{
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
	public static HuffmanNode BuildTree(int [] arr)
	{
		long startTime= System.currentTimeMillis();
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(arr.length,new HuffmanComperator());
		for (int i = 0; i < arr.length; i++)//o(n) Build tree from freq array
		{
			if (arr[i] != 0)//if freq is not 0
			{
				HuffmanNode hn = new HuffmanNode(arr[i],(char)i);//Add node with freq+char 
				q.add(hn);//o(logn) add to priority Q
			}
		}
		HuffmanNode root = null;
		while(q.size() >1)//o(n)
		{
			HuffmanNode first = q.poll();//o(logn)
			HuffmanNode second = q.poll();//o(logn)
			HuffmanNode newNode = new HuffmanNode(first._freq + second._freq,'`');
			newNode.left = first;
			newNode.right = second;
			root = newNode;
			q.add(newNode);//o(logn)
		}
		long endTime=System.currentTimeMillis();
		System.out.println("Building the tree took " + ((endTime - startTime)/1000) + " seconds");
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
 
    //create a code for every char in the tree.  
    public void buildDictionary (HashMap<Character, String> dictionery, String s)
    {

        if (this.left== null && this.right == null) {
            dictionery.put(this._ch, s); 
            return;
        }
 
        // if we go to left then add "0" to the code.
        // if we go to the right add"1" to the code.
 
        // recursive calls for left and
        // right sub-tree of the generated tree.
        this.left.buildDictionary(dictionery,s + "0");
        this.right.buildDictionary(dictionery,s + "1");
    }
    public HuffmanNode getLeft(){
    	return this.left;
    }
    public HuffmanNode getRight(){
    	return this.right;
    }

    public void setLeft(HuffmanNode tmp){this.left=tmp;}
    public void setRight(HuffmanNode tmp){this.right=tmp;}
    
    public String writeTree(HuffmanNode root,String _tree){
    	StringBuilder tree=new StringBuilder();
    	if(root.left==null && root.right==null){
    		tree.append('1');
    		StringBuilder temp = new StringBuilder(Integer.toBinaryString(root.get_char()));
    		while (temp.length() < 8) temp.insert(0, '0');
    		_tree+=tree;//For recursion
    		_tree+=temp;
    	}
    	else {_tree+='0';
    	if(root.getLeft()!=null)_tree=writeTree(root.left,_tree);
    	if(root.getRight()!=null) _tree=writeTree(root.right,_tree);}
    	return _tree;
    }
    public static HuffmanNode ReBuildTree(Queue<Character> charQ){
    	
    	HuffmanNode root=new HuffmanNode(0,'\0');
    	char tmp = charQ.poll();
    	StringBuilder tmpS=new StringBuilder();
    	if (tmp == '1'){ 
    		for(int i=0;i<8;i++) 
    			tmpS.append(charQ.poll());
    		root._ch=(char)Integer.parseInt(tmpS.toString(), 2);
    	}
    	else{
    		root.setLeft(ReBuildTree(charQ));
    		root.setRight(ReBuildTree(charQ));
    		}
    	return root;
    	
    };
    
    
}
