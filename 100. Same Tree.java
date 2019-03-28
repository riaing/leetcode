/*Given two binary trees, write a function to check if they are equal or not.
Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
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
------------------D&C---------------------------------------------------------------------------------------
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
       if(p == null && q == null){
           return true;
       }
       if(p == null || q == null){
           return false;
       }
       if(p.val != q.val){
           return false;
       }
      return  isSameTree(p.left, q.left) &&  isSameTree(p.right, q.right); 
      
    }
}


------------非递归解法： 类似Binary Tree Level Order Traversal，用queue存当前层续的节点，同时加上下一层节点，直到Queue size为零-------------- 
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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> p1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        
        p1.offer(p);
        q1.offer(q);
        
        while(!p1.isEmpty() && !q1.isEmpty()) {
            TreeNode curP = p1.poll();
            TreeNode curQ = q1.poll();
            // 这一步要单独拿出来说，接下来才能把两个node的左右儿子加入queue，不在这判断二者都不为null的话，接下来的queue.offer会有Null pointer Exception 
            if (curP == null && curQ == null) {
                continue;
            }
            if (!checkSame(curP, curQ)) {
                return false;
            }
            p1.offer(curP.left);
            p1.offer(curP.right);
            q1.offer(curQ.left);
            q1.offer(curQ.right);  
        }
        
        return p1.isEmpty() && q1.isEmpty();
    }
    
    // Check if two tree node are the same:
    // p and p are not None,
    // p.val is equal to q.val
    private boolean checkSame(TreeNode p, TreeNode q) {
      
         if (p == null || q == null) {
            return false;
        }
        else {
            return p.val == q.val;
        }
    }
}
