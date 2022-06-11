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

    
    -----------------03/31/2021 DP with thougths, 最优化的解还是见下一个--------------------------------------------------------
    /**

dp[i] -> amount为i时最少硬币数量
dp[0] = 0
dp[i] = min{dp[i-k] + 1}, k belongs to coins[]

优化1： 因为amount <= 10*4, 所以初始化时不需要为MAX_VALUE, 可以为amount+1（因为最多就需要amount个1块钱硬币）
*/


class Solution {
    public int coinChange(int[] coins, int amount) { 
        // if (amount == 0) { // 优化2：0可以合进来
        //     return 0;
        // }
        
        int[] dp = new int[amount+1];
        // initialization 
        for (int i = 0; i <= amount; i++) {
            // dp[i] = Integer.MAX_VALUE;
            dp[i] = amount+1;
        }
        for (int i = 0; i <coins.length; i++) { //这段其实可以不要
            if (coins[i] <= amount) {
                  dp[coins[i]] = 1;
            }
        }
        dp[0] = 0; // 优化2：0可以合进来 
        
        for (int i = 1; i <= amount; i++) {
            // if (dp[i] != Integer.MAX_VALUE) { //优化3：不需要特殊处理硬币本身的amount
            //     continue;
            // }
            
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] < 0){
                    continue; // the coin array is not sort! 
                }
                dp[i] = Math.min(dp[i], dp[i-coins[j]]+1); //优化1 
            }
             
            // dp[i] = dp[i] == Integer.MAX_VALUE ? Integer.MAX_VALUE : dp[i] + 1; // 优化1
         
        }
        return dp[amount] == amount+1 ? -1 : dp[amount];
    }
}
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

-------------------- 2022.2.26 + track path sol 1: curTake 记录 i，j 取几个。----------------------------------------------------------- 
 class Solution {
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[2][amount+1];
        int[][] curTake = new int[coins.length][amount+1]; // 当前 i,j 时硬币取几个。
        
        for (int j = 0; j <= amount; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            }
            else {
                dp[0][j] = j /coins[0];
                curTake[0][j] = j / coins[0]; //就第一行可能是>1的数
            }
        }
        // 注意，因为是求 min，所以要考虑取和不取时，是否为-1的情况
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i%2][j] = dp[(i-1)%2][j]; 
                if ( j - coins[i] >= 0 && dp[i%2][j-coins[i]] != -1) {  //当前元素能取时
                    if (dp[(i-1)%2][j] == -1 || dp[(i-1)%2][j] > dp[i%2][j-coins[i]] + 1) {
                        dp[i%2][j] = dp[i%2][j-coins[i]] + 1;
                        curTake[i][j] = true;
                    }
                }
            }
        }
             
        int j = amount; 
        for (int i = coins.length - 1; i >= 0; i--) {
            if (curTake[i][j] == 0) {
                continue; 
            }
            else {
                int cnt = curTake[i][j];
                while (cnt > 0) {
                    System.out.println(coins[i]);
                    cnt--;
                    j -= coins[i];
                }
            }
        }
        return dp[(coins.length - 1)%2][amount];
    }
}    
    
-------------------------   2022.2.26 + track path sol 2: curTake 记录当前
   class Solution {
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[2][amount+1];
        boolean[][] curTake = new boolean[coins.length][amount+1]; // 当前 i,j 时取
        
        for (int j = 0; j <= amount; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            }
            else {
                dp[0][j] = j /coins[0];
                curTake[0][j] = true; 
            }
        }
         // 注意，因为是求 min，所以要考虑取和不取时，是否为-1的情况
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i%2][j] = dp[(i-1)%2][j]; 
                if ( j - coins[i] >= 0 && dp[i%2][j-coins[i]] != -1) {  //当前元素能取时
                    if (dp[(i-1)%2][j] == -1 || dp[(i-1)%2][j] > dp[i%2][j-coins[i]] + 1) {
                        dp[i%2][j] = dp[i%2][j-coins[i]] + 1;
                        curTake[i][j] = true;
                    }
                }
            }
        }
             
        int j = amount; 
        int i = coins.length - 1; 
        while (i >= 0 && j >= 0) {
            if (!curTake[i][j]) {
                i--; 
            }
            else {
                System.out.println(coins[i]);
                j -= coins[i];
            }
        }
        
        return dp[(coins.length - 1)%2][amount];
    }
} 

--------------- 2022.6最轻爽的解法 ---------------
    /*
dp[i][j] // 前i个coin，组成和为j,最少需要几个硬币
dp[i][j] = min {dp[i-1][j] -> 不取当前， dp[i][j-num[i]] -> 取当前一个}
*/
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length][amount+1];  
        Arrays.stream(dp).forEach(o -> Arrays.fill(o, amount+1));
        for (int i = 0; i < coins.length; i++) {
            dp[i][0] = 0;
        }
        for (int i = 1; coins[0] * i <= amount; i++) {
            dp[0][coins[0] * i] = i;
        }
        
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i-1][j]; 
                if (j - coins[i] >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j- coins[i]]+1); // 
                }
                
            }
        }
        return dp[coins.length-1][amount] == amount+1 ? -1 : dp[coins.length-1][amount];
    }
}

//////////////////////////// 一维 DP /////////  https://labuladong.github.io/algo/3/23/66/ 
int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    // 数组大小为 amount + 1，初始值也为 amount + 1
    Arrays.fill(dp, amount + 1);

    // base case
    dp[0] = 0;
    // 外层 for 循环在遍历所有状态的所有取值
    for (int i = 0; i < dp.length; i++) {
        // 内层 for 循环在求所有选择的最小值
        for (int coin : coins) {
            // 子问题无解，跳过
            if (i - coin < 0) {
                continue;
            }
            dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
        }
    }
    return (dp[amount] == amount + 1) ? -1 : dp[amount];
}
