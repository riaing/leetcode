Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
-------------O（n）, O(1) -------------------------------------------------------------------------------
/**
g[i] = 以i为结尾的max sum
g[i] = max{g[i-1] + A[i], A[i]} = max{0, g[i-1]} + A[i]
g[0] = 0, 多一个dummy
return max{g[0....length-1]}
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length+1];  
        int lastVal = 0; 
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= nums.length; i++) {
            int cur = Math.max(nums[i-1], nums[i-1]+ lastVal);
            res = Math.max(res, cur);
            lastVal = cur; 
        }
        return res; 
    }

    ------ 思路二，前缀和 ---------------------
  解法2： 前缀和思路
在动态规划解法中，我们通过状态转移方程推导以 nums[i] 结尾的最大子数组和，其实用前文 小而美的算法技巧：前缀和数组 讲过的前缀和数组也可以达到相同的效果。

回顾一下，前缀和数组 preSum 就是 nums 元素的累加和，preSum[i+1] - preSum[j] 其实就是子数组 nums[j..i] 之和（根据 preSum 数组的实现，索引 0 是占位符，所以 i 有一位索引偏移）。

那么反过来想，以 nums[i] 为结尾的最大子数组之和是多少？其实就是 preSum[i+1] - min(preSum[0..i])。

所以，我们可以利用前缀和数组计算以每个元素结尾的子数组之和，进而得到和最大的子数组：

// 前缀和技巧解题
int maxSubArray(int[] nums) {
    int n = nums.length;
    int[] preSum = new int[n + 1];
    preSum[0] = 0;
    // 构造 nums 的前缀和数组
    for (int i = 1; i <= n; i++) {
        preSum[i] = preSum[i - 1] + nums[i - 1];
    }
    
    int res = Integer.MIN_VALUE;
    int minVal = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
        // 维护 minVal 是 preSum[0..i] 的最小值
        minVal = Math.min(minVal, preSum[i]);
        // 以 nums[i] 结尾的最大子数组和就是 preSum[i+1] - min(preSum[0..i])
        res = Math.max(res, preSum[i + 1] - minVal);
    }
    return res;
}

---------------- 05/11 训练下 D&C 的思维-- O（nlgN）-----------------------------------------
    
    https://www.geeksforgeeks.org/maximum-subarray-sum-using-divide-and-conquer-algorithm/ 
