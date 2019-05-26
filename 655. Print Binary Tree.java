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
