Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”

        _______6______
       /              \
    ___2__          ___8__
   /      \        /      \
   0      _4       7       9
         /  \
         3   5
For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6. Another example is LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.


两种方法都是利用BST的性质！ 
-----------------------------------------D & C------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
 
/* 对于二叉搜索树，公共祖先的值一定大于等于较小的节点，小于等于较大的节点。换言之，在遍历树的时候，如果当前结点大于两个节点，则结果在当前结点的左子树里，如果当前结点小于两个节点，则结果在当前节点的右子树里。
*/

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (p == root || q == root) {
            return root;
        }
        // 判断是否两点在左子树上
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 否则分别在左右子树上，则return root
        else {
            return root;
        }
    }
}
--------------------------------- iteration --------------------------------------------------------------------------------
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null){
            return root;
        }
        Queue<TreeNode> lca = new LinkedList<TreeNode>(); 
        lca.offer(root);
        
        while(lca.size() >0){
            TreeNode cur = lca.poll();
            if(p.val >= cur.val && q.val <= cur.val){//如果在root的两侧，则root为LCA 
                return cur;
            }
            
            if(p.val <= cur.val && q.val >= cur.val){ //如果在root的两侧，则root为LCA 
                return cur;
            }
            
            if(p.val <= cur.val && q.val<= cur.val){ //如果比root值小，说明在root的左边，往左边遍历 
                lca.offer(cur.left);
            }
            if(p.val >= cur.val && q.val >= cur.val){ //如果比root值大，说明在root的右边 
                lca.offer(cur.right);
            }
        }
         return root;
    }
   
}
