Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.


----------------------------D&C + recursion----------------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
0, root == null在主程序中单独处理
辅助程序中，
1，conquer时 root must be used
2，updated global answer with using both children 
3, returen longest path with only one children 给 root

Time Complexity:O(N). We visit every node once.

Space Complexity: O(N), the size of our implicit call stack during our depth-first search.
**/
class Solution {
    int max = Integer.MIN_VALUE; 
    public int diameterOfBinaryTree(TreeNode root) {
        // 0, root == null在主程序中单独处理 ：corner case [] 
        if (root == null) {
            return 0;
        }
        helper(root);
        return max;
    }
         
    -------------方法一： 这是用depth（node个数）来想 --------------------------------------
    //helper还是找一个single path，通过一个global value max来判断global最长路径。
    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftDepth = helper(root.left);
        int rightDepth = helper(root.right);
            
        //通过给出的例子来思考：如果左边返回2，右边返回1，那么整体周长就是2+1.。。
        // 1，conquer时 root must be used
        // 2，updated global answer with using both children 
        max = Math.max(leftDepth+rightDepth, max);
        
        //3. return depth(nodes) :
        return Math.max(leftDepth, rightDepth) + 1;
    }
   ---------------方法二：用edge的个数来想-------------------------------
   private int pathDown(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }
        int left = pathDown(root.left);
        int right = pathDown(root.right);
        int curLong = (root.left == null ? 0 : (1 + left))  + (root.right == null ? 0 : (1 + right));  
        value = Math.max(curLong, value);
        return Math.max(left, right) + 1;
        
    }
}

---------------------------- 3.24.2022, 用新DS来存longest path + diameter ---------------------------------------------------------------------------
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
Space: o(n), Time: O(n)
*/

class Node {
    TreeNode treeNode;
    int diameter;
    int longestPath; 
    public Node (TreeNode treeNode, int diameter, int longestPath) {
        this.treeNode = treeNode;
        this.diameter = diameter;
        this.longestPath = longestPath;
    }
}


class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        Node res = helper(root);
        return res.diameter;
    }
    
    public Node helper(TreeNode root) {
        if (root == null) {
             return new Node(root, -1, -1);
        }
        
        if (root.left == null && root.right == null) {
            return new Node(root, 0, 0);
        }
        
        Node leftRes = helper(root.left);
        Node rightRes = helper(root.right);
        
        int possibleDiameter = (leftRes.longestPath == -1 ? 0 : (leftRes.longestPath + 1)) + (rightRes.longestPath == -1 ? 0 : (rightRes.longestPath + 1)); 
        int diameter = Math.max(possibleDiameter, Math.max(leftRes.diameter, rightRes.diameter));
        
        int longestPath = Math.max(leftRes.longestPath, rightRes.longestPath) + 1;
        return new Node(root, diameter, longestPath); 
    }
}

------------- 题目定义改一改 The diameter of a tree is the number of nodes on the longest path between any two leaf nodes --------------------------
          
  Diameter 由node 数而不是 path 决定。 code只要小改
  
  
  class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
};

class Node {
    TreeNode treeNode;
    int diameter;
    int longestPath; 
    public Node (TreeNode treeNode, int diameter, int longestPath) {
        this.treeNode = treeNode;
        this.diameter = diameter;
        this.longestPath = longestPath;
    }
}

class TreeDiameter {

  public static int findDiameter(TreeNode root) {
    Node res = helper(root);
        return res.diameter;
  }
  
  private static Node helper(TreeNode root) {
        if (root == null) {
             return new Node(root, 0, 0);
        }
        
        if (root.left == null && root.right == null) {
            return new Node(root, 1, 1);
        }
        
        Node leftRes = helper(root.left);
        Node rightRes = helper(root.right);
        
        // int possibleDiameter = (leftRes.longestPath == -1 ? 0 : (leftRes.longestPath + 1)) + (rightRes.longestPath == -1 ? 0 : (rightRes.longestPath + 1)); 
        int possibleDiameter = leftRes.longestPath + rightRes.longestPath + 1; 
        int diameter = Math.max(possibleDiameter, Math.max(leftRes.diameter, rightRes.diameter));
        
        int longestPath = Math.max(leftRes.longestPath, rightRes.longestPath) + 1;
        return new Node(root, diameter, longestPath); 
    }



  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.right.left = new TreeNode(5);
    root.right.right = new TreeNode(6);
    System.out.println("Tree Diameter: " + TreeDiameter.findDiameter(root));
    root.left.left = null;
    root.right.left.left = new TreeNode(7);
    root.right.left.right = new TreeNode(8);
    root.right.right.left = new TreeNode(9);
    root.right.left.right.left = new TreeNode(10);
    root.right.right.left.left = new TreeNode(11);
    System.out.println("Tree Diameter: " + TreeDiameter.findDiameter(root));
  }
}
