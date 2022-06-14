Given the root of a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus the sum of all keys greater than the original key in BST.

As a reminder, a binary search tree is a tree that satisfies these constraints:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:


Input: root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
Example 2:

Input: root = [0,null,1]
Output: [1,null,1]
 

Constraints:

The number of nodes in the tree is in the range [0, 104].
-104 <= Node.val <= 104
All the values in the tree are unique.
root is guaranteed to be a valid binary search tree.
 

Note: This question is the same as 1038: https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/


------------------ in order traversal 的反向 ---------------------
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
1. 这题就是求suffix sum。通过先遍历右子树-root-左子树的顺序来print descending order，然后global var维持suffix sum
*/
class Solution {
    int suffixSum = 0;
    public TreeNode convertBST(TreeNode root) {
        traverse(root);
        return root; 
        
    }
    
    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        // 降序打印node，所以先走右子树
        traverse(root.right);
        suffixSum += root.val; // 这时候已经算得右子树的和了，加上自己，就为新的val
        root.val = suffixSum;
        // 再走左子树
        traverse(root.left);
    }
}
