// package whatever; // don't place package name!

import java.io.*;
import java.util.*;


// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
}

class Info {
  public int number;
  public boolean splitable;

  public Info(int number, boolean splitable) {
    this.number = number;
    this.splitable = splitable;
  }
}

// contains the parent node and the index of children of where to split
class SplitInfo {
  public Node parent;
  public int index;
  public SplitInfo(Node parent, int index) {
    this.parent = parent;
    this.index = index;
  }
}


class MyCode {

  public static List<Node> solution(Node root) {
    List<Node> result = new ArrayList<Node>();
    List<SplitInfo> potentialSplits = new ArrayList<SplitInfo>();
    Info rootInfo = helper(root, potentialSplits);
    if (rootInfo.splitable) {
      result.add(root);
      for(SplitInfo splitInfo : potentialSplits) {
        int index = splitInfo.index;
        // 1, add to result 
        result.add(splitInfo.parent.children.get(index));
        // 2, split the real tree 
        splitInfo.parent.children.set(index, null);
      }
    }

    return result;
  }

  private static Info helper(Node root, List<SplitInfo> potentialSplits) {
    if (root == null) {
      return new Info(0, false);
    }
    int numOfNodes = 1;
    if (root.children != null) {
      for (int i = 0; i < root.children.size(); i++) {
        Info curNodeInfo = helper(root.children.get(i), potentialSplits);
        numOfNodes += curNodeInfo.number;
        if (curNodeInfo.splitable) {
          SplitInfo potential = new SplitInfo(root, i);
          potentialSplits.add(potential);
        }
      }
    }

    if (numOfNodes % 2 == 0) {
      return new Info(numOfNodes, true);
    }
    else {
      return new Info(numOfNodes, false);
    }
  }

  //Test 
  private static Node construct1() {
  /*
    0

  1 2 3 
  */
    List<Node> children = new ArrayList<Node>();
    Node c1 = new Node(1, null);
    Node c2 = new Node(2, null);
    Node c3 = new Node(3, null);
    children.add(c1);
    children.add(c2);
    children.add(c3);
		Node root = new Node(0, children);
    return root;
  }

  private static Node construct2() {
      /*
    0

  1   2 
        3 
  */  
    List<Node> c2Children = new ArrayList<Node>();
    Node c3 = new Node(3, null);
    c2Children.add(c3);
    Node c2 = new Node(2, c2Children);

    List<Node> children = new ArrayList<Node>();  
    Node c1 = new Node(1, null);
    children.add(c1);
    children.add(c2);
		Node root = new Node(0, children);
    return root;
  }

 private static Node construct3() {
    /*
   4
   */ 

  Node root = new Node(4, null);
  return root;
 }

  private static Node construct4() {
      /*
    0
  
   1  2 
 4      3 
  */  
    List<Node> c2Children = new ArrayList<Node>();
    Node c3 = new Node(3, null);
    c2Children.add(c3);
    Node c2 = new Node(2, c2Children);

     List<Node> c1Children = new ArrayList<Node>();
    Node c4 = new Node(4, null);
    c1Children.add(c4);
    Node c1 = new Node(1, c1Children);

    List<Node> children = new ArrayList<Node>();  
    children.add(c1);
    children.add(c2);
		Node root = new Node(0, children);
    return root;
  }

   private static Node construct5() {
      /*
    -1

    0
  
   1  2 
 4      3 
  */  
    List<Node> c2Children = new ArrayList<Node>();
    Node c3 = new Node(3, null);
    c2Children.add(c3);
    Node c2 = new Node(2, c2Children);

     List<Node> c1Children = new ArrayList<Node>();
    Node c4 = new Node(4, null);
    c1Children.add(c4);
    Node c1 = new Node(1, c1Children);

    List<Node> c0children = new ArrayList<Node>();  
    c0children.add(c1);
    c0children.add(c2);
		Node c0 = new Node(0, c0children);

    List<Node> children = new ArrayList<Node>();  
    children.add(c0);
    Node root = new Node(-1, children);
    return root;
  }


   private static Node construct6() {
      /*
  
      0
  1   2   3 
        4    5 
  */  
    List<Node> c3Children = new ArrayList<Node>();
    Node c4 = new Node(4, null);
    c3Children.add(c4);
    Node c5 = new Node(5, null);
    c3Children.add(c5);
    Node c3 = new Node(3, c3Children);


    Node c2 = new Node(2, null);
    Node c1 = new Node(1, null);


    List<Node> c0children = new ArrayList<Node>();  
    c0children.add(c1);
    c0children.add(c2);
    c0children.add(c3);
		Node c0 = new Node(0, c0children);
    return c0;
  }

  private static Node construct7() {
      /*
  
       0
   1   2   3 
4             5 
  */  
    List<Node> c3Children = new ArrayList<Node>();
    Node c5 = new Node(5, null);
    c3Children.add(c5);
    Node c3 = new Node(3, c3Children);


    List<Node> c1Children = new ArrayList<Node>();
    Node c4 = new Node(4, null);
    c1Children.add(c4);
    Node c1 = new Node(1, c1Children);
    
    List<Node> c0children = new ArrayList<Node>();  
    c0children.add(c1);
    Node c2 = new Node(2, null);
    c0children.add(c2);
    c0children.add(c3);
		Node c0 = new Node(0, c0children);
    return c0;
  }


	public static void main (String[] args) {

    List<Node> res = solution(construct7());
    System.out.println(res);
    for (Node r : res) {
      System.out.println("root: " + r.val);
      List<Node> c = r.children;
      System.out.println("childrenSize " + c.size());
      for (Node curC : c) {
        if (curC == null) {
          System.out.println("null");
        }
        else {  
          System.out.println("children: " + curC.val);
        }
        
      }
    }
	}
}





