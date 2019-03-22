Given a sorted (in ascending order) integer array nums of n elements and a target value, write a function to search target in nums. If target exists, then return its index, otherwise return -1.


Example 1:

Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4

Example 2:

Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
 

Note:

You may assume that all elements in nums are unique.
n will be in the range [1, 10000].
The value of each element in nums will be in the range [-9999, 9999].


------------------基础binary search，array withOut duplicates, find the target ----------------------------------------
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

-------------进阶一，array with duplicates, find the left most target------------------------
class Solution {
    public int search(int[] nums, int target) {
         int start = 0;
        int end = nums.length-1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // 如果有duplicate的情况，考虑mid== target的几种情况(与无duplicate唯一的不同之处)
            if (nums[mid] == target) {
              //如果已经是最左边了，或者左边没有target
                if (mid == 0 || nums[mid-1] != target) {
                     return mid;
                }
                //如果此时左边还有target 
                else{           
                    end = mid;
                }
               
            }
            if (nums[mid] > target) {
                end = mid;
            }
            if (nums[mid] < target) {
                start = mid;
            }
          
        }
        // 因为是找最左边一个，所以首先考虑start!顺序不能错，可对比下面
        if (nums[start] == target) {
            return start;
        }
       
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
-------------------进阶二，array with duplicates, find the right most target--------------------------------
class Solution {
    public int search(int[] nums, int target) {
         int start = 0;
        int end = nums.length-1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            // 如果有duplicate的情况，考虑mid== target的几种情况
            if (nums[mid] == target) {
                // 如果mid已经是最右边一个了，或者mid的右边没有target了
                if (mid == end || nums[mid+1] != target) {
                     return mid;
                }
                //如果mid右边还有target
                else{           
                    start = mid;
                }
               
            }
            if (nums[mid] > target) {
                end = mid;
            }
            if (nums[mid] < target) {
                start = mid;
            }
          
        }
        // 因为是找最右边一个，所以首先考虑end
        if (nums[end] == target) {
            return end;
        }
       
        else if (nums[start] == target) {
            return start;
        }
        // 3, not exist
        // if (nums[end] < target)
        else {
            return -1;
        }
    }
}
