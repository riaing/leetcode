Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.

A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.

 

Example 1:


Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals targetSum:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22
Example 2:


Input: root = [1,2,3], targetSum = 5
Output: []
Example 3:

Input: root = [1,2], targetSum = 0
Output: []
 

Constraints:

The number of nodes in the tree is in the range [0, 5000].
-1000 <= Node.val <= 1000
-1000 <= targetSum <= 1000

------------------------ recursion ----------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/*
Time: 遍历所有 node O(n). 找到每天 path 后要 copy 一遍 curResult，最多 n/2条 path 然后每条path最多 lgn 个node： o(nlgn). 所以总共 O(n + nlgn) 
Space: o(N) of the stack for unbalanced treee. balanced tree is olgn 
For list: 1. 最多有 n/2个 leaf，说明最多n/2条 path。
2. 每条 path 的 node 又和 depth 有关，一般算 lgn 的 depth。->所以总共 nlgn 的 space。
each of these paths can have many nodes in them. For a balanced binary tree (like above), each leaf node will be at maximum depth. As we know that the depth (or height) of a balanced binary tree is O(logN)O(logN) we can say that, at the most, each path can have logNlogN nodes in it. 
*/
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        List<Integer> curResult = new ArrayList<Integer>();
        helper(root, targetSum, results, curResult);
        return results;
        
    }
    
    private void helper(TreeNode node, int targetSum, List<List<Integer>> results, List<Integer> curResult) {
        if (node == null) {
            return;
        }
        if (node.val == targetSum && node.left == null && node.right == null) {
            curResult.add(node.val);
            results.add(new ArrayList<Integer>(curResult));
            curResult.remove(curResult.size()-1); // 记！递归加几次就得移几次
            return;
        }
        curResult.add(node.val);
        helper(node.left, targetSum - node.val, results, curResult);
        helper(node.right, targetSum - node.val, results, curResult);
        curResult.remove(curResult.size()-1);
        return;
    }
}
