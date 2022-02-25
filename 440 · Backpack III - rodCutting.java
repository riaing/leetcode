Description
Given n kinds of items, and each kind of item has an infinite number available. The i-th item has size A[i] and value V[i].

Also given a backpack with size m. What is the maximum value you can put into the backpack?

Wechat reply the【BAT】get the latest frequent Interview questions of ByteDance, Alibaba, etc. (wechat id :jiuzhang15)

You cannot divide item into small pieces.
Total size of items you put into backpack can not exceed m.
Example
Example 1:

Input: A = [2, 3, 5, 7], V = [1, 5, 2, 4], m = 10
Output: 15
Explanation: Put three item 1 (A[1] = 3, V[1] = 5) into backpack.
Example 2:

Input: A = [1, 2, 3], V = [1, 2, 3], m = 5
Output: 5
Explanation: Strategy is not unique. For example, put five item 0 (A[0] = 1, V[0] = 1) into backpack.
    
----------- 马甲 rod cutting ---------------------------------
    Given a rod of length ‘n’, we are asked to cut the rod and sell the pieces in a way that will maximize the profit. We are also given the price of every piece of length ‘i’ where ‘1 <= i <= n’.

Example:

Lengths: [1, 2, 3, 4, 5]
Prices: [2, 6, 7, 10, 13]
Rod Length: 5

Let’s try different combinations of cutting the rod:

Five pieces of length 1 => 10 price
Two pieces of length 2 and one piece of length 1 => 14 price
One piece of length 3 and two pieces of length 1 => 11 price
One piece of length 3 and one piece of length 2 => 13 price
One piece of length 4 and one piece of length 1 => 12 price
One piece of length 5 => 13 price

This shows that we get the maximum price (14) by cutting the rod into two pieces of length ‘2’ and one piece of length ‘1’.

------------------------------------------------- 
public class Solution {
    /**
     * @param A: an integer array
     * @param V: an integer array
     * @param m: An integer
     * @return: an array
     */
    public int backPackIII(int[] A, int[] V, int m) {
        // corner case 
        if (A.length == 0 || V.length == 0) {
            return 0;
        }
        int[][] dp = new int[A.length][m+1];
        for (int i = 0; i < A.length; i++) {
            for (int j = 1; j <= m; j++) {
                if (i == 0) {
                    int cnt = 1;
                    while (cnt*A[i] <= j) {
                        dp[0][j] = cnt*V[0];
                        cnt++;
                    }
                    // System.out.println("dp[0[j " + j + " " + dp[0][j]);
                }
                else {
                    dp[i][j] = dp[i-1][j];
                    int cnt = 1; 
                    while (A[i] * cnt <= j) {
                        dp[i-1][j-A[i]] + V[i] 
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][j - cnt*A[i]] + cnt*V[i]);
                        cnt++;
                    }
                }
               
            }
        }
        return dp[A.length - 1][m];
    }
} 

------------- Improved solution -------------------------------------------------------------------
public class Solution {
    /**
     * @param A: an integer array
     * @param V: an integer array
     * @param m: An integer
     * @return: an array
     */
    public int backPackIII(int[] A, int[] V, int m) {
        // corner case 
        if (A.length == 0 || V.length == 0) {
            return 0;
        }
        int[][] dp = new int[A.length][m+1];
        for (int i = 0; i < A.length; i++) {
            for (int j = 1; j <= m; j++) {
                if (i == 0) {
                    int cnt = 1;
                    while (cnt*A[i] <= j) {
                        dp[0][j] = cnt*V[0];
                        cnt++;
                    }
                }
                else {
                    dp[i][j] = dp[i-1][j];
                    if (j - A[i] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i][j - A[i]] + V[i]);
                    }
                }
               
            }
        }
        return dp[A.length - 1][m];
    }
}
