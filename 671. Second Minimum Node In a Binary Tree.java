Given a non-empty special binary tree consisting of nodes with the non-negative value, where each node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this node's value is the smaller value among its two sub-nodes.

Given such a binary tree, you need to output the second minimum value in the set made of all the nodes' value in the whole tree.

If no such second minimum value exists, output -1 instead.

Example 1:

Input: 
    2
   / \
  2   5
     / \
    5   7

Output: 5
Explanation: The smallest value is 2, the second smallest value is 5.
 

Example 2:

Input: 
    2
   / \
  2   2

Output: -1
Explanation: The smallest value is 2, but there isn't any second smallest value.

----------worst case O(n), but only when left and right tree has the sama value as root ------------------------------
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
 public int findSecondMinimumValue(TreeNode root) {
      if (root == null || (root.left == null && root.right == null)) {
        return -1;
      }
      
      if (root.left.val == root.val && root.right.val != root.val) {
        int secondMinVal = findSecondMinimumValue(root.left); 
        return Math.min(secondMinVal == -1 ? Integer.MAX_VALUE : secondMinVal, root.right.val);
      }

      else if (root.right.val == root.val && root.left.val != root.val) {
        int secondMinVal = findSecondMinimumValue(root.right); 
        return Math.min(secondMinVal == -1 ? Integer.MAX_VALUE : secondMinVal, root.left.val);
      }
     // tree like [2],[2,2], [2,3, null, null] -> should return 3 
      else {
        int leftSecondMinVal = findSecondMinimumValue(root.left);
        int rightSecondMinVal = findSecondMinimumValue(root.right); 
        if (leftSecondMinVal == -1 && rightSecondMinVal == -1) {
          return -1; 
        }
        else if (leftSecondMinVal == -1) {
          return rightSecondMinVal;
        }
        else if (rightSecondMinVal == -1) {
          return leftSecondMinVal;
        }
        else {
          return Math.min(leftSecondMinVal, rightSecondMinVal);
        }
        
      }
      
      
        
    }
}

 --------------------2022 -------------------------------------------
     
     Given a non-empty special binary tree consisting of nodes with the non-negative value, where each node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this node's value is the smaller value among its two sub-nodes. More formally, the property root.val = min(root.left.val, root.right.val) always holds.

Given such a binary tree, you need to output the second minimum value in the set made of all the nodes' value in the whole tree.

If no such second minimum value exists, output -1 instead.

 

 

https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/ 

Example 1:


Input: root = [2,2,5,null,null,5,7]
Output: 5
Explanation: The smallest value is 2, the second smallest value is 5.
Example 2:


Input: root = [2,2,2]
Output: -1
Explanation: The smallest value is 2, but there isn't any second smallest value.
 

Constraints:

The number of nodes in the tree is in the range [1, 25].
1 <= Node.val <= 231 - 1
root.val == min(root.left.val, root.right.val) for each internal node of the tree.

------------------------ 用queue ----------------------------------------------------------

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
/*
重点： 1. root是整棵树的min  2. 如果cur >= secondMin了，整棵subtree都不用走
转成code就做两件事： 1. 跟新secondmin by secondMin > root.val && root.val != min。跟新完后subtree也不用走了 2.只要走cur == min（就是root.val)的subtree
解法1： priorityQueue来维持min和second min。注意：要写code让q不含重复
解法2：两个variable min和second min来代替queue
*/
class Solution {
    public int findSecondMinimumValue(TreeNode root) {
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((a, b) -> b - a) {
               @Override
                public boolean offer(Integer e) 
                {
                    boolean isAdded = false;
                    if(!super.contains(e))
                    {
                        isAdded = super.offer(e);
                    }
                    return isAdded;
    }
        };
        helper(root, q);
        if (q.size() == 1) {
            return -1;
        }
        return q.peek();
    }
    
    private void helper(TreeNode root, PriorityQueue<Integer> q) {
        if (root == null) {
            return;
        }
        if (q.size() < 2 || root.val < q.peek()) { // 跟新secondMin
            q.offer(root.val);
            if (q.size() > 2) {
                q.poll();
            }
            if (root.left != null) { // 只有当root,val = globalMin时才会走完递归，这里尽管看着是递归到了 secondMin的subtree，其实进到下一层后就会停止
                helper(root.left, q);
                helper(root.right, q);
            }
        }
    }
}

 --------------------- 不用queue ---------------
 
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
/*
重点： 1. root是整棵树的min  2. 如果cur >= secondMin了，整棵subtree都不用走
转成code就做两件事： 1. 跟新secondmin by secondMin > root.val && root.val != min。跟新完后subtree也不用走了 2.只要走cur == min（就是root.val)的subtree
解法1： priorityQueue来维持min和second min。注意：要写code让q不含重复
解法2：两个variable min和second min来代替queue
*/
class Solution {
    int min = 0;
    long secondMin = Long.MAX_VALUE;  // 题目说了node可取integer 最大值
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }
        min = root.val; 
        helper(root);
       
        return secondMin == Long.MAX_VALUE ? -1 : (int) secondMin;
    }
    
    private void helper(TreeNode root) {
        if (root.val != min && secondMin > root.val) { // 跟新secondMin
            secondMin = root.val;
        }
        else if (root.val == min) {
            helper(root.left);
            helper(root.right);  // 当root 大于 min时，so all values in the subtree at \text{node}node are at least \text{node.val}node.val, so there cannot be a better candidate
        }
    }
}

--------- follow up: 求thirdMin ----------------------
    与上不同之处： 
    1. 找到第二小后还要递归找三， and要把old min2给到min3
    2. root.val == min1 或者min2时都要递归
    3. 只有找到 root.val > min2， < cur min3时才return
    class Solution {
    int min;
    long secondMin;
    long thirdMin = Long.MAX_VALUE; // [2,2,3,5,2,7,3] -> 5 
    public int findSecondMinimumValue(TreeNode root) {
        this.min = root.val; 
        this.secondMin = Long.MAX_VALUE;
        helper(root); 
        
        secondMin = secondMin == Long.MAX_VALUE ? -1 : (int) secondMin; 
        System.out.println("thirdMin " + thirdMin);
        return (int) secondMin; 
    }
    private void helper(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.val > min && root.val < secondMin) {
            thirdMin = secondMin;
            secondMin = root.val; 
            // 找到第二小之后还要继续找第三小
            helper(root.left);
            helper(root.right);
            return;
        }
        else if (root.val > secondMin && root.val < thirdMin) {
            thirdMin = root.val; 
        }
        else if (root.val == min || root.val == secondMin) { // 当root 大于 min时，so all values in the subtree at \text{node}node are at least \text{node.val}node.val, so there cannot be a better candidate
             helper(root.left);
             helper(root.right);
        } 
    }
    
}

 
