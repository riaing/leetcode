Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

Example 1:

Input: [1,3,5]
Output: 1
Example 2:

Input: [2,2,2,0,1]
Output: 0
Note:

This is a follow up problem to Find Minimum in Rotated Sorted Array.
Would allow duplicates affect the run-time complexity? How and why?

--------------------------------------------------------------------------------------------------------
class Solution {
    public int findMin(int[] nums) {
         if (nums== null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
             
                end = mid;
                   if(mid == 3) {
                     
                }
            }
            // another rotated array 
            else if (nums[mid] > nums[end]){
                start = mid;
            }
            //如果有duplicate的处理方法
            else {
                end--;
            }
        }

        return nums[start] < nums[end] ? nums[start] : nums[end];
        
    }
}
