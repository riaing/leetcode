Given a binary tree, return the preorder traversal of its nodes‘ values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,2,3] 


Time complexity : O(n).
Space complexity : O(n).  
----------------------Devide and Conquer-------------------------------------------------------------------------
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if(root == null) {
            return res;
        }
        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
        
    }
}
------------------非递归stack 1 ---------------------------------------------------------------------------------------
   
   
     1)访问结点root，并将结点root入栈;

     2)当stack不为空，pop栈顶，加入result，加入右边，再加入左边（因为stack先进后出的性质）

     3)直到栈为空，则遍历结束。*/  
        
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> s = new LinkedList<TreeNode>();
        s.push(root);
        while(!s.isEmpty()) {
            TreeNode cur = s.pop();
            if (cur != null) {
                res.add(cur.val);
                s.push(cur.right); //因为是stack，所以先放右边再放左边
                s.push(cur.left);
            }
        }
        return res;
    }
}
------------------非递归 stack 2, 提早判断null，可能省一点点time。。。-----------------------------------------------------------
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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> s = new LinkedList<TreeNode>();
        if (root != null) {
               s.push(root);
        }
        while(!s.isEmpty()) {
            TreeNode cur = s.pop();
            res.add(cur.val);
            if (cur.right != null) {
                s.push(cur.right); //因为是stack，所以先放右边再放左边
            }
            if (cur.left != null) {
                s.push(cur.left);
            }
        }
        return res;
    }
}
