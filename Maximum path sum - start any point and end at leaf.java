import java.io.*;
import java.util.*;

/*
Given a tree, return max path sum which path is part of any route from root -> leaf

    1 
 2,    5  
n, n, -4, -7  
return 1+2 = 2

      -1 
   1,      5  
-2, -3,  -3, -7  
return  5 + -3 = 2 


    1 
 2,    5  
n, n, 4, 7  
return 1 + 5 + 7 = 13 
*/


  class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val) {
      this.val = val;
    }
  }

class Solution {
  int maxValue = Integer.MIN_VALUE;
  
  public int findMaxPathValue(TreeNode root) {
      helper(root);
      return maxValue;
  }
  
  private int helper(TreeNode root) {
    if (root == null) {
      return Integer.MIN_VALUE;
    }
    if (root.left == null && root.right == null) {
      maxValue = Math.max(root.val, maxValue); //关键：这里记得要判断
      return root.val;
    }
    
    int left = helper(root.left);
    int right = helper(root.right);
    
    /* compare these 3 conditions with global return value：
    因为must end at leaf，所以比较：
    2, root + max(left, right)
    3, max(left, right)
    
    （当题目问可以start & end at ANY node时，要加上 1，just root比较）
    */
    int maxSubtreeValue = Math.max(left, right);
    int levelMax = Math.max(root.val + maxSubtreeValue, maxSubtreeValue);             maxValue = Math.max(levelMax, maxValue);
    
    // return at this step 
   // maxReturn = max{root.val, root.val + maxSubtreeValue}                 
    return root.val + maxSubtreeValue;
  }
  
  public static void main(String[] args) {
    Solution s = new Solution();
    // test for tree 
    TreeNode root = new TreeNode(-1);
    TreeNode left = new TreeNode(1);
    TreeNode right = new TreeNode(5);
     TreeNode ll = new TreeNode(-2);
    TreeNode lr = new TreeNode(-3);
    TreeNode rightLeft = new TreeNode(-3);
    TreeNode rightRight = new TreeNode(-7);
    root.left = left;
    root.right = right;
    left.left = ll;
    left.right = lr;
    right.left = rightLeft;
    right.right = rightRight;
    System.out.println("test tree: " + s.findMaxPathValue(root));
    
  }
  
}
