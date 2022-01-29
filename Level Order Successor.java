Given a binary tree and a node, find the level order successor of the given node in the tree. The level order successor is the node that appears right after the given node in the level order traversal.

Example 1:

 3  
 4  
 Given Node:  
 Level Order Successor:  
    1   
    2   
    3   
    4   
    5   
Example 2:

 Given Node:  
 9  
 Level Order Successor:  
 10  
    12   
    7   
    1   
    9   
    10   
    5   
Example 3:

 Given Node:  
 Level Order Successor:  
 12  
 7  
    12   
    7   
    1   
    9   
    10   
    5   
    
    
----------------- BFS， 不需要记录层数 -----------------------------

import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
};

class LevelOrderSuccessor {
  public static TreeNode findSuccessor(TreeNode root, int key) {
     if (root == null) {
            return null;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (q.size() != 0) {
            TreeNode cur = q.poll();
            // 先加入后面的 node
            if (cur.left != null){
                q.offer(cur.left);
            }
            if (cur.right != null) {
                q.offer(cur.right);
            }

            if (cur.val == key) {
                break;
            }

        }
        return q.size() == 0 ? null : q.peek(); //有可能 key 不在
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(12);
    root.left = new TreeNode(7);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(9);
    root.right.left = new TreeNode(10);
    root.right.right = new TreeNode(5);
    TreeNode result = LevelOrderSuccessor.findSuccessor(root, 12);
    if (result != null)
      System.out.println(result.val + " ");
    result = LevelOrderSuccessor.findSuccessor(root, 9);
    if (result != null)
      System.out.println(result.val + " ");
  }
}

