Given a binary tree, return the postorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]

--------------------------Divide and Conquer--------------------------------------------------------------------------
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if(root == null) {
            return res;
        }
        res.addAll(postorderTraversal(root.left));
        res.addAll(postorderTraversal(root.right));
         res.add(root.val);
        return res;
    }
}
