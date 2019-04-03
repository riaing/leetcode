Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
--------------------------------D & C -----------------------------------------------------------------------
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
     int postIndex;
    Map<Integer, Integer> map; // construct a map key-inorder element, value-element's index 
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // corner case
        if (postorder == null || inorder == null || postorder.length == 0 || inorder.length == 0) {
            return null; 
        }
        postIndex = postorder.length-1;
        map = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(inorder, postorder, 0, inorder.length-1); 
    }
    
    private TreeNode buildTree(int[] inorder, int[] postorder, int inS, int inE) {
        if (inS == inE) {
            postIndex--;
            return new TreeNode(inorder[inS]);
        }
        else if (inS > inE) {
            return null;
        }
        int rootVal = postorder[postIndex]; 
        TreeNode root = new TreeNode(rootVal);
        postIndex--;
        // determine the subarray for left and right tree according to root's index in inorder
        int rootIndexInInorderArray = map.get(rootVal);
        root.right = buildTree(inorder, postorder, rootIndexInInorderArray+1, inE);
        root.left = buildTree(inorder, postorder, inS, rootIndexInInorderArray-1);
       
        return root; 
    }
        
}
