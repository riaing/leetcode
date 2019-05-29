Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).

You are given a target value to search. If found in the array return true, otherwise return false.

Example 1:

Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
Example 2:

Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
Follow up:

This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
Would this affect the run-time complexity? How and why?
    
    
------------------自己模板---------------------------------------------------------------------------------
  /**
当有重复数字，会存在A[mid] = A[start]的情况。此时左半序列A[start : mid-1]可能是sorted，也可能并没有sorted，如下例子。

3 3 3 3 1 2 3
3 1 2 3 3 3 3 

所以当A[mid] = A[start] != target时，无法排除一半的序列，而只能排除掉A[start]：

A[mid] = A[start] != target时：搜寻A[start+1 : end]

正因为这个变化，在最坏情况下，算法的复杂度退化成了O(n)：
序列 2 2 2 2 2 2 2 中寻找target = 1。

*/
class Solution {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int start = 0; 
        int end = nums.length - 1;
        
        while (start < end) {
            int mid = start + (end-start + 1)/2;
            if (nums[mid] == target) {
                return true;
            }
  
            if (nums[mid] > nums[start]) {
              
                if (nums[start] <= target && target < nums[mid]) {
                    end = mid-1;
                }

                else {
                    start = mid;
                }
            }
 
            else if (nums[mid] < nums[start]) {
                if (nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                }
          
                else {
                    end = mid-1;
                }  
            }
            //和第一题唯一不一样的地方！ 
            else {
                start++;
            }
        }
        
        if (nums[start] == target) {
            return true;
        }
        return false;        
    }
    
}  
-----------------------------------九章模板. -------------------------------------------------------------------

/**
当有重复数字，会存在A[mid] = A[start]的情况。此时左半序列A[start : mid-1]可能是sorted，也可能并没有sorted，如下例子。

3 3 3 3 1 2 3
3 1 2 3 3 3 3 

所以当A[mid] = A[start] != target时，无法排除一半的序列，而只能排除掉A[start]：

A[mid] = A[start] != target时：搜寻A[start+1 : end]

正因为这个变化，在最坏情况下，算法的复杂度退化成了O(n)：
序列 2 2 2 2 2 2 2 中寻找target = 1。

*/
class Solution {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int start = 0; 
        int end = nums.length - 1;
        
        while (start +1 < end) { 
            int mid = start + (end-start)/2; 
            if (nums[mid] == target) {
                return true;
            }
  
            if (nums[mid] > nums[start]) {
              
                if (nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                }

                else {
                    start = mid;
                }
            }
 
            else if (nums[mid] < nums[start]) {
                if (nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                }
          
                else {
                    end = mid;
                }  
            }
            //和第一题唯一不一样的地方！ 
            else {
                start++;
            }
        }
        
        if (nums[start] == target || nums[end] == target) {
            return true;
        }
        return false;        
    }
    
}
