Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there is no such subarray, return 0 instead.

 

Example 1:

Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length under the problem constraint.
Example 2:

Input: target = 4, nums = [1,4,4]
Output: 1
Example 3:

Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
 

Constraints:

1 <= target <= 109
1 <= nums.length <= 105
1 <= nums[i] <= 105
 

Follow up: If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log(n)).

-------------- Sliding window --------------------------
/*Sliding window  https://www.educative.io/courses/grokking-the-coding-interview/7XMlMEQPnnQ */

class Solution {
    public int minSubArrayLen(int S, int[] arr) {
        int returnVal = Integer.MAX_VALUE;
        if (arr == null || arr.length == 0) {
          return returnVal; 
        }
        // 1. 只需一个指针
        int curSum = 0; 
        int start = 0; 
        for (int end = 0; end < arr.length; end ++) {
          //2. establish the first window 
          curSum = curSum + arr[end]; 
          //3. move window by certain conditions 
          while (curSum >= S) {
            // 根据题目要求变的 logic
            returnVal = Math.min(returnVal, end - start + 1); 
            // 更新 window，移动前指针
            curSum = curSum - arr[start];
            start++; 
          }
        }
        return returnVal == Integer.MAX_VALUE ? 0 : returnVal;
    }
}
