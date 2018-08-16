Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.

Calling next() will return the next smallest number in the BST.

Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.

--------------------------------------------
实际就是in-order traversal
Example
For the following binary search tree, in-order traversal by using iterator is [1, 6, 10, 11, 12]
   10
 /    \
1      11
 \       \
  6       12

题中提示了O（1）的time和O（h）的space，联想到用stack，以及一直遍历最左边的node加入stack（则space为O（h））

---------------------解法一 o(1), o(h)
先遍历左边node加入stack，在next（）的时候，pop（），并加入pop值的右边(N)，然后遍历N的左边加入stack。
想法是：pop（）的值相当于当前in order traversal的root（因为left为null），然后push right（N），再遍历N的左边

/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

public class BSTIterator {
    final Deque<TreeNode> stack = new LinkedList<TreeNode>(); 
    public BSTIterator(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode node = root;
        // Add the left node to stack. 
        while(node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode smallest = stack.pop();
        // add the right node 
        if (smallest.right != null) {
            TreeNode node = smallest.right;
            while (node != null) {
                stack.push(node);
                // then add the left 
                node = node.left;
            }            
        }
        return smallest.val;
    }
}

/**
 * Your BSTIterator will be called like this:
 * BSTIterator i = new BSTIterator(root);
 * while (i.hasNext()) v[f()] = i.next();
 */
 -----------------------------------------------------
 
    For 0(n) time and o(1) space, see https://github.com/awangdev/LintCode/blob/master/Java/Binary%20Search%20Tree%20Iterator.java 
