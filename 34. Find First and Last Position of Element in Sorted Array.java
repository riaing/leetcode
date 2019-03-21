Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

Your algorithm’s runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]

----------------------------binary search拓展-----------------------------
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1,-1};
        }
        int[] res = new int[2];
        int start = 0;
        int end = nums.length-1;
        // Find Last Position of Target 
        res[1] = findRight(nums, target, start, end);
        // Find First Position of Target
        res[0] = findLeft(nums, target, start, end);  
        return res;
        
    }
    
    private int findRight(int[] nums, int target, int start, int end) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2; 
            if (nums[mid] <= target) {
                start = mid;
            }
            // nums[mid] > target
            else {
                end = mid;
            }
        }
        if (nums[end] == target) {
            return end;
        }
        else if (nums[start] == target) {
            return start;
        }
        else {
            return -1;
        }
    }
    
       private int findLeft(int[] nums, int target, int start, int end) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2; 
            if (nums[mid] < target) {
                start = mid;
            }
            // nums[mid] >= target
            else {
                end = mid;
            }
        }
        // this order matters! 
        if (nums[start] == target) {
            return start;
        }
        else if (nums[end] == target) {
            return end;
        }
        else {
            return -1;
        }
    }
    
    
}
