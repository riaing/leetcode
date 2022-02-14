Description
There are n items and a backpack with size m. Given array A representing the size of each item and array V representing the value of each item.

What's the maximum value can you put into the backpack?

Wechat reply the 【125】 get the latest frequent Interview questions . (wechat id : jiuzhang15)

A[i], V[i], n, m are all integers.
You can not split an item.
The sum size of the items you want to put into backpack can not exceed m.
Each item can only be picked up once
m <= 1000m<=1000\
len(A),len(V)<=100len(A),len(V)<=100

Example
Example 1:

Input:

m = 10
A = [2, 3, 5, 7]
V = [1, 5, 2, 4]
Output:

9
Explanation:

Put A[1] and A[3] into backpack, getting the maximum value V[1] + V[3] = 9

Example 2:

Input:

m = 10
A = [2, 3, 8]
V = [2, 5, 8]
Output:

10
Explanation:

Put A[0] and A[2] into backpack, getting the maximum value V[0] + V[2] = 10

Challenge
O(nm) memory is acceptable, can you do it in O(m) memory?
  
---------------- DP 2D --------------------------
  public class Solution {
    /**
     * @param m: An integer m denotes the size of a backpack
     * @param A: Given n items with size A[i]
     * @param V: Given n items with value V[i]
     * @return: The maximum value
     */
    public int backPackII(int m, int[] A, int[] V) {
        int[][] dp = new int[A.length][m+1];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j <= m; j++) {
                if (j == 0) {
                    dp[i][0] = 0;
                }
                if (i == 0) {
                    if (A[i] <= j) {
                        dp[0][j] = V[0];
                    }
                }
                else {
                    if (A[i] <= j) {
                        dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j - A[i]] + V[i]);
                    }
                    else {
                        dp[i][j] = dp[i-1][j];
                    }
                }
               
            }
        }
        return dp[A.length - 1][m];
    }
}

