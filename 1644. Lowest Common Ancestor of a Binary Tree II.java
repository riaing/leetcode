https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/ 

Given the root of a binary tree, return the lowest common ancestor (LCA) of two given nodes, p and q. If either node p or q does not exist in the tree, return null. All values of the nodes in the tree are unique.

According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a binary tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)". A descendant of a node x is a node y that is on the path from node x to some leaf node.

 

Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:



Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5. A node can be a descendant of itself according to the definition of LCA.
Example 3:



Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 10
Output: null
Explanation: Node 10 does not exist in the tree, so return null.
 

Constraints:

The number of nodes in the tree is in the range [1, 104].
-109 <= Node.val <= 109
All Node.val are unique.
p != q

-------------------------------------- 解法 -----------------------------------------------------------------
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
要扫两遍树：第一遍搜索p,q 是否在树里（需要后序遍历）。第二遍按 Lowest common Ancestor写法。
提高：两遍可以合成一遍：
方法1： 对二叉树进行完全搜索，同时global var记录p和q是否同时存在树中，从而满足题目的要求。
方法2：新的object记录{Node，count} -> count == 2时才能返回node。 https://www.youtube.com/watch?v=d1b1WcKOGkU  
*/
=============================== 方法1 =============================
class Solution {
    boolean findP;
    boolean findQ; 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode res = findNode(root, p, q);
        if (findP && findQ) {
            return res;
        }
        return null;
        
    }
    
    private TreeNode findNode(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        
        TreeNode left = findNode(root.left, p, q);
        TreeNode right = findNode(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        
        if (root.val == p.val || root.val == q.val) {
            if (root.val == p.val) {
                findP = true;
            }
            else {
                findQ = true;
            }
            return root; // 找到了一个值
        }
        
        return left == null ? right : left; 
    }
    
}

=================== 方法2 ========================================
public class Node {
    TreeNode node;
    int count; // how many p,q found 
    
    Node(TreeNode node, int count) {
        this.node = node;
        this.count = count; 
    }
}

class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Node res = findNode(root, p, q);
        if (res.count == 2) {
            return res.node;
        }
        return null;
        
    }
    
    private Node findNode(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return new Node(null, 0);
        }
        
        Node left = findNode(root.left, p, q);
        Node right = findNode(root.right, p, q);
        
        if (left.node != null && right.node != null) {
            return new Node(root, 2);
        }
        
        if (root.val == p.val || root.val == q.val) {
            
            return new Node(root, 1 + left.count + right.count); // 找到了一个值
        }
        
        return left.node == null ? right : left; 
    }
    
}
