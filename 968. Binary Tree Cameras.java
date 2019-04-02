Given a binary tree, we install cameras on the nodes of the tree. 

Each camera at a node can monitor its parent, itself, and its immediate children.

Calculate the minimum number of cameras needed to monitor all nodes of the tree.

 

Example 1:


Input: [0,0,null,0,0]
Output: 1
Explanation: One camera is enough to monitor all nodes if placed as shown.
Example 2:


Input: [0,0,null,0,null,0,null,null,0]
Output: 2
Explanation: At least two cameras are needed to monitor all nodes of the tree. The above image shows one of the valid configurations of camera placement.

Note:

The number of nodes in the given tree will be in the range [1, 1000].
Every node has value 0.
----------------------------------------Greedy思想-----------------------------------------------------
https://irisjavadiary.blogspot.com/2019/01/968-binary-tree-cameras.html 
Time Complexity: O(N)O(N), where NN is the number of nodes in the given tree.

Space Complexity: O(H)O(H), where HH is the height of the given tree.  

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
/ 0-> not monitored, 1-> monitored without cam, 2-> monitored with a cam
如果用这三种情况表示，思路就非常straight forward了
假如left or right有任意的没有 monitored的点，那么它们必须被当前点cover，当前点必须是2
如果它们都被cover了且任意一个点有cam，那么可以cover当前点，当前点就是1
如果左右孩子都被cover了但是都没有cam，那么当前点是0，等待着被它的parent cover
*/
class Solution {
     private int count;
    // 0-> not monitored, 1-> monitored without cam, 2-> monitored with a cam
    public int minCameraCover(TreeNode root) {
        if (dfs(root) == 0) {
            count++;
        }
        return count;
    }
    private int dfs(TreeNode node) {
        
        if (node == null) {
            return 1;
        }
        int left = dfs(node.left);
        int right = dfs(node.right);
        
        if (left == 0 || right == 0) {
            count++;
            return 2;
        }
        if (left == 2 || right == 2) {
            return 1;
        }
        else { // left == 1 || right == 1
            return 0;
        }
    }
}
