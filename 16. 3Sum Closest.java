Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

Example:

Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
---------------------------------------------------------------------------------------------
/**
仍然是3 sum的思路。用一个全局变量res记录每次找到数与target的差，更新。最后返回差最小的res
*/
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = 0; 
        int diff = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length-2; i++) { // 注意这里留两个位子给第2，3个数
            int start = i+1;
            int end = nums.length-1;
            while (start < end) {
                int curSum = nums[start] + nums[end];
                if (diff > Math.abs(target - nums[i] - curSum)) {
                    res = nums[i] + curSum;
                    diff = Math.abs(target - nums[i] - curSum);
                }
                if (curSum > target - nums[i]) {
                    end--;
                }
                else if (curSum < target - nums[i]) {
                    start++;
                }
                else {
                    return target;
                }
            }
        }
        return res;
    }
}
