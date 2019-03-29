Consider all the leaves of a binary tree.  From left to right order, the values of those leaves form a leaf value sequence.



For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.
-----------------------------------DFS解（也可以BFS遍历到最后一层）---------------------------------------------------------------------
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
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> left = leaveNodes(root1);
        List<Integer> right = leaveNodes(root2);
        return left.equals(right);
    }
    
    private List<Integer> leaveNodes(TreeNode node) {
        List<Integer> res = new ArrayList<Integer>();
        if (node == null) {
            return res;
        }
        else {
            if (node.left == null && node.right == null) {
                res.add(node.val);
            }
            res.addAll(leaveNodes(node.left));
            res.addAll(leaveNodes(node.right));
        }
        return res; 
    }
    
}
