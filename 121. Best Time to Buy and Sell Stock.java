Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Note that you cannot sell a stock before you buy one.

Example 1:

Input: [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
Example 2:

Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.

  --------4.11.2021 DP 思路--------------------------------------------------
  /*
initial thoughts: 两重循环，第i天买第j天卖，取最大值。
会发现第j天卖和第j+1天卖会重复 j+1....N这段找最大值
所以可以memerization。 dp[i]: 第i天到最后一天这段时间的最大值
for loop 要反着来
dp[i] = max {dp[i+1]， v[i]}

得到这个dp后，再求max{dp[i] - v[i]} //以i天买入，今后某一天卖出的最大值。

类似，dp[i] 也可以定义为0.。。i天的最小值。那么最后就求 max{v[i] - dp[i]}// 第i天卖出，前面哪天买的最大值
*/

--------------------正常DP思路- O(n), O(n) --------------------------------------------------------------------
第一步：
m[i] : 前i个中的的最小值
m[i] = min {m[i-1], a[i]}
m[0]： a[0]
return: m[a.length-1]

第二步：可以在每次求得min[i]时，更新maxValue的值: a[i] - m[i]

class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        int[] m = new int[prices.length];
        m[0] = prices[0]; 
        for (int i = 1; i < prices.length; i++) {
            m[i] = Math.min(m[i-1], prices[i]);
            res = Math.max(res, prices[i] - m[i]);
        }
        return res;
    }
}

--------------------------space到O(1) ---------------------------------------------------------------------------
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        //这里因为m[i]只和m[i-1]有关，所以我们可以直接用一个number代替，空间降到O(1)
        // int[] m = new int[prices.length];
        int minPrice = prices[0]; 
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            res = Math.max(res, prices[i] - minPrice);
        }
        return res;
    }
}
