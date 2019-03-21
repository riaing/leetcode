Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Example 1:

Input: [1,3,5,6], 5
Output: 2
Example 2:

Input: [1,3,5,6], 2
Output: 1
Example 3:

Input: [1,3,5,6], 7
Output: 4
Example 4:

Input: [1,3,5,6], 0
Output: 0
--------------------------binary search------------------------------------------------------------------------
// find the first element that is grater or equal to target.
class Solution {
    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                start = mid;
            }
            if (nums[mid] > target) {
                end = mid;
            }
        }
        // consider all conditions: 
        //1, add to beginning 
        if (nums[start] >= target) {
            return start;
        }
        // 2, add to middle
        // if excute here, we know that nums[start] < target, based on this: 
        else if (nums[end] >= target) {
            return end;
        }
        // 3, add after end 
        // if (nums[end] < target)
        else {
            return end+1;
        }
    }
}
