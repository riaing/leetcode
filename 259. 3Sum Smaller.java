Given an array of n integers nums and an integer target, find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.

 

Example 1:

Input: nums = [-2,0,1,3], target = 2
Output: 2
Explanation: Because there are two triplets which sums are less than 2:
[-2,0,1]
[-2,0,3]
Example 2:

Input: nums = [], target = 0
Output: 0
Example 3:

Input: nums = [0], target = 0
Output: 0
 

Constraints:

n == nums.length
0 <= n <= 3500
-100 <= nums[i] <= 100
-100 <= target <= 100
  
  
  
  ---------- 2022.1.20 3 sum 思路------------
  class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int start = i + 1;
            int end = nums.length - 1; 
            while (start < end) {
                int curSum = nums[i] + nums[start] + nums[end];
                if (curSum < target) {
                    count = count + (end - start);  // 注意：因为 end + start < target, 那么 start + (end-1), start +(end-2)...start + (end - start)这些都会<target。start 到 end 间总共有 end - start 对数
                    start++;
                }     
                else {
                    end--;
                }
            }   
        }
        return count;
        
    }
}
