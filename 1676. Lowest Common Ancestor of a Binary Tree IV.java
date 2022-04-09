Given the root of a binary tree and an array of TreeNode objects nodes, return the lowest common ancestor (LCA) of all the nodes in nodes. All the nodes will exist in the tree, and all values of the tree's nodes are unique.

Extending the definition of LCA on Wikipedia: "The lowest common ancestor of n nodes p1, p2, ..., pn in a binary tree T is the lowest node that has every pi as a descendant (where we allow a node to be a descendant of itself) for every valid i". A descendant of a node x is a node y that is on the path from node x to some leaf node.

Example 1：
Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [4,7]
Output: 2
Explanation: The lowest common ancestor of nodes 4 and 7 is node 2.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [1]
Output: 1
Explanation: The lowest common ancestor of a single node is the node itself.

Example 3： 
Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [7,6,2,4]
Output: 5
Explanation: The lowest common ancestor of the nodes 7, 6, 2, and 4 is node 5.
 

Constraints:

The number of nodes in the tree is in the range [1, 104].
-109 <= Node.val <= 109
All Node.val are unique.
All nodes[i] will exist in the tree.
All nodes[i] are distinct.

--------------- 树求lca -----------------------------------
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        /*
        注意：这里两个重要条件：
        1. node必定在树上：否则要改logic
        2. 每个node value unique :否则不能用integer hashset
        */
        // 将列表转化成哈希集合，便于判断元素是否存在 
        Set<Integer> nodesValues = new HashSet<Integer>();
        for (TreeNode n : nodes) {
            nodesValues.add(n.val);
        }
        return helper(root, nodesValues);
    }
    
    private TreeNode helper(TreeNode root, Set<Integer> nodesValues) {
        if (root == null) {
            return null; 
        }
        
        if (nodesValues.contains(root.val)) {
            return root;
        }
        TreeNode left = helper(root.left, nodesValues);
        TreeNode right = helper(root.right, nodesValues);
        
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }
}
