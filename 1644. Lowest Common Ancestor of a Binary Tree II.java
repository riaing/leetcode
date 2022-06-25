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
但对于这道题来说，p和q不一定存在于树中，所以你不能遇到一个目标值就直接返回，而应该对二叉树进行完全搜索（遍历每一个节点），如果发现p或q不存在于树中，那么是不存在LCA的。

回想我在文章开头分析的几种find函数的写法，哪种写法能够对二叉树进行完全搜索来着？

这种：

TreeNode find(TreeNode root, int val) {
    if (root == null) {
        return null;
    }
    // 先去左右子树寻找
    TreeNode left = find(root.left, val);
    TreeNode right = find(root.right, val);
    // 后序位置，判断 root 是不是目标节点
    if (root.val == val) {
        return root;
    }
    // root 不是目标节点，再去看看哪边的子树找到了
    return left != null ? left : right;
}
那么解决这道题也是类似的，我们只需要把前序位置的判断逻辑放到后序位置即可：

// 用于记录 p 和 q 是否存在于二叉树中
boolean foundP = false, foundQ = false;

TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    TreeNode res = find(root, p.val, q.val);
    if (!foundP || !foundQ) {
        return null;
    }
    // p 和 q 都存在二叉树中，才有公共祖先
    return res;
}

// 在二叉树中寻找 val1 和 val2 的最近公共祖先节点
TreeNode find(TreeNode root, int val1, int val2) {
    if (root == null) {
        return null;
    }
    TreeNode left = find(root.left, val1, val2);
    TreeNode right = find(root.right, val1, val2);

    // 后序位置，判断当前节点是不是 LCA 节点
    if (left != null && right != null) {
        return root;
    }

    // 后序位置，判断当前节点是不是目标值
    if (root.val == val1 || root.val == val2) {
        // 找到了，记录一下
        if (root.val == val1) foundP = true;
        if (root.val == val2) foundQ = true;
        return root;
    }

    return left != null ? left : right;
}
这样改造，对二叉树进行完全搜索，同时记录p和q是否同时存在树中，从而满足题目的要求

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
