https://leetcode.com/problems/binary-tree-pruning/ 
We are given the head node root of a binary tree, where additionally every node's value is either a 0 or a 1.

Return the same tree where every subtree (of the given tree) not containing a 1 has been removed.

(Recall that the subtree of a node X is X, plus every node that is a descendant of X.)

Example 1:
Input: [1,null,0,0,1]
Output: [1,null,0,null,1]
 
Explanation: 
Only the red nodes satisfy the property "every subtree not containing a 1".
The diagram on the right represents the answer.


Example 2:
Input: [1,0,1,0,0,0,1]
Output: [1,null,1,null,1]



Example 3:
Input: [1,1,0,1,1,0,1,0]
Output: [1,1,0,1,1,null,1]



Note:

The binary tree will have at most 100 nodes.
The value of each node will only be 0 or 1.



Time Complexity: O(N)O(N), where NN is the number of nodes in the tree. We process each node once.

Space Complexity: O(H)O(H), where HH is the height of the tree. This represents the size of the implicit call stack in our recursion.
----------------------自己想出来的recursive写法，不推荐，因为咩有充分利用pruneTree来进行D&C-----------------------------------------
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
    public TreeNode pruneTree(TreeNode root) {
        recurse(root);
        return root;
    }
    
    private void recurse(TreeNode root) {
         if (root == null) {
            return;
        }
        // must prune first 
        pruneTree(root.left);
        pruneTree(root.right);
        
        // for the case of [0]
        if (root.left == null && root.right == null && root.val == 0) {
            root = null;
            return;
        }
        
        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                if (root.left.val == 0) {
                    root.left = null;
                }
            } 
        }
        
        if (root.right != null) {
            if (root.right.left == null && root.right.right == null) {
                if (root.right.val == 0) {
                    root.right = null;
                }
            } 
        }
    }
}

------------------D & C ---------------------------------------------------------------------------------------------
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
    public TreeNode pruneTree(TreeNode root) {
       if (root == null) {
           return root;
       }
        // 都必须先prune，prune完了后再处理
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        
        if (root.left == null && root.right == null && root.val == 0) {
            return null;
        }
       
        return root;
    }
}
