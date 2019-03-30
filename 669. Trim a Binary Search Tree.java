Given a binary search tree and the lowest and highest boundaries as L and R, trim the tree so that all its elements lies in [L, R] (R >= L). You might need to change the root of the tree, so the result should return the new root of the trimmed binary search tree.

Example 1:
Input: 
    1
   / \
  0   2

  L = 1
  R = 2

Output: 
    1
      \
       2
Example 2:
Input: 
    3
   / \
  0   4
   \
    2
   /
  1

  L = 1
  R = 3

Output: 
      3
     / 
   2   
  /
 1
 
---------------------------------------------------D&C------------------------------------------------------------------
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
首先来处理root，1，root == null，返回
2， 如果root < L, 说明root的左子树也都< L, 那么左子树也要全部丢弃，所以这时候我们直接return trimBST(root.right). 如果root > R, 同理。
3， 如果root合格了，那么我们分别trim它的左右子树即可


Time Complexity: O(N), where N is the total number of nodes in the given tree. We visit each node at most once.

Space Complexity: O(N). Even though we don't explicitly use any additional memory, the call stack of our recursion could be as large as the number of nodes in the worst case.
*/
class Solution {
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return root;
        }
        
        if (root.val < L) {
            return trimBST(root.right, L, R);
        }
        else if (root.val > R) {
            return trimBST(root.left, L, R);
        }
        else {
            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);
            return root;
        }
    }
}
