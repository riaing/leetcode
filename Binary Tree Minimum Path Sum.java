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
    int min; 
    public int maxPathSum(TreeNode root) {
        min = Integer.MAX_VALUE;
    
        findMinSumCrossRoot(root);
        return min;
    }
    
    private int findMinSumCrossRoot(TreeNode root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
   
        int leftSum = Math.min(findMinSumCrossRoot(root.left), 0);
        int rightSum = Math.min(findMinSumCrossRoot(root.right), 0);
        
        int crossSum = leftSum + rightSum + root.val;
        if (min > crossSum) {
            min = crossSum;
        }
     
        int res = Math.min(leftSum, rightSum) + root.val;

        return res;
        
    }
}
