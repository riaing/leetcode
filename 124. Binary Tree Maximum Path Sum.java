Given a non-empty binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

Example 1:

Input: [1,2,3]

       1
      / \
     2   3

Output: 6
Example 2:

Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42
----------------------自己的写法，对比path sum III------------------------------------------------------------------------
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
分两种情况：一是只在左右树，二是经过了root节点的single path
Time: O(n^2),对每个node，都要call findSinglePathMaxSum（），所以是n + n-1 + n-2 +....1 = n^2 
*/
class Solution {
    public int maxPathSum(TreeNode root) {
           // 用于判断corner case 
        if (root == null) {
            return 0;
        }
        return helper(root);
    }

    private int helper(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        //1, 看不包含root的左右子树的max
        int val = Math.max(helper(root.left), helper(root.right));
        // 2, 找一条cross root的path，那么root左右两边的path必须是single path，
        
        //先找一定经过left的max single path，如果是负数，则不需要它，设为0即可
        int maxLeft = Math.max(findSinglePathMaxSum(root.left), 0);
        //再找一定经过right的max single path，如果是负数，则不需要它，设为0即可
        int maxRight= Math.max(findSinglePathMaxSum(root.right), 0);
        
        // 这里如果maxLeft，maxRight = 0，说明不需要左右子树的值，则直接返回root的值
        int crossRoot = maxLeft + maxRight + root.val; 
       
        
        return Math.max(val, crossRoot);   
    }
    
    
    // O(n) find a max-value single path that must start from root, but not necessary go to leave
    private int findSinglePathMaxSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int max = Math.max(findSinglePathMaxSum(root.left),  findSinglePathMaxSum(root.right));
        //这里的 Math.max(max, 0)说明如果左右子树的max sum都很小，那么直接返回root。这一步确保了这条single path不用走到底。如果题目改成为求一条root-leave的max single path，就不需要这个max的判断
        return Math.max(max, 0) + root.val;  
    }
}

----------------------------O（n）看看大神的解法-----------------------------------------------------
/**
0, root == null在主程序中单独处理
辅助程序中，
1，conquer时 root must be used
2，updated global answer with using both children 
3, returen longest path with only one children 给 root
*/       
       
public class Solution {
    int maxValue;
    
    public int maxPathSum(TreeNode root) {
        if (root == null) {
               return 0; 
        }
           
        maxValue = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxValue;
    }
    
    private int maxPathDown(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        maxValue = Math.max(maxValue, left + right + node.val); //每次更新max
        return Math.max(left, right) + node.val; //注意这里的return 
    }
}

------------------ 2022.3.24 DC ----------------------------------
       
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

       Time/space O(n)
class Node {
    TreeNode treeNode;
    int maximumPathSum;
    int pathSum;
    public Node(TreeNode treeNode, int maximumPathSum, int pathSum) {
        this.treeNode = treeNode;
        this.maximumPathSum = maximumPathSum;
        this.pathSum = pathSum;
    }
}


class Solution {
    public int maxPathSum(TreeNode root) {
        Node res = helper(root);
        return res.maximumPathSum;
    }
    
    private Node helper(TreeNode root) {
        if (root == null) {
            return new Node(null, Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        
        if (root.left == null && root.right == null) {
            return new Node(root, root.val, root.val);
        }
        
        Node left = helper(root.left);
        Node right = helper(root.right);
        
        int crossRootMaxSum = root.val + (left.pathSum > 0 ? left.pathSum : 0) + (right.pathSum > 0 ? right.pathSum : 0);  
        int curMaxPathSum = Math.max(Math.max(left.maximumPathSum, right.maximumPathSum), crossRootMaxSum);
        
        // 计算cur path sum：可包括左右子树，或者不包只取root 
        int childrenLargerPathSum = Math.max(left.pathSum, right.pathSum);
        int curPathSum = root.val + (childrenLargerPathSum > 0 ? childrenLargerPathSum : 0); 
        
        return new Node(root, curMaxPathSum, curPathSum);
        
    }
}
