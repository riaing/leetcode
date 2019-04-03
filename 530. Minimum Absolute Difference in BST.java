Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

Example:

Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
 

Note: There are at least two nodes in this BST.


----------根据BST性质，inorder traversal the tree的结果必然是increasing order，然后比较相邻两个node的值，取最小----------------------
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
    public int getMinimumDifference(TreeNode root) {
        // increasing order of inorder traversal for BST 
        List<Integer> nodes = inorderTraversal(root);
       
        int val = Integer.MAX_VALUE;
        int left = 0;
        int right = 1;
        while(right < nodes.size()) {
            val = Math.min(val, Math.abs(nodes.get(right) - nodes.get(left)));
            left++;
            right++;
        }
        return val;
    }
    
    private List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }
        
        res.addAll(inorderTraversal(root.left));
        res.add(root.val);
        res.addAll(inorderTraversal(root.right));
        return res;
    }
}

-------------------------D & C： 同样适用于普通binary tree，而不限于BST-------------------------------------------------------
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
这个方法同样适用于普通Binary tree

对于每一个node，他的当前minDiff就是他与左子树的最大值之差，或者他与右子树的最小值之差。所以TreeInfo中至少要存max，min。
那么对于整个树来说，minDiff，就是当前node的min(当前node的minDiff， 左子树的minDiff，右子树的minDiff)
我们在treeInfo中存global的minDiff，每次算出来当前minDiff后，进行上面的对比即可。
*/
class Solution {
    class TreeInfo {
        int max; 
        int min;
        int minAbsDiff; // this is global min diff 
        
        public TreeInfo(int max, int min, int minAbsDiff) {
            this.max = max;
            this.min = min;
            this.minAbsDiff = minAbsDiff;
        }
    }
    public int getMinimumDifference(TreeNode root) {
        TreeInfo info = find(root);
        return info.minAbsDiff;
    }
    
    private TreeInfo find(TreeNode root) {
        if (root == null) {
            return new TreeInfo(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        // 涉及到运算，发现需要单独跳出来作为base condition。
        if (root.left == null && root.right == null) {
            return new TreeInfo(root.val, root.val, Integer.MAX_VALUE);                       
        }
        
        TreeInfo left = find(root.left);
        TreeInfo right = find(root.right);
        
        int min = Math.min(root.val, Math.min(left.min, right.min));
        int max = Math.max(root.val, Math.max(left.max, right.max));
        
        // 因为计算时可能会有overflow，所以把左右子树为null时分开写
        // 当前的minDiff就是 root val与左子树最大值/右子树最小值之差。
        int curMinAbsDiff;
        if (root.left == null) {
            curMinAbsDiff = Math.abs(right.min - root.val);
        }
        else if (root.right == null) {
            curMinAbsDiff = Math.abs(root.val - left.max);
        }
        else {
           curMinAbsDiff = Math.min(Math.abs(root.val - left.max),  Math.abs(right.min - root.val));
        }
        // 那么gloabal的minDiff 就是当前minDiff,左minDiff，右minDiff 的最小
        int minAbsDiff = Math.min(Math.min(left.minAbsDiff, right.minAbsDiff), curMinAbsDiff);
        
        return new TreeInfo(max, min, minAbsDiff);
        
    }
}
