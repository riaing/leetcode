You are given a binary array nums containing only the integers 0 and 1. Return the number of subarrays in nums that have more 1's than 0's. Since the answer may be very large, return it modulo 109 + 7.

A subarray is a contiguous sequence of elements within an array.

 

Example 1:

Input: nums = [0,1,1,0,1]
Output: 9
Explanation:
The subarrays of size 1 that have more ones than zeros are: [1], [1], [1]
The subarrays of size 2 that have more ones than zeros are: [1,1]
The subarrays of size 3 that have more ones than zeros are: [0,1,1], [1,1,0], [1,0,1]
The subarrays of size 4 that have more ones than zeros are: [1,1,0,1]
The subarrays of size 5 that have more ones than zeros are: [0,1,1,0,1]
Example 2:

Input: nums = [0]
Output: 0
Explanation:
No subarrays have more ones than zeros.
Example 3:

Input: nums = [1]
Output: 1
Explanation:
The subarrays of size 1 that have more ones than zeros are: [1]
 

Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 1


----------------- preSum O(N^2) -----------------
class Solution {
    public int subarraysWithMoreZerosThanOnes(int[] nums) {
        int m = (int)1e9 + 7;
        // 1. trick, 0 -> -1 
        int[] preSum = new int[nums.length+1];
        preSum[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i] == 0 ? -1 : 1;
            preSum[i+1] = preSum[i] + cur;
        }
        // 2. 遍历presum，看每个元素后面有几个大于它的值
        int res = 0; 
        for (int i = 0; i < preSum.length; i++) {
            for (int j = i; j < preSum.length; j++) {
                if (preSum[j] - preSum[i] > 0) {
                    res++;
                    res = res % m; 
                }
            }
        }
        return res; 
    }
}
