Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
-------------O（n）, O(n) -------------------------------------------------------------------------------
/**
g[i] = 以i为结尾的max sum
g[i] = max{g[i-1] + A[i], A[i]} = max{0, g[i-1]} + A[i]
g[0] = A[0]
return max{g[0....length-1]}
*/
class Solution {
    public int maxSubArray(int[] nums) {
        int[] g = new int[nums.length];
        g[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            g[i] = Math.max(0, g[i-1]) + nums[i];
            max = Math.max(max, g[i]);
        }
        return max;
    }
}
