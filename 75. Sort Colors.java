Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function.

 

Example 1:

Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Example 2:

Input: nums = [2,0,1]
Output: [0,1,2]
 

Constraints:

n == nums.length
1 <= n <= 300
nums[i] is either 0, 1, or 2.
 

Follow up: Could you come up with a one-pass algorithm using only constant extra space?

----------- 2 pointer 扫两遍  -----------------------------------------

class Solution {
    public void sortColors(int[] nums) {
        //1. put all 0 before start 
        int start = 0;
        int end = nums.length - 1;
        while (start < end) {
            if (nums[start] == 0) {
                start++;
            }
            if (nums[end] != 0) {
                end--;
            }
            if (start < end && nums[start] != 0 && nums[end] == 0) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
        }
        // System.out.println(nums[0] + " " +  nums[1] + " " + nums[2]);
        
        
        // 2. put all 2 after end 
        start = 0;
        end = nums.length - 1;
        while (start < end) {
            if (nums[start] != 2) {
                start++;
            }
            if (nums[end] == 2) {
                end--;
            }
            if (start < end && nums[start] == 2 && nums[end] != 2) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
        }
        
        return;
    }
} 

----------- 2 pointer 优化 code，只扫一遍 -----------------------------
/*
左指针是第一个非零。右指针是第一个非2。扫整个 array，当遇到1时跳过，遇到0时和左指针 swap，遇到2时和右指针 swap。
O（n）
*/
class Solution {
    public void sortColors(int[] nums) {
        
        int start = 0;
        int end = nums.length - 1;
        while (start < nums.length && nums[start]==0) {
            start++;
        }
        while (end >= 0 && nums[end]==2) {
            end--;
        }
        
        int i = start;
        while (i <= end) {
            if (nums[i] == 1) {
                i++;
            }
            else if (nums[i] == 0) {
                // swap with start 
                int temp = nums[i];
                nums[i] = nums[start];
                nums[start] = temp;
                

                start++;
                // while (start < nums.length && nums[start]==0) { // 优化，直接调到下一次非0的地方
                //     start++;
                // }
                i = start; 
            }
            else {
                 // swap with end 
                int temp = nums[i];
                nums[i] = nums[end];
                nums[end] = temp;
                
                // while (end >= 0 && nums[end]==2) { // 优化，直接调到下一次非2的地方
                //     end--;
                // }
                end--;
            }
        }
        
        return;
    }
}
