Given a rectangle of size n x m, return the minimum number of integer-sided squares that tile the rectangle.

 

Example 1:



Input: n = 2, m = 3
Output: 3
Explanation: 3 squares are necessary to cover the rectangle.
2 (squares of 1x1)
1 (square of 2x2)
Example 2:



Input: n = 5, m = 8
Output: 5
Example 3:



Input: n = 11, m = 13
Output: 6
 

Constraints:

1 <= n, m <= 13

-------------------------------- dfs -------------------------------------
/*
如果能每次一刀切，那么DP可做。Ex3是这个数量级内的唯一不能分割的情况
 两种切法：1， 竖着切 2.横着切。变成两个子问题
Time： 每次切割是线性： O（mn*max(m,n) 

dp[i][j] = min squares to fill i,j的长方形
  
  
  https://www.youtube.com/watch?v=2QRUgAT7sGc 
*/
class Solution {
    int totalArea; 
    int row;
    int col;
    int minNum;
    // 1. DP 解，无法解决
    public int tilingRectangle(int n, int m) {
        totalArea = m*n; 
        row = n;
        col = m; 
        minNum = n*m;
        return dfs(n, m);
    }
    
//     private int dp(int n, int m) {

//         // 1. 特殊处理11，13的case
        
//         dp[i][i] = 0 
//         for (int r = 1; r <= i/2; r++) {
            
//         }
        
//         for (int c = 1; c <= j/2; c++) {
//             dp[i][j] = min(dp[i][c], dp[i][j-c]) // 竖着切 
//         }
        
//         return dp[n][m]; 
//     }
    
    /*
    DFS, 填充：按col or row
    create int[m][n] visited     
    */
    private int dfs(int n, int m) {
        //从低往上填，高度数组
        boolean[][] visited = new boolean[n][m];
        int res = 0;
        helper(visited, 0, res);
        return minNum;  
    }
    
    private void helper(boolean[][] visited, int curArea, int res) {
        if (res >= minNum) {
            return; 
        }
        // all filled 
        if (curArea == totalArea) {
            minNum = Math.min(minNum, res);
            return; 
        }
        // find the top left start point 
        int[] start = find(visited); 
        for (int i = Math.min(row - start[0], col - start[1]); i >= 1; i--) {
            if (!fill(visited, start, i, true)) {
                break;   
            }
            helper(visited, curArea + i*i, res+1);
            fill(visited, start, i, false);
        }
    }
    
    // false 则不能fill
    private boolean fill(boolean[][] visited, int[] start, int len, boolean fill) {
        for (int i = start[0]; i < start[0] + len; i++) {
            for (int j = start[1]; j < start[1] + len; j++) {
                if (fill && visited[i][j]) {
                    return false; 
                }
            }
        }
        // 现在可以fill
        for (int i = start[0]; i < start[0] + len; i++) {
            for (int j = start[1]; j < start[1] + len; j++) {
                if (fill) {
                    visited[i][j] = true;
                }
                else {
                    visited[i][j] = false; 
                }
            }
        }
        return true;
    }
    
    private int[] find(boolean[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                if (!visited[i][j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1,-1}; 
    }
    
    
}
