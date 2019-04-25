Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.
Follow up: Could you improve it to O(n log n) time complexity?

--------------------------------------------DP-----------------------------------------------------------------------
/**
m[i]: 以i为结尾的最长sub
m[i] = max{m[j] + 1 && a[j] < a[i]}, 0<= j < i
m[0...n-1] = 1: 这里必须是所以值都initialize成1，
return max{m[0...n-1]}

time: O(n^2), space o(n)
*/
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        
        int[] res = new int[nums.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = 1;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    res[i] = Math.max(res[i], res[j] + 1);
                }
            }
        }
        
        int max = res[0];
        for (int n = 1; n < res.length; n++) {
            max = Math.max(max, res[n]);
        }
        return max; 
    }
}

