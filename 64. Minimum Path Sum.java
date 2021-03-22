Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example:

Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.

----------------------写法一，将边界的两条边当初始值处理（推荐) ------------------------------------------------
class Solution {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int[][] m = new int[grid.length][grid[0].length];
        m[0][0] = grid[0][0];
        for (int i = 1; i < grid.length; i++) {
            m[i][0] = m[i-1][0] + grid[i][0];   
        }
        for (int i = 1; i < grid[0].length; i++) {
            m[0][i] = m[0][i-1] + grid[0][i];   
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                m[i][j] = Math.min(m[i-1][j], m[i][j-1]) + grid[i][j];
            }
        }
        return m[grid.length-1][grid[0].length-1];
    }
}

------------------写法二，用if将边界的两条边特殊处理 -------------------------------------------------------- 
  
  class Solution {
    public int minPathSum(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
           return 0;
        }
        int[][] dp = new int[grid.length][grid[0].length];
        
        dp[0][0] = grid[0][0];
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE; 
            
                if (i - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][j]);
                }
                if (j-1>= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j-1]);
                }
                dp[i][j] += grid[i][j];
            }
        }
        
        return dp[grid.length-1][grid[0].length -1];
        
    }
}
  
