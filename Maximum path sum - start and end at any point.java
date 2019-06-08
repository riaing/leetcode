求 max path sum
import java.io.*;
import java.util.*;

/*
Given a tree, return max path sum which path is define as a route start from any node and end at any node 
    1 
 2,    5  
n, n, -4, -7  
return 1+5

    -1 
 2,    5  
n, n, -4, -7  
return 5

    -1 
 2,    5  
n, n, 4, 7  
return 5 + 7
*/


  class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val) {
      this.val = val;
    }
  }

  class Node {
    int val;
    int max;
    
    public Node(int val, int max) {
      this.val = val;
      this.max = max; 
    }
  }

class Solution {
  int maxValue = Integer.MIN_VALUE;
  
  public int findMaxPathValue(TreeNode root) {
      helper(root);
      return maxValue;
  }
  
  private Node helper(TreeNode root) {
    if (root == null) {
      return new Node(0, Integer.MIN_VALUE);
    }
    if (root.left == null && root.right == null) {
      return new Node(root.val, root.val);
    }
    Node left = helper(root.left);
    Node right = helper(root.right);
    
    /* compare these 3 conditions with global return value： 这一步反应题意，可以是任意点开始任意点结束
    1, just root
    2, root + max(left, right)
    3, max(left, right)
    */
    int maxSubtreeValue = Math.max(left.max, right.max);
    int levelMax = Math.max(root.val + maxSubtreeValue, Math.max(root.val, maxSubtreeValue));
                          
    maxValue = Math.max(levelMax, maxValue);
    

    // return at this step 
   // maxReturn = max{root.val, root.val + maxSubtreeValue}                 
    return new Node(root.val, Math.max(root.val, (root.val + maxSubtreeValue)));
  }
  
  public static void main(String[] args) {
    Solution s = new Solution();
    // test for tree 
    TreeNode root = new TreeNode(-1);
    TreeNode left = new TreeNode(2);
    TreeNode right = new TreeNode(5);
    TreeNode rightLeft = new TreeNode(4);
    TreeNode rightRight = new TreeNode(7);
    root.left = left;
    root.right = right;
    right.left = rightLeft;
    right.right = rightRight;
    System.out.println("test tree: " + s.findMaxPathValue(root));
    
  }
  
}
