Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree 
of s. A subtree of s is a tree consists of a node in s and all of this node‘s descendants. The tree s could also be considered 
     as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.
Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.

解：整体思想都是看s中的每一个node，是否以此为root，能找到一个和 t identical的tree。所以时间都是o(m*n). m->t’s nodes. n-> s‘s nodes 
        
-----------------------------非递归--------------------------------------------------------------------------------------- 
/** level order traversal整个s。 对于s中的每一个node，查看是否以此为root，和t identical。 */

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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(s);
        
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (identical(cur, t)) {
                return true;
            }
            if (cur != null) {
                q.offer(cur.left);
                q.offer(cur.right);
            }
        }
        return false;
    }
        
    // O(m) -> m is # of t's nodes 
    private boolean identical(TreeNode x,TreeNode y) {
         if(x==null && y==null)
            return true;
        if(x==null || y==null)
            return false;
        return x.val==y.val && identical(x.left, y.left) && identical(x.right,y.right);
    }
}


-----------------------------------D&C------------------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/** D&C思想：判断当前s中以当前node为root，是否与t identical，不然的话，看左右两边的subtree 是不是和t identical。 
time: O(m*n) -> identical() is O(m), 然后最坏可能得遍历s中的每一个node（n）。对于每一个node in n，都要call一遍identical（）
*/

class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (identical(s, t)) {
            return true;
        }
        if (s != null) {
            return isSubtree(s.left, t) || isSubtree(s.right, t);
        }
        //说明 s == null && t！=null了。
        return false;
  
        /**等同于这一行。。。 */
        //return (identical(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t));
    }
    
    // O(m) -> m is # of t's nodes 
    private boolean identical(TreeNode x,TreeNode y) {
         if(x==null && y==null)
            return true;
        if(x==null || y==null)
            return false;
        return x.val==y.val && identical(x.left, y.left) && identical(x.right,y.right);
    }
}
