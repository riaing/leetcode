Given a non-empty special binary tree consisting of nodes with the non-negative value, where each node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this node's value is the smaller value among its two sub-nodes.

Given such a binary tree, you need to output the second minimum value in the set made of all the nodes' value in the whole tree.

If no such second minimum value exists, output -1 instead.

Example 1:

Input: 
    2
   / \
  2   5
     / \
    5   7

Output: 5
Explanation: The smallest value is 2, the second smallest value is 5.
 

Example 2:

Input: 
    2
   / \
  2   2

Output: -1
Explanation: The smallest value is 2, but there isn't any second smallest value.

----------worst case O(n), but only when left and right tree has the sama value as root ------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
 public int findSecondMinimumValue(TreeNode root) {
      if (root == null || (root.left == null && root.right == null)) {
        return -1;
      }
      
      if (root.left.val == root.val && root.right.val != root.val) {
        int secondMinVal = findSecondMinimumValue(root.left); 
        return Math.min(secondMinVal == -1 ? Integer.MAX_VALUE : secondMinVal, root.right.val);
      }

      else if (root.right.val == root.val && root.left.val != root.val) {
        int secondMinVal = findSecondMinimumValue(root.right); 
        return Math.min(secondMinVal == -1 ? Integer.MAX_VALUE : secondMinVal, root.left.val);
      }
     // tree like [2],[2,2], [2,3, null, null] -> should return 3 
      else {
        int leftSecondMinVal = findSecondMinimumValue(root.left);
        int rightSecondMinVal = findSecondMinimumValue(root.right); 
        if (leftSecondMinVal == -1 && rightSecondMinVal == -1) {
          return -1; 
        }
        else if (leftSecondMinVal == -1) {
          return rightSecondMinVal;
        }
        else if (rightSecondMinVal == -1) {
          return leftSecondMinVal;
        }
        else {
          return Math.min(leftSecondMinVal, rightSecondMinVal);
        }
        
      }
      
      
        
    }
}
