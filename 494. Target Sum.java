https://leetcode.com/problems/target-sum/submissions/ 

You are given an integer array nums and an integer target.

You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers.

For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
Return the number of different expressions that you can build, which evaluates to target.

 

Example 1:

Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3
Example 2:

Input: nums = [1], target = 1
Output: 1
 

Constraints:

1 <= nums.length <= 20
0 <= nums[i] <= 1000
0 <= sum(nums[i]) <= 1000
-1000 <= target <= 1000 

---------------------------------------
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int[][] dp = new int[nums.length][2000+1];
        
        // 边界条件的思路
        //for (j = -1000; j <= 1000; j++) {
        //     if (nums[0] == j) {
        //         dp[0][j] = 1;
        //     }
        //     if (-nums[0] == j) {
        //         dp[0][j]++;
        //     }
        // }
        dp[0][nums[0]+1000] = 1;  
        dp[0][-nums[0]+1000] += 1;
       
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j <= 2000; j++) {
                if (j-nums[i] >= 0) {
                    dp[i][j] = dp[i-1][j-nums[i]];
                }
                if (j + nums[i] <= 2000) {
                    dp[i][j] += dp[i-1][j+nums[i]];
                }
                
            }
        }
        return dp[nums.length-1][target+1000];
        
        
    }
}
