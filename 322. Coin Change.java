You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:

Input: coins = [1, 2, 5], amount = 11
Output: 3 
Explanation: 11 = 5 + 5 + 1
Example 2:

Input: coins = [2], amount = 3
Output: -1
Note:
You may assume that you have an infinite number of each kind of coin.

--------------------------------------------------DP ---------------------------------------------------------------------------
/**
dp[i]表示钱数为i时的最小硬币数的找零

m[0] = 0;
m[i] = amount+1 // 一个很大的值

回归例子1，假设我取了一个值为5的硬币，那么由于目标值是11，所以是不是假如我们知道dp[6]，那么就知道了组成11的dp值了？所以我们更新dp[i]的方法就是遍历每个硬币，如果遍历到的硬币值小于i值（比如我们不能用值为5的硬币去更新dp[3]）时，我们用 dp[i - coins[j]] + 1 来更新dp[i]，所以状态转移方程为：
dp[i] = min(dp[i], dp[i - coins[j]] + 1);
其中coins[j]为第j个硬币，而i - coins[j]为钱数i减去其中一个硬币的值，剩余的钱数在dp数组中找到值，然后加1和当前dp数组中的值做比较，取较小的那个更新dp数组。

return m[amount] 

*/
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] m = new int[amount+1];
        m[0] = 0;
        // m[i] 最多需要i个一块钱硬币，所以+1是为了表示初始值很大
        for (int i = 1; i <= amount; i++) {
            m[i] = amount + 1;
        }
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) { // 遍历每个硬币
                if (i - coins[j] >= 0) {
                    m[i] = Math.min(m[i], m[i-coins[j]] + 1);
                    
                }   
            }
        }
        return m[amount] == amount+1 ? -1 : m[amount];
    }
    
    ---------------------------------------------DFS + Memo --------------------------------------------------
    /**
DFS + memo 

*/
class Solution {
    public int coinChange(int[] coins, int amount) {
       Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        return helper(coins, amount, map);
        
    }
    private int helper(int[] coins, int amount, Map<Integer, Integer> map) {
        if (amount < 0) {
            return -1;
        }
        if (map.containsKey(amount)) {
            return map.get(amount);
        }
        int cur = amount+1;
        for (int i = 0; i < coins.length; i++) {
            int tmpNum = helper(coins, amount - coins[i], map);
            if (tmpNum >= 0) {
                cur = Math.min(cur, tmpNum+1);
            }
        }
        if (cur == amount+1) {
            cur = -1;
        }
        map.put(amount, cur);
        return map.get(amount);
    }
}
}
