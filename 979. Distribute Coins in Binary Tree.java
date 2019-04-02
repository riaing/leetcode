iven the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.

In one move, we may choose two adjacent nodes and move one coin from one node to another.  (The move may be from parent to child, or from child to parent.)

Return the number of moves required to make every node have exactly one coin.

 

Example 1:



Input: [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.
Example 2:



Input: [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root [taking two moves].  Then, we move one coin from the root of the tree to the right child.
Example 3:



Input: [1,0,2]
Output: 2
Example 4:



Input: [1,0,0,null,3]
Output: 4
 

Note:

1<= N <= 100
0 <= node.val <= N

----------------------       D & C----------------------------------------------------------------------------------
思路：
f the leaf of a tree has 0 coins (an excess of -1 from what it needs), then we should push a coin from its parent onto the 
leaf. If it has say, 4 coins (an excess of 3), then we should push 3 coins off the leaf. In total, the number of moves from 
that leaf to or from its parent is excess = Math.abs(num_coins - 1). Afterwards, we never have to consider this leaf again 
in the rest of our calculation.
写题时
Let dfs(node) be 要给上一步的coin数量 - the excess number of coins in the subtree at or below this node: namely, the number of coins in the subtree, 
minus the number of nodes in the subtree.

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
    int times;
    public int distributeCoins(TreeNode root) {
        times = 0;
        excessCoinsToParent(root);
        return times;
    }
    
    // the excess number of coins at this node - 要给上一步的coin数量 
    // excess = Math.abs(numCoins at this node - 1)
    private int excessCoinsToParent(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 这一步可省略
        if (root.left == null && root.right == null) {
            times += Math.abs(root.val -1); 
            return root.val - 1;
        }
        
        int left = excessCoinsToParent(root.left); // receive how many coins from left; 
        int right = excessCoinsToParent(root.right); 
        
        int excessCoins = root.val + left + right - 1;
        times += Math.abs(excessCoins);
        return excessCoins;
    }
}
