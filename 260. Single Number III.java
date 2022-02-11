Given an integer array nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once. You can return the answer in any order.

You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.

 

Example 1:

Input: nums = [1,2,1,3,2,5]
Output: [3,5]
Explanation:  [5, 3] is also a valid answer.
Example 2:

Input: nums = [-1,0]
Output: [-1,0]
Example 3:

Input: nums = [0,1]
Output: [1,0]
 

Constraints:

2 <= nums.length <= 3 * 104
-231 <= nums[i] <= 231 - 1
Each integer in nums will appear twice, only two integers will appear once.

------------------- XOR bitwise --------------------
// 先想办法把 array 分成两个，让 unque num 分别在俩 array 之间
class Solution {
    public int[] singleNumber(int[] nums) {
        int n1n2 = 0;  // n1^n2
        for (int num : nums) {
            n1n2 ^= num;
        }
        
        // 找到右边第一位1. knowledge 1: 一个数和（2,4,8...) &, 结果不是零的话就能找到位数为1的地方 
        int rightMostBit = 1;
        while ((rightMostBit & n1n2) == 0) {
            rightMostBit = rightMostBit << 1;
        }
        
        // 根据 right bit 把 array 分成两块。那位上为1的和不为1的
        // knowledge 2: 分的方法是将数字 & (2, 4,8...),如果得0， 说明那位上不是0
        int numNotZero = 0;
        int numZero = 0;
        for (int num : nums) {
            if ((rightMostBit & num) == 0) {
                numZero ^= num;
            }
            else {
                numNotZero ^= num;
            }
        }
        return new int[]{numNotZero, numZero};
    }
}
