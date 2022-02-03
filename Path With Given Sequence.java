https://www.educative.io/courses/grokking-the-coding-interview/m280XNlPOkn 

Given a binary tree and a number sequence, find if the sequence is present as a root-to-leaf path in the given tree.

Example 1:
1
7
9
2
9
Sequence: [1, 9, 9]Output: true Explanation: The tree has a path 1 -> 9 -> 9.
Example 2:
1
0
1
1
6
5
-------------------
注意下 sequence 长于/短于 tree path 的情况

import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
};

class PathWithGivenSequence {
  public static boolean findPath(TreeNode root, int[] sequence) {
    // corner case 
    if (root == null) {
      return sequence == null || sequence.length == 0;
    }
    return helper(root, sequence, 0);
  }

  private static boolean helper(TreeNode root, int[] sequence, int index) {
    if (root == null || index > sequence.length - 1) { // sequence is shorter then path
      return false;
    }
    if (root.val != sequence[index]) {
      return false;
    }

    if (root.left == null && root.right == null && index == sequence.length -1) {
      return true; // above if already make sure sequence[index] == root.val 
    }
    return helper(root.left, sequence, index+1) || helper(root.right, sequence, index+1);
  }

  
  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(0);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(1);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(5);

    System.out.println("Tree has path sequence: " + PathWithGivenSequence.findPath(root, new int[] { 1, 0, 7 }));
    System.out.println("Tree has path sequence: " + PathWithGivenSequence.findPath(root, new int[] { 1, 1, 6 }));
    // sequence longer than tree path 
    System.out.println("Tree has path sequence: " + PathWithGivenSequence.findPath(root, new int[] { 1, 1, 6, 7 }));
    // sequence shorter than tree path 
    System.out.println("Tree has path sequence: " + PathWithGivenSequence.findPath(root, new int[] { 1, 1}));
  }
}


