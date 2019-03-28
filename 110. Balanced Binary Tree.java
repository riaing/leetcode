Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth 
of the two subtrees of every node never differ by more than 1.

 To check if a tree is height-balanced, get the height of left and right subtrees. Return true if
 difference between heights is not more than 1 and left and right subtrees are balanced, otherwise return false.
 
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
  
  -------------best solutio: recursion with memorization-----------------------------------------------------------------
 Still based on DFS. Instead of calling depth() explicitly for each child node, we return the height of the current node in
 DFS recursion. 
Time:  each node in the tree only need to be accessed once. Thus the time complexity is O(N), better than the first solution.

public class Solution {
    class TreeInfo{   // helper class ,同时contain判断题目所需的所有infor
        boolean isBalanced; 
        int height; 
        public TreeInfo(int height , boolean isBalanced){
            this.isBalanced = isBalanced;
            this.height = height; 
        }
    }
    
    public boolean isBalanced(TreeNode root) {
        return helper(root).isBalanced;  //用一个type 为TreeInfo 的method
    }
    
    private TreeInfo helper(TreeNode root){
        if(root == null){
            return new TreeInfo(0, true);  //return的必须是一个new的object
        }
        TreeInfo leftTree = helper(root.left);  //recursion
        TreeInfo rightTree = helper(root.right); 
        
        int height = Math.max(leftTree.height, rightTree.height) +1; 
        boolean isBalanced = Math.abs(leftTree.height - rightTree.height)<=1 &&leftTree.isBalanced && rightTree.isBalanced; 
        // 计算两个返回值 
        
        return  new TreeInfo(height, isBalanced);  
    }   
} 

 -----------------first solution. based on definition approach -------------------------------------------------------------
 Time: o(n^2) 
 For the current node root, calling depth() for its left and right children actually has to access all of its children, 
thus the complexity is O(N). We do this for each node in the tree, so the overall complexity of isBalanced will be O(N^2), because 
height is n when the tree is skewed. 
  
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
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isBalanced(root.left) && isBalanced(root.right) && Math.abs(height(root.left) - height(root.right)) <= 1;
        
    }
    
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    
}
