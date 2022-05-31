Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.

 

Example 1:

Input: nums = [0,1]
Output: 2
Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.
Example 2:

Input: nums = [0,1,0]
Output: 2
Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 

Constraints:

1 <= nums.length <= 105
nums[i] is either 0 or 1.

------------------------------------------preSum + hashMap --------------------------------------------------- 
class Solution {
    public int findMaxLength(int[] nums) {
        // 1, 求1，0 相同数量 => 0 = -1, 1 = 1 => 寻找和为 0 的最长子数组。
        int[] dp = new int[nums.length+1];
        dp[0] = 0; 
        for (int i = 0; i < nums.length; i++) {
            dp[i+1] = dp[i] + (nums[i] == 0 ? -1 : 1); 
        }
        
        // 2. 求 dp[i] - dp[j] = 0 => dp[i] = dp[j] => 建立dp[i] -> i的map
        Map<Integer, Integer> map = new HashMap<Integer,Integer>(); 
        for (int i = 0; i < dp.length; i++) {
            if (!map.containsKey(dp[i])) {
                map.put(dp[i], i); 
            }
        }

        int maxLen = 0; 
        // 3. 找到dp[i] 左边的一个dp[j], where dp[i] == dp[j]
        for (int i = 0; i < dp.length; i++) {
            if (map.containsKey(dp[i]) && i - map.get(dp[i]) >=1) {
                maxLen = Math.max(maxLen, i - map.get(dp[i]));
                
            }
        }
         return maxLen; 
    }
   
}
