Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example:

Given the sorted array: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5

----------------------------------------D & C ---------------------------------------------------------------------
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
要BST平衡，自然要使每个节点的左右子树的大小尽可能接近。队列已经排序过，所以很自然可以取中间的数字为根节点。左半队列 < 中间数字 < 右边队列，
符合BST的条件。然后分制递归来构建左右子树。
Time: O(n) -> 
Space: o(n) -> need to create each node. (stack tree o(lgn)) 
*/
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        
        return construct(nums, 0, nums.length-1); 
        
    }
    private TreeNode construct(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return new TreeNode(nums[end]);
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = construct(nums, start, mid-1);
        root.right = construct(nums, mid+1, end);
        return root; 
    }
}
