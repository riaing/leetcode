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
    
---------------纯想-------------------------------------------------------------------------------------    
// find the first element that is grater or equal to target.
//找到target右边第一个大于它的数
    //写完后想corner case，只有两个数的情况
class Solution {
    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid+1;
            }
            else if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] > target) {
                end = mid;
            } 
        }
        return nums[start] >= target ? start : start+1;
    }
}
    
--------------------------binary search 九章模板------------------------------------------------------------------------
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
------------------------最基本的binary search写法------------------------------------------------------------------------------
    Given a sorted (in ascending order) integer array nums of n elements and a target value, write a function to search target in nums. If target exists, then return its index, otherwise return -1.


Example 1:

Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4

Example 2:

Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
    
   ----------写法
   class Solution {
    public int search(int[] nums, int target) {
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
        if (nums[start] == target) {
            return start;
        }
        // 2, add to middle
        else if (nums[end] == target) {
            return end;
        }
        // 3, not exist
        // if (nums[end] < target)
        else {
            return -1;
        }
    }
}
