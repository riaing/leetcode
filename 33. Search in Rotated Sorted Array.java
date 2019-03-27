Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Your algorithm's runtime complexity must be in the order of O(log n).

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

    
--------------3.26.19 update 自己的模板----------------------------------------------------------------------
 反正要不start=mid，要不end=mid。如果start=mid的话，mid要+1，所以就凑足条件即可   
    
// 先考虑mid在哪段上，by 判断mid <? start。然后再判断target在哪
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0; 
        int end = nums.length - 1;
        
        while (start < end) {
            int mid = start + (end-start)/2+1;
            if (nums[mid] == target) {
                return mid;
            }
            // mid在第一段（可能第二段如果没有rotate的话）
            if (nums[mid] > nums[start]) {
                // target在第一段上的start和mid之间
                if (nums[start] <= target && target < nums[mid]) {
                    end = mid-1;
                }
                //说明target在mid右侧，有可能在第一段上，也有可能在第二段上。又是一个roated array，照样解决。
                else {
                    start = mid;
                }
            }
            // mid在第二段
            else {
                if (nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                }
               // 也又是一个roated array
                else {
                    end = mid-1;
                }  
            }
        }
        
        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}
-------------------------binary search----------------------------------------------------------------------
/**
rotated array 想像一下，可能有一个或两个上升区间。一个的话就是连续array，两个的话，想象两条上升直线分别在第三象限和第二象限。
先考虑mid在哪段上，by 判断mid <? start。然后再判断target在哪
**/
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0; 
        int end = nums.length - 1;
        
        while (start +1 < end) {
            int mid = start + (end-start)/2;
            if (nums[mid] == target) {
                return mid;
            }
            // mid在第一段（可能第二段如果没有rotate的话）
            if (nums[mid] > nums[start]) {
                // target在第一段上的start和mid之间
                if (nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                }
                //说明target在mid右侧，有可能在第一段上，也有可能在第二段上。又是一个roated array，照样解决。
                else {
                    start = mid;
                }
            }
            // mid在第二段
            else {
                if (nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                }
               // 也又是一个roated array
                else {
                    end = mid;
                }  
            }
        }
        
        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}
