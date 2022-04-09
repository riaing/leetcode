Lc原题: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/  

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 
  三种情况：
 1. 在root 上: left == null and right == null
 2. 在root 左或者右:
 3. 在root两边: left != null && right != null
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) { // 题目说了p,q一定存在
            return null;
        }
        // 前序遍历
        if (root.val == p.val || root.val == q.val) { 
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        // 后序位置，已经知道左右子树是否存在目标值
        if (left != null && right != null) {
            return root;
        }
        
        return left == null ? right : left;
        
    }
}
 
------------------------------- 改题：求某个node的path
 */
class Solution {
    public TreeNode findPath(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> test = new ArrayList<TreeNode>();
        printPath(root, q, new ArrayList<TreeNode>(), test);
        for(TreeNode t: test) {
             System.out.println(t.val); 
        }
    }
    
    // 基础题
    private void printPathAndDirection(TreeNode root, TreeNode p, List<TreeNode> curRes, List<TreeNode> res, List<String> curDirection, List<String> directionRes) {
        if (root == null) {
            return;
        }
        if (root.val == p.val) {
            curRes.add(root);
            res.addAll(new ArrayList<TreeNode>(curRes));
            directionRes.addAll(new ArrayList<String>(curDirection));
            return;
        }
        
        curRes.add(root);
        curDirection.add("left");
        printPath(root.left, p, curRes, res, curDirection, directionRes);
        curDirection.remove(curDirection.size()-1);
        
        curDirection.add("right");
        printPath(root.right, p, curRes, res, curDirection, directionRes);
        curRes.remove(curRes.size()-1);
        curDirection.remove(curDirection.size()-1);
        return; 
    }

------------- Google 真题：求两个node之path ----------------------------------------------
  思路 1. find lowest common ancestor 2. find lca to node''s path, separately 
  .... 
  
  麻烦些的思路：1. find root - node''s path, seperately, store in a list 2. from the end of the lists, find the lowest common ancestor 3. print the required result 
  
  /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 
google题：求两个node1到node2的path.用up，left，right来代表
exmample 1. 5->1的path为up，right。
example 2. 5->4的path为right，right

 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) { // 题目说了p,q一定存在
            return null;
        }
        // 1. 先分别求出root到两个node的path
        List<TreeNode> node1Path = new ArrayList<TreeNode>();
        List<String> node1Direction = new ArrayList<String>();
        printPath(root, p, new ArrayList<TreeNode>(), node1Path, new ArrayList<String>(), node1Direction);
        System.out.println(node1Direction);
        List<TreeNode> node2Path = new ArrayList<TreeNode>();
        List<String> node2Direction = new ArrayList<String>();
        printPath(root, q, new ArrayList<TreeNode>(), node2Path, new ArrayList<String>(), node2Direction);
        System.out.println(node2Direction);
        // 2. 找到俩node的lowest common ancestor，by 倒着推两个list，找到第一个相同node -> 用set
        TreeNode lca = root;
        Set<TreeNode> node1Set = new HashSet<TreeNode>();
        for (int i = node1Path.size()-1; i >= 0; i--) {
            node1Set.add(node1Path.get(i));
        }
       
        for (int j = node2Path.size()-1; j >= 0; j--) {
            if (node1Set.contains(node2Path.get(j))) {
                lca = node2Path.get(j);
                break;
            }
        }

        
        //3. print Path. 对于左边的，直接update即可，右边的，要再走一遍找左右
        List<String> nodeToNodePath = new ArrayList<String>();
        int count = (node1Path.size() - 1) - node1Path.indexOf(lca); // 第一个node走几步到达lca
        nodeToNodePath.add("up" + "*" + count);

        TreeNode cur = lca;
        int index = node2Path.indexOf(lca) + 1;
        while (cur != q) {
            if (cur.left.val == node2Path.get(index).val) {
                nodeToNodePath.add("left");
                cur = cur.left;
            }
            else {
                 nodeToNodePath.add("right");
                cur = cur.right;
            }
            index++;
        }
        
        // System.out.println(nodeToNodePath);
        
        
        
        
//         // 求lowest common ancestor的code
//         if (root.val == p.val || root.val == q.val) { 
//             return root;
//         }
//         TreeNode left = lowestCommonAncestor(root.left, p, q);
//         TreeNode right = lowestCommonAncestor(root.right, p, q);
        
//         // 后序位置，已经知道左右子树是否存在目标值
//         if (left != null && right != null) {
//             return root;
//         }
//         return left == null ? right : left;
        return null; 
        
    }
    
    private void printPathAndDirection(TreeNode root, TreeNode p, List<TreeNode> curRes, List<TreeNode> res, List<String> curDirection, List<String> directionRes) {
        if (root == null) {
            return;
        }
        if (root.val == p.val) {
            curRes.add(root);
            res.addAll(new ArrayList<TreeNode>(curRes));
            directionRes.addAll(new ArrayList<String>(curDirection));
            return;
        }
        
        curRes.add(root);
        curDirection.add("left");
        printPath(root.left, p, curRes, res, curDirection, directionRes);
        curDirection.remove(curDirection.size()-1);
        
        curDirection.add("right");
        printPath(root.right, p, curRes, res, curDirection, directionRes);
        curRes.remove(curRes.size()-1);
        curDirection.remove(curDirection.size()-1);
        return; 
    }
}
  
