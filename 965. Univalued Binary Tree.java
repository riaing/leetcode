/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

Time Complexity: O(N), where NN is the number of nodes in the given tree.

Space Complexity: O(H), where HH is the height of the given tree. 
--------------------------concise写法，行数少-----------------------------------------------------------------------
// A tree is univalued 当root == left/right,并且left/right也是univalued。
class Solution {
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 一次判断左右子树是否unival，就是判断子树是否等于root.val，以及子树本身是否unival。
        boolean leftCorrect = root.left == null ? true : root.val == root.left.val && isUnivalTree(root.left); 
       
        boolean rightCorrect = root.right == null ? true : root.val == root.right.val && isUnivalTree(root.right);
        
        return leftCorrect && rightCorrect;
        
    }
}

---------------------比较能理解的写法----------------------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// A tree is univalued 当 1，root == left/right； 2，并且left/right也是univalued。
class Solution {
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 判断left/right是否等于root
        if (root.left != null && root.left.val != root.val) {
            return false;
        }
        if (root.right != null && root.right.val != root.val) {
            return false;
        }
        // 判断 左右子树是否unival。
        return isUnivalTree(root.left) && isUnivalTree(root.right); 
        
 
    }
}
