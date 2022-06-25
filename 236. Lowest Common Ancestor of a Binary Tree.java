参考： https://mp.weixin.qq.com/s/njl6nuid0aalZdH5tuDpqQ 

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]


 

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 

Note:

All of the nodes' values will be unique.
p and q are different and both values will exist in the binary tree.
 
 ----------------拓展到 N-ary tree， 如果两个子树有返回值，那么LCA就是root，如果只有一个返回值，那么LCA就在那个子树上 ---------------------------------------------------------------------------
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        
        if (p == root || q == root) {
            return root;
        }
        int count = 0; 
        List<TreeNode> childrenNode = new ArrayList<TreeNode>(); // 记录有返回值的子树
      
        TreeNode left_ancestor = lowestCommonAncestor(root.left, p, q);
        TreeNode right_ancestor = lowestCommonAncestor(root.right, p, q);
        
        // n-tree的话就依次扫child node
        if (left_ancestor != null) {
            childrenNode.add(left_ancestor);
        }
        if (right_ancestor != null) {
            childrenNode.add(right_ancestor);
        }
        
        if (childrenNode.size() == 2) {
            return root;
        }
        else if (childrenNode.size() == 1){ 
            return childrenNode.get(0);
        }
        else {
            return null;
        }
       
        一下是二叉树的解法
        // if (left_ancestor != null && right_ancestor != null) {
        //     return root;
        // } else if (left_ancestor != null) {
        //     return left_ancestor;
        // } 
        // else if (right_ancestor != null) {
        //     return right_ancestor;
        //  }
        //    else {
        //     return null;
        // }
    }
}

------------------------------------------- D&C  O(n) -----------------------------------------------------------------------------
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
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 
 三种情况：
 1. 在root 上
 2. 在root 左或者右
 3. 在root两边
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) { // 题目说了p,q一定存在
            return null;
        }
        // 在root上
        if (root.val == p.val || root.val == q.val) { 
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        // 后序位置，在root两边
        if (left != null && right != null) {
            return root;
        }
        // 在左边或者右边
        return left == null ? right : left;
        
    }
}
}

------------------------------------ brute force  o(n^2)--------------------------------------------------------------------------------------
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        
        if (p == root || q == root) {
            return root;
        }
        boolean pAtLeft = contains(root.left, p);
        boolean qAtLeft = contains(root.left, q);
        
        if (pAtLeft && qAtLeft) {
            return lowestCommonAncestor(root.left, p, q);
        }
        else if (!pAtLeft && !qAtLeft) {
            return lowestCommonAncestor(root.right, p, q);
        }
        else {
            return root;
        }
    }
    
    private boolean contains(TreeNode root, TreeNode node) {
        if (root == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        return contains(root.left, node) || contains(root.right, node); 
    }
}
