Given a binary tree, return the postorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [3,2,1]

--------------------------Divide and Conquer--------------------------------------------------------------------------
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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if(root == null) {
            return res;
        }
        res.addAll(postorderTraversal(root.left));
        res.addAll(postorderTraversal(root.right));
         res.add(root.val);
        return res;
    }
}

----------------------stack,运用与inorder的相似性--------------------------------------------------------------------------
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
巧妙运用几个inorder vs postorder的关联：由于postorder的顺序是左-右-根，而inorder的顺序是根-左-右，二者其实还是很相近的，我们可以先在先序遍历的方法上做些小改动，使其遍历顺序变为根-右-左，然后翻转一下，就是左-右-根啦，翻转的方是反向加入结果res，每次都在结果res的开头加入结点值，而改变先序遍历的顺序就只要该遍历一下入栈顺序，先左后右，这样出栈处理的时候就是先右后左啦
*/
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
      List<Integer> res = new ArrayList<Integer>();
      Deque<TreeNode> s = new LinkedList<TreeNode>();
        if (root != null) {
               s.push(root);
        }
        while(!s.isEmpty()) {
            TreeNode cur = s.pop();
            res.add(0, cur.val);
            if (cur.left != null) {
                s.push(cur.left); //先左后右，使出栈的结果变为根-右-左
            }
            if (cur.right != null) {
                s.push(cur.right);
            }
        }
        return res;
    }
}
