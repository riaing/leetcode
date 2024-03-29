
Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:

[4,5,6,7,0,1,2] if it was rotated 4 times.
[0,1,2,4,5,6,7] if it was rotated 7 times.
Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].

Given the sorted rotated array nums of unique elements, return the minimum element of this array.

You must write an algorithm that runs in O(log n) time.

 

Example 1:

Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.
Example 2:

Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
Example 3:

Input: nums = [11,13,15,17]
Output: 11
Explanation: The original array was [11,13,15,17] and it was rotated 4 times. 
 

Constraints:

n == nums.length
1 <= n <= 5000
-5000 <= nums[i] <= 5000
All the integers of nums are unique.
nums is sorted and rotated between 1 and n times.


 这题的应用题：求rotation count: https://www.educative.io/courses/grokking-the-coding-interview/7nPmB8mZ6vj 
--------------------------------------3.6.3022 ---------------------------------
/*
rotation array 两种情况，1， rotate了，2， 没rotate

rotate时有俩：
1. mid 在第一段： mid > end  =》移start
2. mid 在第二段： start > mid < end => 移end

不rotate时 （就是要找第一个元素）
1. mid < end => 移end： 可以和rotate#2合并

合并而得：
mid > end时，移start
否则移end

*/
class Solution {
    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length - 1; 
        while (start < end) {
            int mid = start + (end - start) / 2;
            // System.out.println(mid);
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            }
            // if (nums[start] >= nums[mid] && nums[mid] < nums[end]) {
            else{
                end = mid;
            }
        }
        return nums[start];
    }
}
