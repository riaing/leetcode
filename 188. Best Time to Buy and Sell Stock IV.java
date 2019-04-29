Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.

Note:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

Example 1:

Input: [2,4,1], k = 2
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
Example 2:

Input: [3,2,6,5,0,3], k = 2
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
             Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.


注意这是这类题的general写法，stock III就是个例子
------------------------------------------------------------------------------------------------------
/*
m[i][j] : 第j天最多i次transaction的最大利益。TIMe:O(k * n)
*/
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] m = new int[k+1][prices.length+1];
        for (int i = 1; i <= k; i++) {
            int maxDiff = -prices[0];
            for (int j = 2; j <= prices.length; j++) {
                m[i][j] = Math.max(m[i][j-1], prices[j-1] + maxDiff); // 注意因为price是0base，所以prices[j-1]，但理解上来说就是第j天的价格
                maxDiff = Math.max(maxDiff, m[i-1][j] - prices[j-1]); 
            }
        }
        return m[k][prices.length];
    }
}

------省空间的rotational array ---------------------
/*
m[i][j] : 第j天最多i次transaction的最大利益。TIMe:O(k * n)
*/
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int[][] m = new int[2][prices.length+1];
        int oldRow = 0;
        for (int i = 1; i <= k; i++) {
            int newRow = 1 - oldRow;
            int maxDiff = -prices[0];
            for (int j = 2; j <= prices.length; j++) {
                m[newRow][j] = Math.max(m[newRow][j-1], prices[j-1] + maxDiff); // 注意因为price是0base，所以prices[j-1]，但理解上来说就是第j天的价格
                maxDiff = Math.max(maxDiff, m[oldRow][j] - prices[j-1]); 
            }
            oldRow = newRow;
        }
        return m[oldRow][prices.length];
    }
}
