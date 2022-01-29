Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]

-----------------BFS normal traversal + 一点判断条件------------------------------------------------------------
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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
       
        List<List<Integer>> results = new LinkedList<List<Integer>>();
        if (root == null) {
            return results;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        boolean rightToLeft = false; 
        while (!q.isEmpty()) {
            List<Integer> result = new LinkedList<Integer>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                result.add(cur.val);

                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            // 对每行进行判断，reverse
            if (rightToLeft) {
                Collections.reverse(result);
                rightToLeft = false;
            }
            else {
                rightToLeft = true;
            }
            results.add(result); 
        }
        return results;
    }
}
---------------- 2022.1.28 update solution ---------------------------
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
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
         List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (root == null) {
            return results;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean l2r = true; // order to add current level's node 
       
        while (q.size() != 0) {
            int size = q.size();
            List<Integer> res = new ArrayList<Integer>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                // add next level's node by order 
                if (l2r) {
                    res.add(cur.val);
                }
                else {
                    res.add(0, cur.val); // 只要加进去的时候不一样就行了，树的 node 还是正常顺序
                }
                if (cur.left != null){
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            results.add(res);
            l2r = !l2r;
        }
        return results;
    }
}
