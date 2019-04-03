Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Example 1:

Input:
    2
   / \
  1   3
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.


----------- 利用bst的性质，inorder traversal树，判断结果是不是increasing order----------------------------------------------


-----------------------------------------D & C w/ memorization O(n) ----------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/*
left's max must < root && right's min must > root  && left is valid && right is valid 
*/
class Solution {
    class TreeInfo {
        int max; 
        int min;
        boolean valid;
        
        public TreeInfo(int max, int min, boolean valid) {
            this.max = max;
            this.min = min;
            this.valid = valid;
        }
    }
    
    public boolean isValidBST(TreeNode root) {
        TreeInfo info =  validatedBST(root);
        return info.valid;
    }
    
    private TreeInfo validatedBST(TreeNode root) {
        if (root == null) {
            return new TreeInfo(Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }
        
        TreeInfo left = validatedBST(root.left);
        TreeInfo right = validatedBST(root.right);
        if (root.val == 1) {
            System.out.println(right.min);
        }
        
        int min = Math.min(root.val, Math.min(left.min,right.min));
        int max = Math.max(root.val, Math.max(left.max, right.max));
        
        // (root.left == null || root.val > left.max) 这里判断root.left == null 是为了corner case: 当叶子节点值为 Integer.MIN_VALUE，root.val > left.max会报错。所以我们skip 这步判断when是leave时。
        // 另外两种处理方法：1，给TreeInfo 加个filed check if 为 null。2， 将 root == null时的max/min改为long，再分别-1/+1
        boolean valid = (root.left == null || root.val > left.max) && (root.right == null || root.val < right.min) && left.valid && right.valid;
        return new TreeInfo(max, min, valid);
    }
}
