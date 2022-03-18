ou are playing a game involving a circular array of non-zero integers nums. Each nums[i] denotes the number of indices forward/backward you must move if you are located at index i:

If nums[i] is positive, move nums[i] steps forward, and
If nums[i] is negative, move nums[i] steps backward.
Since the array is circular, you may assume that moving forward from the last element puts you on the first element, and moving backwards from the first element puts you on the last element.

A cycle in the array consists of a sequence of indices seq of length k where:

Following the movement rules above results in the repeating index sequence seq[0] -> seq[1] -> ... -> seq[k - 1] -> seq[0] -> ...
Every nums[seq[j]] is either all positive or all negative.
k > 1
Return true if there is a cycle in nums, or false otherwise.

 

Example 1:

Input: nums = [2,-1,1,2,2]
Output: true
Explanation:
There is a cycle from index 0 -> 2 -> 3 -> 0 -> ...
The cycle's length is 3.
Example 2:

Input: nums = [-1,2]
Output: false
Explanation:
The sequence from index 1 -> 1 -> 1 -> ... is not a cycle because the sequence's length is 1.
By definition the sequence's length must be strictly greater than 1 to be a cycle.
Example 3:

Input: nums = [-2,1,-1,-2,-2]
Output: false
Explanation:
The sequence from index 1 -> 2 -> 1 -> ... is not a cycle because nums[1] is positive, but nums[2] is negative.
Every nums[seq[j]] must be either all positive or all negative.
 

Constraints:

1 <= nums.length <= 5000
-1000 <= nums[i] <= 1000
nums[i] != 0
 
 
------------------------- detect cycle 进阶 ------------------------------------------------------------------- 
/*
思路： We can start from each index of the array to find the cycle. If a number does not have a cycle we will move forward to the next element.  
at every index i, try to find cycle
Time: O(n^2)
Space: O(1)

计算方法： 
求new index的方法 
(curIndex + nums[curIndex]) % nums.length 
java中 a % b if a < 0, 因为取余，所以结果也可能小于0. 解决方法是  (a % b + b) % b 
所以当curIndex + nums[curIndex] < 0 时，用以上

Ref： https://stackoverflow.com/questions/4412179/best-way-to-make-javas-modulus-behave-like-it-should-with-negative-numbers/25830153#25830153 
https://nibes.cn/blog/11286 

An Alternate Approach#
In our algorithm, we don’t keep a record of all the numbers that have been evaluated for cycles. We know that all such numbers will not produce a cycle for any other instance as well. If we can remember all the numbers that have been visited, our algorithm will improve to O(N) as, then, each number will be evaluated for cycles only once. We can keep track of this by creating a separate array, however, in this case, the space complexity of our algorithm will increase to O(N).

*/
class Solution {
    public boolean circularArrayLoop(int[] nums) {
     
        for (int i = 0; i < nums.length; i++) { // i决定起点,对于每一个起点，试着找cycle
            int slow = i; int quick = i; 
            while (slow != -1 && quick != -1) {
              
                slow = findNextIndex(nums, slow);
                quick = findNextIndex(nums, quick); 
                    
                if (slow == -1 || quick == -1) {
                    //return false;  wrong: this means current i is false, but we should try the next i.
                    break; 
                }
                if (quick != -1) {
                    quick = findNextIndex(nums, quick); // quick needs to move 2 steps 
                }
                
                if (quick == slow) {
                    return true;
                }

            } 
        }
   
        return false; 
    }
    
    // 
    private int findNextIndex(int[] nums, int curIndex) {
        int next = curIndex + nums[curIndex];
        if (next < 0) { // 考虑负数情况
            next = next % nums.length + nums.length;
        }
        next = next % nums.length; 
        
        // 加条件判断并返回一个非常规数（eg: -1) 作为fasle的indicator
        // condition 1: array must move towards one direction 
        if ((nums[curIndex] > 0 && nums[next] < 0) || (nums[curIndex] < 0 && nums[next] > 0)) {
            return -1; 
        }
        // condition 2: if array pointing to the element it self, not a cycle: not a cycle because the sequence's length is 1. 
        if (curIndex == next) {
            return -1; 
        }
        return next; 
    }
}
