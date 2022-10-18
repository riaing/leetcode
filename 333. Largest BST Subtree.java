Given the root of a binary tree, find the largest subtree, which is also a Binary Search Tree (BST), where the largest means subtree has the largest number of nodes.

A Binary Search Tree (BST) is a tree in which all the nodes follow the below-mentioned properties:

The left subtree values are less than the value of their parent (root) node's value.
The right subtree values are greater than the value of their parent (root) node's value.
Note: A subtree must include all of its descendants.

 

Example 1:



Input: root = [10,5,15,1,8,null,7]
Output: 3
Explanation: The Largest BST Subtree in this case is the highlighted one. The return value is the subtree's size, which is 3.
Example 2:

Input: root = [4,2,7,2,3,5,null,2,null,null,null,null,null,1]
Output: 2
 

Constraints:

The number of nodes in the tree is in the range [0, 104].
-104 <= Node.val <= 104
 

Follow up: Can you figure out ways to solve it with O(n) time complexity?
  
  
  ------------------------------
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
    int min;
    int max;
    int totalNodes;
    boolean isBST; 
    public Node() {
        this.isBST = false;
    }
    public Node(int min, int max, int totalNodes, boolean isBST) {
        this.min = min;
        this.max = max;
        this.totalNodes = totalNodes;
        this.isBST = isBST;
    }
} 

class Solution {
    int largestBST = 0; 
    public int largestBSTSubtree(TreeNode root) {
        helper(root);
        return largestBST; 
    }
    private Node helper(TreeNode cur) {
        if (cur == null) {
            return new Node(Integer.MAX_VALUE, Integer.MIN_VALUE, 0, true);  // 空指针也是 BST
        }
        // if (cur.left == null && cur.right == null) { 不需要了，上几行已经处理
        //     return new Node(cur.val, cur.val, 1, true);
        // }
        Node left = helper(cur.left);
        Node right = helper(cur.right);
        Node curNode = new Node();
        curNode.min = Math.min(Math.min(left.min, right.min), cur.val);
        curNode.max = Math.max(Math.max(left.max, right.max), cur.val);
        curNode.totalNodes = 1 + left.totalNodes + right.totalNodes;
        
        // find largest BST 
        if ((cur.left == null || left.max < cur.val) && (cur.right == null || right.min > cur.val) && left.isBST && right.isBST) {
            curNode.isBST = true;
            largestBST = Math.max(largestBST, curNode.totalNodes);
        }
        return curNode; 
    }
}
