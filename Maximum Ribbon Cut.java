基本一样题：求最少 cut https://github.com/riaing/leetcode/blob/master/322.%20Coin%20Change.java 

We are given a ribbon of length ‘n’ and a set of possible ribbon lengths. We need to cut the ribbon into the maximum number of pieces that comply with the above-mentioned possible lengths. Write a method that will return the count of pieces.

Example 1:

n: 5
Ribbon Lengths: {2,3,5}
Output: 2
Explanation: Ribbon pieces will be {2,3}.
Example 2:

n: 7
Ribbon Lengths: {2,3}
Output: 3
Explanation: Ribbon pieces will be {2,2,3}.
Example 3:

n: 13
Ribbon Lengths: {3,5,7}
Output: 3
Explanation: Ribbon pieces will be {3,3,7}. 
  
  
--------------------------------------------------   
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
                if (j - coins[i] >= 0 && dp[i%2][j-coins[i]] != -1) {  //当前元素能取时
                    if (dp[(i-1)%2][j] == -1 || dp[(i-1)%2][j] < dp[i%2][j-coins[i]] + 1) {
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
