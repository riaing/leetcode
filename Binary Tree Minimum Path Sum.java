类似lt 125，但是找出minimum path sum： 
https://cloud.githubusercontent.com/assets/9201514/22659169/a0c693b6-ec6a-11e6-8642-2683bc89566c.png 这个eg中应该返回-10
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
