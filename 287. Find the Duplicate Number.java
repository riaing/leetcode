Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

Example 1:

Input: [1,3,4,2,2]
Output: 2
Example 2:

Input: [3,1,3,4,2]
Output: 3
Note:

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2).
There is only one duplicate number in the array, but it could be repeated more than once.

-----------------------bineray search ------------------------------------------------------------------------------------
/**
我们在区间[1, n]中搜索，首先求出中点mid，然后遍历整个数组，统计所有小于等于mid的数的个数，如果个数小于等于mid，则说明重复值在[mid+1, n]之间，
反之，重复值应在[1, mid]之间，然后依次类推，直到搜索完成，此时的low就是我们要求的重复值.
Time nlogx -> x= max - min;
*/
class Solution {
    public int findDuplicate(int[] nums) {
        int max = nums.length -1;
        int min = 1; 
        while (min < max) {
            int mid = min + (max - min) / 2;
            if (findLessOrEqualToK(nums, mid) <= mid) {
                min = mid + 1;
            }
            else {
                max = mid;
            }
        }
        return min;  
    }
    
    private int findLessOrEqualToK(int[] nums, int k) {
        int count  = 0; 
        for (int num : nums) {
            if (num <= k) {
                count++;
            }
        }
        return count;
    }
}
