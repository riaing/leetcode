Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.


----------------------------D&C + recursion----------------------------------------------------------------------------
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

Time Complexity:O(N). We visit every node once.

Space Complexity: O(N), the size of our implicit call stack during our depth-first search.
**/
class Solution {
    int max = Integer.MIN_VALUE; 
    public int diameterOfBinaryTree(TreeNode root) {
        // 0, root == null在主程序中单独处理 ：corner case [] 
        if (root == null) {
            return 0;
        }
        helper(root);
        return max;
    }
    
    //helper还是找一个single path，通过一个global value max来判断global最长路径。
    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftDepth = helper(root.left);
        int rightDepth = helper(root.right);
            
        //通过给出的例子来思考：如果左边返回2，右边返回1，那么整体周长就是2+1.。。
        // 1，conquer时 root must be used
        // 2，updated global answer with using both children 
        max = Math.max(leftDepth+rightDepth, max);
        
        //3. return depth(nodes) :
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
