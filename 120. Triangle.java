Given triangle number，every number can move down right or down left to next level's number. Find the maximum path sum from top to bottom

    1
   2  3
 4   5   6 
 表现形式为2D array[[1 0 0 0], [2 3 0 0], [4 5 6 0]], return 10(1+3+6)
 
 
-----------详细的各种解法见Eclipse里Triangle Number--------------------------------------------------------------------
/** This is DP solution with maintainning a 2D array of two row, h columns. because m[i,j] = max (m[i-1, j-1], m[i-1, j]), every row is only related
 * to the previous row, so we only store the information of previous row and current row. 
 * and we use a int oldRow to indicate which is the previous row.
 * time is 1+2+3...+h = h^2/2 = O（h^2）space is O(2h)

 */ 
public class Solution8DPAdvance {
    
    public long findMaxSum(int[][] input) {
        long[][] maxSum = new long[2][input[0].length];
        maxSum[0][0] = input[0][0];
        int oldRow = 0;
        for (int i = 1; i < input.length; i++) {
            int newRow = 1 - oldRow;
            maxSum[newRow][0] = maxSum[oldRow][0] + input[i][0];
            maxSum[newRow][i] =  maxSum[oldRow][i - 1] + input[i][i];
            for (int j = 1; j < i; j++) {
                maxSum[newRow][j] = Math.max(maxSum[oldRow][j - 1], maxSum[oldRow][j]) + input[i][j];
            }
            oldRow = newRow;
        }
        long finalMaxSum = maxSum[oldRow][0];
        for (int j = 1; j < maxSum[oldRow].length; j++) {
            finalMaxSum = Math.max(finalMaxSum, maxSum[oldRow][j]);
        }
        return finalMaxSum;
    }
}

----------------- 求最小值 --------------------------------------------------------------------------------------
    Given a triangle array, return the minimum path sum from top to bottom.

For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

    
    class Solution {
   /**
   
  dp[I][J] = min(dp[i-1][j], dp[i-1][j-1]) + a[i][j]
   */
    public int minimumTotal(List<List<Integer>> triangle) {
        int lastRowLength = triangle.get(triangle.size()-1).size(); 
        int rows = triangle.size();
        // 滚动数组省空间，每次都%2
        int[][] dp = new int[2][lastRowLength]; 
            
        
        //This is not needed if 边界问题处理对了
        // for (int i = 0; i < rows; i++) {
        //     for (int j = 0; j < lastRowLength; j++) {
        //         dp[i][j] = Integer.MAX_VALUE;
        //     }
        // }
        dp[0][0] = triangle.get(0).get(0);
        
        
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[i%2][j] = Integer.MAX_VALUE;
                if (j - 1 >= 0) {
                    dp[i%2][j] = Math.min(dp[i%2][j], dp[(i-1) %2][j-1]);
                }
                if (j < triangle.get(i).sizeå() -1) {
                    dp[i%2][j] = Math.min(dp[i%2][j], dp[(i-1) %2][j]);
                }
                dp[i%2][j] += triangle.get(i).get(j);
                
                
            }
        }
        
        int minVal = dp[(rows-1)%2][0];
        for (int j = 1; j < lastRowLength; j++) {
            minVal = Math.min(minVal, dp[(rows-1) % 2][j]);
        }
        return minVal;
        
    }
}
