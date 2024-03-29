Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, and return the product.

It is guaranteed that the answer will fit in a 32-bit integer.

A subarray is a contiguous subsequence of the array.

 

Example 1:

Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
Example 2:

Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 

Constraints:

1 <= nums.length <= 2 * 104
-10 <= nums[i] <= 10
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
  
  ------------------- 04.03.2021 两个数组的DP ------------------------------------------------------------------------------
  /*
max[i]:以i为结尾的continuous subarray的最大值
min[i]: 以i为结尾的continuous subarray的最小值
max[i] = max {min[i-1] * nums[i], max[i-1] * nums[i], nums[i]}

*/
class Solution {
    public int maxProduct(int[] nums) {
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = nums[0];
        min[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max[i] = Math.max(nums[i], Math.max(max[i-1] * nums[i], min[i-1] * nums[i]));
            min[i] = Math.min(nums[i], Math.min(max[i-1] * nums[i], min[i-1] * nums[i]));
            result = Math.max(result, max[i]);
        }
        return result;
    }
}

-------------------------- 2022 ---------------------------------------------------------------
/*
dp[i][0/1] : 以i结尾的前i个元素，0 - 的最大值， 1 -的最小值
dp[i]  = if i >= 0: dp[i][max] = dp[i-1][max] * n[i], n[i]
                    dp[i][min] = dp[i-1][min] * n[i], n[i]
         
         if i < 0:  dp[i][max] = dp[i-1][min] * n[i], n[i]
                    dp[i][min] = dp[i-1][max] * n[i], n[i]            

本题重点：
1. 要求contituous array，所以必须包括n
2. 计算会和前一个的min，max有关。所以array需要记住min，max

O(n) 
                                             
*/
class Solution {
    public int maxProduct(int[] nums) {
        int[][] dp = new int[nums.length][2]; // 0 - max， 1- min
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        int max = dp[0][0];
        for (int i = 1; i < nums.length; i++) {
            int n = nums[i];
            if (n >= 0) {
                dp[i][0] = Math.max(n, dp[i-1][0] * n);
                dp[i][1] = Math.min(n, dp[i-1][1] * n);
            }
            else {
                dp[i][0] = Math.max(dp[i-1][1] * n, n);
                dp[i][1] = Math.min(dp[i-1][0] * n, n);
            }
            max = Math.max(dp[i][0], max);
            // System.out.println(i + " max "  + " : " + dp[i][0] + " - min: " + dp[i][1]);
        }
        
        return max;
    }
}
