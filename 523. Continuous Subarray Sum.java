Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.

An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.

 

Example 1:

Input: nums = [23,2,4,6,7], k = 6
Output: true
Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.
Example 2:

Input: nums = [23,2,6,4,7], k = 6
Output: true
Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.
Example 3:

Input: nums = [23,2,6,4,7], k = 13
Output: false
 

Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 109
0 <= sum(nums[i]) <= 231 - 1
1 <= k <= 231 - 1

------------------------------- Presum + map ------------------------------------------------------------------
/*
要求的(preSum[i] - preSum[j]) % k == 0， 其实就是 preSum[i] % k == preSum[j] % k。

所以我们使用一个哈希表，记录 preSum[j] % k 的值以及对应的index，就可以迅速判断 preSum[i] 是否符合条件了。
*/
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        int[] dp = new int[nums.length+1];
        dp[0] = 0; // no element 
        for (int i = 1; i <= nums.length; i++) {
            dp[i] = dp[i-1] + nums[i-1];
        }
        System.out.println(Arrays.toString(dp));
        // 建立 preSum[i] % k -> i 
        Map<Integer, Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i <= nums.length; i++) {
            int reminder = dp[i] % k;
            if (!map.containsKey(reminder)) { // 只存靠右的那部分，比如ex1中，【2，4】的pair只存2
                map.put(reminder, i); 
            }
        }
        System.out.println(map);
        
        for (int i = 1; i < dp.length; i++) {
            int need = dp[i] % k; // this is preSum[j] % k 
            if (map.containsKey(need) && i - map.get(need) >=2) {
                return true;
            }
            
        }
           
        return false; 
    }
}
