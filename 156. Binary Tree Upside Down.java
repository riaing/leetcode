Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.

Example:

Input: [1,2,3,4,5]

    1
   / \
  2   3
 / \
4   5

Output: return the root of the binary tree [4,5,2,#,#,3,1]

   4
  / \
 5   2
    / \
   3   1  
Clarification:

Confused what [4,5,2,#,#,3,1] means? Read more below on how binary tree is serialized on OJ.

The serialization of a binary tree follows a level order traversal, where '#' signifies a path terminator where no node exists below.

Here's an example:

   1
  / \
 2   3
    /
   4
    \
     5
The above binary tree is serialized as [1,2,3,#,#,4,#,#,5].
---------------------------------------------递归处理-----------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
对于每个node的左子树来说，规律：他的右兄弟变成了左子树，parent变成了右子树。所以需要记录parent
一直递归左子树，
Time: O(depth)，因为递归只递归左子树，所以就是数的深度

*/
class Solution {
 
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root; 
        }
       return helper(root, null);
    }
    
    private TreeNode helper(TreeNode node, TreeNode parent) {
        TreeNode newRoot;
        if (node == null) {
            return null;
        }
        //必须先有这一步
        newRoot = helper(node.left, node);
        
        if (node.left == null && node.right == null) {
            newRoot = node;
        }
        
        // 特殊处理根节点
        if (parent == null) {
            node.right = null;
            node.left = null;
        }
        else { 
            node.left = parent.right;
            node.right = parent;
        }
        return newRoot;
    }
    
}

---------------- 后续遍历 2022。6 return 最左边 -----------------------------
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

*/
class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root; 
        }
        TreeNode newRoot = upsideDownBinaryTree(root.left); 
        upsideDownBinaryTree(root.right); 
        TreeNode oldLeft = root.left;
        TreeNode oldRight = root.right;
        root.right = null; 
        root.left = null; 
        oldLeft.right = root; 
        oldLeft.left = oldRight;
        return newRoot;
    
        
    }
}
