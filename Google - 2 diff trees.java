Given two binary trees, explain how you would create a diff such that if you have that diff and either of the trees you should be able to generate 
the other binary tree. Implement a function which takes TreeNode tree1, TreeNode tree2 and returns that diff. diff can be a list, a merged tree 
with annoation etc. 
  
 ---------------- 1. 先merge 两树， 2， 通过merged 和第一树，还原第二树 -----------------------
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
 
class Node {
    int firstTreeVal;
    TreeNode treeNode; // 存总value，以及当一树为null时，此就是另一棵树
    Node left;
    Node right;
    public Node(int firstTreeVal, int val) {
        this.firstTreeVal = firstTreeVal;
        this.treeNode = new TreeNode(val); 
    }
    
    public Node(int firstTreeVal, TreeNode treeNode) {
        this.firstTreeVal = firstTreeVal;
        this.treeNode = treeNode; 
    }
}

class Solution {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) { 
        Node test = mergeTreeForGoogle(root1, root2); 
        printTree(test);
        TreeNode secondTree = recoverSecondTree(test);
        System.out.println("recover ");
        printTree(secondTree);
       
        // 原题： merge 2 trees ： https://leetcode.com/problems/merge-two-binary-trees/ 
        TreeNode root = helper(root1, root2);
        return root; 
    }
    private TreeNode helper(TreeNode root1, TreeNode root2) {
         if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        TreeNode root = new TreeNode(root1.val + root2.val);
        root.left = helper(root1.left, root2.left);
        root.right = helper(root1.right, root2.right);
        return root; 
    }
    
    // google 题：两个tree找diff。 1) merge 两tree，node中记录第一个tree的value 2） 通过merged tree 复原第二个tree
    public Node mergeTreeForGoogle(TreeNode root1, TreeNode root2) {    // the tree contains val from first tree 
        if (root1 == null && root2 == null) {
            return null; 
        }
        if (root1 == null) {
            return new Node(-1, root2); // Corner case: assume -1 is an unreachable value, 直接包括第二棵树 
        }
        if (root2 == null) {
            return new Node(root1.val, root1);
        }
        
        Node root = new Node(root1.val, root1.val + root2.val);
        root.left = mergeTreeForGoogle(root1.left, root2.left);
        root.right = mergeTreeForGoogle(root1.right, root2.right);
        return root; 
    }
    
    public TreeNode recoverSecondTree(Node mergedRoot) {
        if (mergedRoot == null || mergedRoot.treeNode.val == mergedRoot.firstTreeVal) {
            return null; 
        }
        if (mergedRoot.firstTreeVal == -1) { // corner case: 第一树不存在，直接返回第二棵树
            return mergedRoot.treeNode; 
        }
        
        TreeNode root = new TreeNode(mergedRoot.treeNode.val - mergedRoot.firstTreeVal); 
        root.left = recoverSecondTree(mergedRoot.left);
        root.right = recoverSecondTree(mergedRoot.right);
        return root; 
    }
    
    
    // test 
    public void printTree(Node root) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            if(temp !=null)
                System.out.print(temp.treeNode.val + "("+temp.firstTreeVal+")" + " ");
            if(temp == null) {
                System.out.print("null" + " ");
                if(queue.isEmpty()) break;
                continue;
            }

            /*Enqueue left child */
                queue.add(temp.left);
            /*Enqueue right child */
                queue.add(temp.right);
        }
    }
    public void printTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if(temp !=null)
                System.out.print(temp.val + " ");
            if(temp == null) {
                System.out.print("null" + " ");
                if(queue.isEmpty()) break;
                continue;
            }

            /*Enqueue left child */
                queue.add(temp.left);
            /*Enqueue right child */
                queue.add(temp.right);
        }
    }
}
