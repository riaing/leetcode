Given a binary tree, find its minimum depth.
还可用BFS解。 

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

其实跟Maximum Depth of Binary Tree非常类似，只是这道题因为是判断最小深度，所以必须增加一个叶子的判断
（因为如果一个节点如果只有左子树或者右子树，我们不能取它左右子树中小的作为深度，因为那样会是0，我们只有在叶子节点才能判断深度
，而在求最大深度的时候，因为一定会取大的那个，所以不会有这个问题）。


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
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
 -----------------------------recursion------------------------------------------------------------------------------
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = minDepth(root.left);
        int rightHeight = minDepth(root.right);
         
        if (leftHeight == 0) {
            return 1 + rightHeight; 
        }
        else if (rightHeight == 0) {
            return 1 + leftHeight;
        }
        else {
            return 1 + Math.min(leftHeight, rightHeight);
        }
    }
}
--------------------------------------------------------------------------------------------------------------------
相似的题像是 find the max value of a binary tree: 

public int maxValueInBinaryTree(TreeNode root) {
	if(root == null){
		Return 0 ;
	}
	if(root.left == null && root.right ==null) { 如果为根节点
		Return root.val;
	}
	if(root.left != null && root.right == null){ 如果只有左子树
		Return Math.max(root.val , maxValueInBinaryTree(root.left);
	}
	if(root.left == null && root.right != null){ only has right tree
		Return Math.max(root.val , maxValueInBinaryTree(root.right );
	}
	//如果左右子树都有
	Int cur = Math.max(maxValueInBinaryTree(root.left),  maxValueInBinaryTree(root.right));
	Return Math.max(root.val , cur);
}
				
------------------------------------非递归解法------------------------------------------------------------------------
 public int minDepth(TreeNode root) {
 2         if(root == null)
 3             return 0;
 4         
 5         int depth = 1;//The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 6         LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
 7         queue.add(root);
 8         int curnum = 1;
 9         int nextnum = 0;
10         while(!queue.isEmpty()){
11             TreeNode cur = queue.poll();
12             curnum--;
13             
14             if(cur.left == null && cur.right == null)
15                 return depth;
16             
17             if(cur.left != null){
18                queue.add(cur.left);
19                nextnum++;
20             }
21             
22             if(cur.right != null){
23                 queue.add(cur.right);
24                 nextnum++;
25             }
26             
27             if(curnum == 0){
28                 curnum = nextnum;
29                 nextnum = 0;
30                 depth++;
31             }
32         }
33         return depth;
34     }
