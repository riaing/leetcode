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
 Solution#
This problem is quite similar to Single Number, the only difference is that, in this problem, we have two single numbers instead of one. Can we still 
use XOR to solve this problem?

Let’s assume num1 and num2 are the two single numbers. If we do XOR of all elements of the given array, we will be left with XOR of num1 and num2 as 
all other numbers will cancel each other because all of them appeared twice. Let’s call this XOR n1xn2. Now that we have XOR of num1 and num2, how can 
we find these two single numbers?

As we know that num1 and num2 are two different numbers, therefore, they should have at least one bit different between them. If a bit in n1xn2 is ‘1’, 
this means that num1 and num2 have different bits in that place, as we know that we can get ‘1’ only when we do XOR of two different bits, i.e.,

1 XOR 0 = 0 XOR 1 = 1
We can take any bit which is ‘1’ in n1xn2 and partition all numbers in the given array into two groups based on that bit. One group will have all those 
numbers with that bit set to ‘0’ and the other with the bit set to ‘1’. This will ensure that num1 will be in one group and num2 will be in the other.
 We can take XOR of all numbers in each group separately to get num1 and num2, as all other numbers in each group will cancel each other. Here are the 
 steps of our algorithm:

Taking XOR of all numbers in the given array will give us XOR of num1 and num2, calling this XOR as n1xn2.
Find any bit which is set in n1xn2. We can take the rightmost bit which is ‘1’. Let’s call this rightmostSetBit.
Iterate through all numbers of the input array to partition them into two groups based on rightmostSetBit. Take XOR of all numbers in both the groups 
separately. Both these XORs are our required numbers.
 
 
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
