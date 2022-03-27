https://leetcode.com/problems/unique-binary-search-trees/ 

题目1： 
Given an integer n, return the number of structurally unique BST''s (binary search trees) which has exactly n nodes of unique values from 1 to n.

Input: n = 3
Output: 5
Example 2:

Input: n = 1
Output: 1

1 <= n <= 19


---------------------------------- recursion + memo -----------------------------
O(n^2) 
class Solution {
    Map<Integer, Integer> choiceMap = new HashMap<Integer, Integer>();
    
    public int numTrees(int n) {
        if (choiceMap.containsKey(n)) {
            return choiceMap.get(n);
        }
        
        if (n == 0 || n == 1) { // 把n=0时返回1，那么后面 left*right时才不会错
            choiceMap.put(n, 1);
            return 1;
        }
        
        int res = 0;
        // how many nodes gives to left tree 
        for (int i = 0; i <= n - 1; i++) { // root ocupied 1 
            int leftChoices = numTrees(i);
            int rightChoices = numTrees(n-1-i);
            // if (leftChoices == 0) { //如果把n=0返回0则需要这个特殊判断
            //     res += rightChoices;
            // }
            // if (rightChoices == 0) {
            //     res += leftChoices;
            // }

                res = res + leftChoices*rightChoices;
            
            
        }
        choiceMap.put(n, res);
        return res; 
    }
}
-------------------------- DP ------------------------------------------------
class Solution {
/*
Time O(n^2)
space O(n)
*/
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = i-1; j >=0; j--) {
                dp[i] += dp[j] * dp[i-1-j]; //左子树的个数 + 减去root后，右子树的个数
            } 
        }
        return dp[n];
        
    }
} 

================== 题目二： return all BST 

Given a number ‘n’, write a function to return all structurally unique Binary Search Trees (BST) that can store values 1 to ‘n’?

Example 1:

Input: 2
Output: List containing root nodes of all structurally unique BSTs.
Explanation: Here are the 2 structurally unique BSTs storing all numbers from 1 to 2: 1->2 , 2-> 1
  
  
import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
};

class UniqueTrees {
  static Map<Integer, List<TreeNode>> choiceMap = new HashMap<Integer, List<TreeNode>>();

  public static List<TreeNode> findUniqueTrees(int n) {
    if (choiceMap.containsKey(n)) {
            return choiceMap.get(n);
    }
    List<TreeNode> result = new ArrayList<>();
    if (n == 0) {
      result.add(null);
      choiceMap.put(n, result);
      return result;
    }
    
    if (n == 1) {
      result.add(new TreeNode(1));
      choiceMap.put(n, result);
      return result;
    } 

    for (int i = 0; i < n; i++) { // 找左右子树。因为root占了一个node，所以循环不能到n
      List<TreeNode> left = findUniqueTrees(i);
      List<TreeNode> right = findUniqueTrees(n-1-i);
      for (TreeNode l : left) {
        for (TreeNode r: right) {
          TreeNode root = new TreeNode(n);
          root.left = l;
          root.right = r;
          result.add(root);
        }
      }
      choiceMap.put(n, result);
    }
    return result;
  }

  public static void main(String[] args) {
    List<TreeNode> result = UniqueTrees.findUniqueTrees(2);
    System.out.println("Total trees: " + result.size());
    for (TreeNode res : result) {
        System.out.println("root " + res.val + " left " + (res.left == null ? "null" : res.left.val) +
         " right " + (res.right == null ? "null" : res.right.val));
    }
  }
}  

