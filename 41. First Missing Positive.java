Given an unsorted integer array nums, return the smallest missing positive integer.

You must implement an algorithm that runs in O(n) time and uses constant extra space.

 

Example 1:

Input: nums = [1,2,0]
Output: 3
Example 2:

Input: nums = [3,4,-1,1]
Output: 2
Example 3:

Input: nums = [7,8,9,11,12]
Output: 1
 

Constraints:

1 <= nums.length <= 5 * 105
-231 <= nums[i] <= 231 - 1

-------------- cyclic sort --------------------------
class Solution {
    public int firstMissingPositive(int[] nums) {
        // construct array from [1, 2, 3, ....]
        int i = 0; 
        while (i < nums.length) {
            // swap if it's not in the right index 
            if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i]-1]) {
                swap(nums, i, nums[i] - 1); // 与正确 index 交换
            }
            else {
               i++;  
            }
        }
        
        // find the smallest one that's not on the correct index 
        for ( i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
            if (nums[i] != i+1) {
                return i+1;
            }
        }
        // 出了 loop 说明 array 是正确的，那么 array 的后一个就是 missing 
        return nums.length+1;  // [1,2] should return 3
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }
}
