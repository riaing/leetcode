Print a binary tree in an m*n 2D string array following these rules:

The row number m should be equal to the height of the given binary tree.
The column number n should always be an odd number.
The root node's value (in string format) should be put in the exactly middle of the first row it can be put. The column and the row where the root node belongs will separate the rest space into two parts (left-bottom part and right-bottom part). You should print the left subtree in the left-bottom part and print the right subtree in the right-bottom part. The left-bottom part and the right-bottom part should have the same size. Even if one subtree is none while the other is not, you don't need to print anything for the none subtree but still need to leave the space as large as that for the other subtree. However, if two subtrees are none, then you don't need to leave space for both of them.
Each unused space should contain an empty string "".
Print the subtrees following the same rules.
Example 1:
Input:
     1
    /
   2
Output:
[["", "1", ""],
 ["2", "", ""]]
Example 2:
Input:
     1
    / \
   2   3
    \
     4
Output:
[["", "", "", "1", "", "", ""],
 ["", "2", "", "", "", "3", ""],
 ["", "", "4", "", "", "", ""]]
Example 3:
Input:
      1
     / \
    2   5
   / 
  3 
 / 
4 
Output:

[["",  "",  "", "",  "", "", "", "1", "",  "",  "",  "",  "", "", ""]
 ["",  "",  "", "2", "", "", "", "",  "",  "",  "",  "5", "", "", ""]
 ["",  "3", "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]
 ["4", "",  "", "",  "", "", "", "",  "",  "",  "",  "",  "", "", ""]]
Note: The height of binary tree is in the range of [1, 10].

-----------------------------------------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/*
1,判断出返回list的大小：row为height，col为2^height-1
2，每个值，总是放在这一行的最中间。
*/
class Solution {
    public List<List<String>> printTree(TreeNode root) {
        int h = getHeight(root);
        String[][] tmpRes = new String[h][(int) Math.pow(2, h) - 1];
        for (String[] row : tmpRes) {
            Arrays.fill(row, "");
        }
     
        fillValue(tmpRes, root, 0, 0, tmpRes[0].length - 1);
        List<List<String>> res = new ArrayList<List<String>>();
        for (String[] row : tmpRes) {
            res.add(Arrays.asList(row));
        }
        return res;
    }
    
    private void fillValue(String[][] tmpRes, TreeNode root, int level, int start, int end) {
        if (level == tmpRes.length || root == null) {
            return;
        }
        // put root in the middle 
        int mid = start + (end - start) / 2; 
        tmpRes[level][mid] = root.val + "";
        fillValue(tmpRes, root.left, level+1, start, mid-1);
        fillValue(tmpRes, root.right, level+1, mid+1, end);     
    }
    
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0; 
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
}

----------------- 2022 两种放置方法 ----------------------------------
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
class Solution {
    public List<List<String>> printTree(TreeNode root) {
        // 1. 找height
        int height = getHeight(root);
        int width =  (int) Math.pow(2, height+1) - 1; 
        // 2. 递归放到相应的位子
        String[][] matrix = new String[height+1][width];
        Arrays.stream(matrix).forEach(row -> Arrays.fill(row, ""));
        
        // 方法1： 按题目给的提示计算
        int col = (matrix[0].length - 1) / 2;
        // place(matrix, 0, col, h, root); 
        
        // 方法2： 左子树的位置就是从root 分开，前一半的中间。 如果root放在mid， 左子树就放在（0 + (mid -1)） / 2的地方  https://www.youtube.com/watch?v=ipIL1qVAazk&ab_channel=HuaHua 
        place2(matrix, 0, 0, width-1, root);
        
        // System.out.println(Arrays.deepToString(matrix));


        return Arrays.stream(matrix)
                               .map(Arrays::asList)
                               .collect(Collectors.toList());
    }
    
    private void place(String[][] matrix, int r, int c, int h, TreeNode root) {
        if (root == null) {
            return;
        }
        // place root value 
        matrix[r][c] = root.val + "";
        if (root.left != null) {
            place(matrix, r+1, c - (int) Math.pow(2, h-r-1), h, root.left); // 填充范围就是左边一半的中间
        }
        if (root.right != null) {
             place(matrix, r+1, c + (int) Math.pow(2, h-r-1), h, root.right);
        }
    }
    
    // 记这种解法
    private void place2(String[][] matrix, int r, int left, int right, TreeNode root) {
        if (root == null) {
            return;
        }
        // place root value 
        int mid = (left + right) / 2; 
        matrix[r][mid] = root.val + "";
        
       
        if (root.left != null) {
            place2(matrix, r+1, left, mid-1, root.left); // 填充范围就是左边一半的中间
        }
        if (root.right != null) {
             place2(matrix, r+1, mid+1, right, root.right);
        }
    }
    
    
    private int getHeight(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }
}
