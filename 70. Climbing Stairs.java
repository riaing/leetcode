You are climbing a staircase. It takes n steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?


Example 1:

Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
 

Constraints:

1 <= n <= 45
Accepted
1,419,798
Submissions
 
问法二： 
Given a number ‘n’, implement a method to count how many possible ways there are to express ‘n’ as the sum of 1, 3, or 4.

---------------- sequence DP --------------------------
公式： CountWays(n) = CountWays(n-1) + CountWays(n-2) + CountWays(n-3) + ... + CountWays(n-k), for n >= k 

class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1; 
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i-1];
            if (i-2 >= 0) {
                dp[i] += dp[i-2];
            }
            // if (i-3 >= 0) {
            //     dp[i] += dp[i-3];
            // }
        }
        return dp[n];
    }
}
