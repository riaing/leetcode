Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public int maxDepth(TreeNode root) {
        if(root== null){
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left) , maxDepth(root.right));
    }
}

----------------非递归解法,算出层数就是-------------------------------------------------------------------------------

public int maxDepth(TreeNode root) {
 2     if(root == null)
 3         return 0;
 4     
 5     int depth = 0;
 6     LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
 7     queue.add(root);
 8     int curNum = 1; //num of nodes left in current level
 9     int nextNum = 0; //num of nodes in next level
10     while(!queue.isEmpty()){
11         TreeNode n = queue.poll();
12         curNum--;
13         if(n.left!=null){
14             queue.add(n.left);
15             nextNum++;
16         }
17         if(n.right!=null){
18             queue.add(n.right);
19             nextNum++;
20         }
21         if(curNum == 0){
22             curNum = nextNum;
23             nextNum = 0;
24             depth++;
25         }
26     }
27     return depth;
28 }
