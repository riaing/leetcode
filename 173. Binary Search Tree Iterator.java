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

class BSTIterator {
    Deque<TreeNode> stack;
    
    public BSTIterator(TreeNode root) {
        this.stack = new LinkedList<>();
        pushLeftBranch(root, stack); 
    }
    
    public int next() {
        // 重点是1.先pop自己，2.把自己的右边加到stack里
        TreeNode cur = stack.pop();
        // stack.forEach(o -> System.out.println(o.val));
        pushLeftBranch(cur.right, stack);
        return cur.val; 
    }
    
    public boolean hasNext() {
        return !stack.isEmpty(); 
        
    }
    
    private void pushLeftBranch(TreeNode root, Deque<TreeNode> stack) {
        // 将左子树全部加到stack 
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */

 -----------------------------------------------------
 
    For 0(n) time and o(1) space, see https://github.com/awangdev/LintCode/blob/master/Java/Binary%20Search%20Tree%20Iterator.java 
