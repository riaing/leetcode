Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

 

Example 1:


Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
Example 2:


Input: matrix = [["0","1"],["1","0"]]
Output: 1
Example 3:

Input: matrix = [["0"]]
Output: 0
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is '0' or '1'.

--------------------------- DP ----------------------------
/*
从一个小正方形，怎么推到大正方形
dp[i][j] 以i，j为右下角的正方形的最大面积的边长
dp[i][j] = min(i,j-1 ; i-1, j; i-1, j-1) + 1 , if matrix[i][j] = 1
           0 if matrix[i][j] = 0
初始：第一行和第一列就是matrix的值           
return 任意i，j组成的最大面积            
*/
class Solution {
    public int maximalSquare(char[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];  // 以i，j为右下角的正方形的最大面积
       
        // 注意返回值也要考虑边界，从这里算起
        int maxRes = 0; 
        for(int i = 0; i < matrix.length; i++) {
            dp[i][0] = matrix[i][0] - '0';
            maxRes = Math.max(maxRes, dp[i][0]);
        }
        for (int j = 0; j < matrix[0].length; j++) {
            dp[0][j] = matrix[0][j] - '0';
            maxRes = Math.max(maxRes, dp[0][j]);
        }
       
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                dp[i][j] = 0; 
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                }
                // System.out.println("location " + i  + " " + j + " res: " + dp[i][j]);
                maxRes = Math.max(maxRes, dp[i][j]);
            }
        }
        return maxRes* maxRes;    
    }
}
