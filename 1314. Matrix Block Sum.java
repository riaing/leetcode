Given a m x n matrix mat and an integer k, return a matrix answer where each answer[i][j] is the sum of all elements mat[r][c] for:

i - k <= r <= i + k,
j - k <= c <= j + k, and
(r, c) is a valid position in the matrix.
 

Example 1:

Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1
Output: [[12,21,16],[27,45,33],[24,39,28]]
Example 2:

Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2
Output: [[45,45,45],[45,45,45],[45,45,45]]
 

Constraints:

m == mat.length
n == mat[i].length
1 <= m, n, k <= 100
1 <= mat[i][j] <= 100

--------------- 2D preSum -------------------
/*
题目给了中心(i,j)和半径k，可以求出左上角的坐标(i-k-1)(j-k-1) 注意一定要减1，是不包括左上角的cell，和右下角的坐标(i+k)(j+k) 
然后带入基础题：已知两点坐标，求长方形面积即可

注意此题要保证左上和右下在matrix之内，所以做min，max判断一下
*/
class Solution {
    public int[][] matrixBlockSum(int[][] mat, int k) {
        // 1. preSum of (0,0) -> (i,j)的长方形
        int[][] dp = new int[mat.length+1][mat[0].length+1]; 
        preSum(mat, dp);
        
        
        int[][] res = new int[mat.length][mat[0].length];
        // 2. 求出左上角， 右下角的左边
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                int[] leftCorner = new int[2];
                leftCorner[0] = Math.max(0, i - k) - 1;
                leftCorner[1] = Math.max(0, j - k) - 1; // 看题！，是不包括左上角的那个corner
                
                int[] rightCorner = new int[2];
                rightCorner[0] = Math.min(mat.length-1, i + k);
                rightCorner[1] = Math.min(mat[0].length-1, j + k);
                
                
                // 算面积
                res[i][j] = dp[rightCorner[0] + 1][rightCorner[1] + 1] -
                                dp[leftCorner[0] + 1][rightCorner[1] + 1] -
                                dp[rightCorner[0] + 1][leftCorner[1] + 1] + 
                                dp[leftCorner[0] + 1][leftCorner[1] + 1];
                
             }
        }
        return res; 
    }
    
    private void preSum(int[][] mat, int[][] dp) {
        for (int i = 1; i <= mat.length; i++) {
            for (int j = 1; j <= mat[0].length; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + mat[i-1][j-1];
            }
        }

        return; 
    }
}
