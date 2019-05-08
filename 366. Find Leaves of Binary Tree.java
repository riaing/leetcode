Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.

 

Example:

Input: [1,2,3,4,5]
  
          1
         / \
        2   3
       / \     
      4   5    

Output: [[4,5,3],[2],[1]]
 

Explanation:

1. Removing the leaves [4,5,3] would result in this tree:

          1
         / 
        2          
 

2. Now removing the leaf [2] would result in this tree:

          1          
 

3. Now removing the leaf [1] would result in the empty tree:

          []         
----------每次delete leave from tree，worst case n +(n-1) + n-2... = O(n^2) -----------------------------------
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
    List<List<Integer>> res; 
    List<Integer> leaves; 
    public List<List<Integer>> findLeaves(TreeNode root) {
        res = new ArrayList<List<Integer>>();
  
        while (root != null) {
            leaves = new ArrayList<Integer>();
            root = deleteLeave(root);
            res.add(new ArrayList<Integer>(leaves)); 
        }
        
        return res;
    }
    
    // Delete leave node of the tree 
    private TreeNode deleteLeave(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            leaves.add(root.val);
            return null;
        }
        root.left = deleteLeave(root.left);
        root.right = deleteLeave(root.right);
        return root;
    }
}

--------------优化，找到每个node该加进array的index ------------
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
    List<List<Integer>> res; 
    public List<List<Integer>> findLeaves(TreeNode root) {
        res = new ArrayList<List<Integer>>();
  
        indexOfWhichListToAdd(root);
        
        return res;
    }
    
    // Return the index of which list the current node is add on 
    private int indexOfWhichListToAdd(TreeNode root) {
        if (root == null) {
            return -1;
        }
        int left = indexOfWhichListToAdd(root.left);
        int right = indexOfWhichListToAdd(root.right);
        
        int childIndex = Math.max(left, right);
        // After find the childIndex, we know cur node is added to the next list
        int curIndex = childIndex+1;
        while (res.size() < curIndex+1) {
            res.add(new ArrayList<Integer>());
        }
        // find the current index of which list to add on this node 
       
        res.get(curIndex).add(root.val);
        return curIndex;
    }
}
