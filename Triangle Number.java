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
