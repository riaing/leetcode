Given a binary tree, find the length of the longest path where each node in the path has the same value. This path may or may not pass through the root.

Note: The length of path between two nodes is represented by the number of edges between them.

Example 1:

Input:

              5
             / \
            4   5
           / \   \
          1   1   5
Output:

2
Example 2:

Input:

              1
             / \
            4   5
           / \   \
          4   4   5
Output:

2
Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
------------------------D&C recursion--------------------------------------------------------------------------------
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
0, root == null在主程序中单独处理
辅助程序中，
1，conquer时 root must be used
2，updated global answer with using both children 
3, returen longest path with only one children 给 root
*/
class Solution {
    int max; 
    public int longestUnivaluePath(TreeNode root) {
        //0， 单独处理
        if (root == null) {
            return 0;
        }
        max = 0; 
        helper(root);
        return max; 
    }
    
    // 
    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = helper(root.left);
        int r = helper(root.right);
        // conquer
        
        int pathL = root.left != null && root.left.val == root.val ? l : 0;
        int pathR = root.right != null && root.right.val == root.val ? r: 0;

        // 1，root must be used, 2，updated global answer with using both children 
        /**
        这里包含三种情况：
        1，左右都不符合条件，return 0:没有edge）
        2，当左/右子树满足条件时，ans更新为1+左/Z右子树返回的值， 返回1+左/Z右子树返回的值.
        当左右都满足条件时，ans更新为 2 + 左返回值+ 右返回值， 返回max（左，右返回值）+ 1
        */
        max = Math.max(max, pathL+pathR);
        
        // 3， return # of depths: longest path with only one children 给 root
        return Math.max(pathL, pathR) + 1;
    }
}
