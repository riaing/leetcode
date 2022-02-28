You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

 

Example 1:

Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
Example 2:

Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.
 

Constraints:

1 <= nums.length <= 100
0 <= nums[i] <= 400

------------ DP， 定义为前 i 的 min， 找 path  ------------------------------------------------
/*
定义时不一定包括当前 i、
另一种定义方法是定要包括当前 i/ends at i. 那个需要两个 loop 
at house i, the max profit.  
2 choices: 
    1. rob house i, then dp[i-2] + nums[i]
    2. don't rob current house i, then dp[i-1]
    
O(n) 
*/
class Solution {
    public int rob(int[] nums) {
        // corner case 
        if (nums.length == 1) {
            return nums[0];
        }
        
        int[] dp = new int[nums.length];
        boolean[] from = new boolean[nums.length];
        dp[0] = nums[0];
        from[0] = true;
        dp[1] = Math.max(nums[0], nums[1]);
        if (nums[1] > nums[0]) {
            from[1] = true;
        }
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
            if (dp[i-2] + nums[i] > dp[i-1]) {
                from[i] = true;
            }
        }
        int i = nums.length - 1;
        while (i >= 0) {
            if (from[i]) {
                System.out.println(nums[i]);
                i -= 2;
            } else {
                i -= 1;
            }
        }
        return dp[nums.length-1];
    }
}

-------------- 稍微差的解法， dp[i]定义为以 i 结尾的 min -------------------------- 
 /*
if rob house i, the max profit 
*/
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = nums[1];
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Integer.MIN_VALUE;
            for (int j = i-2; j >= 0; j--) {
                dp[i] = Math.max(dp[i], dp[j] + nums[i]);
            }
            // System.out.println("i " + i + " " + dp[i]);
        }
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
