Given preorder and inorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7 
   
-------------------------------D & C -------------------------------------------------------------------------
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
1, preorder中的第一个数总是为root
2，根据preorder中root的index，可以将inorder分为两个list，index左边的subarray为左子树，反之。
3，递归subarray即可得左右子树。

优化： 
1， 用global value map来存inorder中的element-index pair。因为我们每次递归都要根据root将inorder分为两个subarray，存进map省了重复运算
2， global value preIndex：因为每次递归都是找preorder的第一个元素作为root。 

time：O（n）
space: O(N), since we store the entire tree.
*/
class Solution {
    int preIndex;
    Map<Integer, Integer> map; // construct a map key-inorder element, value-element's index 
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // corner case
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null; 
        }
        preIndex = 0;
        map = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(preorder, inorder, 0, inorder.length-1); 
    }
    
    private TreeNode buildTree(int[] preorder, int[] inorder, int inS, int inE) {
        if (inS == inE) {
            preIndex++;
            return new TreeNode(inorder[inS]);
        }
        else if (inS > inE) {
            return null;
        }
        int rootVal = preorder[preIndex]; 
        TreeNode root = new TreeNode(rootVal);
        preIndex++;
        // determine the subarray for left and right tree according to root's index in inorder
        int rootIndexInInorderArray = map.get(rootVal);
        root.left = buildTree(preorder, inorder, inS, rootIndexInInorderArray-1);
        root.right = buildTree(preorder, inorder, rootIndexInInorderArray+1, inE);
        return root; 
    }
}
