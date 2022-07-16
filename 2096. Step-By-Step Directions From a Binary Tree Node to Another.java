You are given the root of a binary tree with n nodes. Each node is uniquely assigned a value from 1 to n. You are also given an integer startValue representing the value of the start node s, and a different integer destValue representing the value of the destination node t.

Find the shortest path starting from node s and ending at node t. Generate step-by-step directions of such path as a string consisting of only the uppercase letters 'L', 'R', and 'U'. Each letter indicates a specific direction:

'L' means to go from a node to its left child node.
'R' means to go from a node to its right child node.
'U' means to go from a node to its parent node.
Return the step-by-step directions of the shortest path from node s to node t.

 

Example 1:


Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
Output: "UURL"
Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.
Example 2:


Input: root = [2,1], startValue = 2, destValue = 1
Output: "L"
Explanation: The shortest path is: 2 → 1.
 

Constraints:

The number of nodes in the tree is n.
2 <= n <= 105
1 <= Node.val <= n
All the values in the tree are unique.
1 <= startValue, destValue <= n
startValue != destValue

------------------------------------ lca 的运用 ---------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/*
第一次做的code： https://github.com/riaing/leetcode/blob/master/Find%20path%20of%20a%20tree%20node.java 
*/
class Solution {
    public String getDirections(TreeNode root, int startValue, int destValue) {
        // 1, find lwa 
        TreeNode lwa = lowestCommonAncestor(root, startValue, destValue); 
        
        StringBuilder b = new StringBuilder();
        // 2. find the steps from start to lwa 
        int steps = stepsToRoot(lwa, startValue);
        for (int i = 0; i < steps; i++) {
            b.append('U');
        }
        // 3. find lwa to end's path 
        boolean destPath = findPath(lwa, destValue, b); 
        return b.toString();         
    }
    
    private TreeNode lowestCommonAncestor(TreeNode root, int p, int q) {
        if (root == null) {
            return null;
        }
        if (root.val == p || root.val == q) {
            return root; 
        }
        
        TreeNode checkLeft = lowestCommonAncestor(root.left, p, q);
        TreeNode checkRight = lowestCommonAncestor(root.right, p, q);
        if (checkLeft != null && checkRight != null) {
            return root; 
        }
        
        if (checkLeft != null) {
            return checkLeft;   
        }
        return checkRight;  
    }
    
    private int stepsToRoot(TreeNode root, int startValue) {  // 基础：1. 判断是否在树里， 2. 返回steps
        if (root == null) {
            return -1;  // 找不到就用-1 代表
        }
        if (root.val == startValue) {
            return 0;
        }
        
        // 因为要找在哪，后续遍历
        int leftSteps = stepsToRoot(root.left, startValue); 
        int rightSteps = stepsToRoot(root.right, startValue);      
        if (leftSteps == -1 && rightSteps == -1) {
            return -1; 
        }
        
        if (leftSteps == -1) {
            return 1 + rightSteps;
        }
        return 1 + leftSteps; 
    }
    
    private boolean findPath(TreeNode root, int destValue, StringBuilder b) { // 基础：1. 判断是否在树里， 2. 返回path
        if (root == null) {
            return false;
        }
        if (root.val == destValue) {
            return true;
        }
        
        b.append('L');
        boolean left = findPath(root.left, destValue, b);
        if (left) {
            return true; 
        }
        b.deleteCharAt(b.length() - 1); //一定要回溯

        
        b.append('R');
        boolean right = findPath(root.right, destValue, b);
        if (right) {
            return true;
        }
        b.deleteCharAt(b.length() - 1);
        return right; 
    }
}
